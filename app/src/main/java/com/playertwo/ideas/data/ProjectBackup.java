package com.playertwo.ideas.data;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Versioned local data export. Call on a background thread with no concurrent writers. */
public final class ProjectBackup {
    private ProjectBackup() { }

    public interface ByteWriter { void write(java.nio.file.Path path, byte[] bytes) throws IOException; }

    public static void write(IdeaDatabase db, File target) throws IOException {
        write(db, target, Files::write);
    }

    public static void write(IdeaDatabase db, File target, ByteWriter writer) throws IOException {
        if (target.getParentFile() == null || !target.getParentFile().isDirectory()) throw new IOException("invalid backup directory");
        try {
        JSONObject payload = new JSONObject();
        payload.put("version", 2);
        JSONArray projects = new JSONArray();
        for (ProjectEntity p : db.projects().all()) {
            projects.put(new JSONObject().put("projectId", p.projectId).put("title", nullable(p.title))
                .put("status", nullable(p.status)).put("updatedAt", p.updatedAt).put("archivedAt", p.archivedAt));
        }
        JSONArray drafts = new JSONArray();
        for (DraftEntity d : db.drafts().all()) {
            drafts.put(new JSONObject().put("projectId", d.projectId).put("content", nullable(d.content)).put("updatedAt", d.updatedAt));
        }
        JSONArray revisions = new JSONArray();
        for (ProjectRevisionEntity r : db.revisions().all()) {
            revisions.put(new JSONObject().put("projectId", r.projectId).put("revision", r.revision)
                .put("title", nullable(r.title)).put("status", nullable(r.status)).put("updatedAt", r.updatedAt));
        }
        JSONArray events = new JSONArray();
        for (ProjectEventEntity e : db.events().all()) {
            events.put(new JSONObject().put("eventId", e.eventId).put("projectId", e.projectId)
                .put("type", nullable(e.type)).put("createdAt", e.createdAt));
        }
        payload.put("projects", projects).put("drafts", drafts).put("events", events).put("revisions", revisions);
        String body = payload.toString();
        JSONObject envelope = new JSONObject().put("sha256", sha256(body)).put("payload", body);
        File temporary = new File(target.getParentFile(), target.getName() + ".partial");
        try {
            writer.write(temporary.toPath(), envelope.toString().getBytes(StandardCharsets.UTF_8));
            Files.move(temporary.toPath(), target.toPath(), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException error) {
            Files.deleteIfExists(temporary.toPath());
            throw error;
        }
        } catch (org.json.JSONException error) { throw new IOException("cannot encode backup", error); }
    }

    public static void restore(IdeaDatabase db, File source) throws IOException {
        JSONObject envelope;
        JSONObject payload;
        try {
            envelope = new JSONObject(new String(Files.readAllBytes(source.toPath()), StandardCharsets.UTF_8));
            String body = envelope.getString("payload");
            if (!sha256(body).equals(envelope.getString("sha256"))) throw new IOException("backup digest mismatch");
            payload = new JSONObject(body);
            if (payload.getInt("version") != 2) throw new IOException("unsupported backup version");
            JSONArray projects = payload.getJSONArray("projects");
            JSONArray drafts = payload.getJSONArray("drafts");
            JSONArray events = payload.getJSONArray("events");
            JSONArray revisions = payload.getJSONArray("revisions");
            Set<String> ids = new HashSet<>();
            List<ProjectEntity> projectRows = new ArrayList<>();
            List<DraftEntity> draftRows = new ArrayList<>();
            List<ProjectEventEntity> eventRows = new ArrayList<>();
            List<ProjectRevisionEntity> revisionRows = new ArrayList<>();
            for (int i = 0; i < projects.length(); i++) {
                JSONObject p = projects.getJSONObject(i);
                String id = p.getString("projectId");
                if (id.isEmpty() || !ids.add(id)) throw new IOException("invalid project ID");
                projectRows.add(new ProjectEntity(id, optionalString(p, "title"), optionalString(p, "status"),
                    p.getLong("updatedAt"), p.getLong("archivedAt")));
            }
            for (JSONArray children : new JSONArray[]{drafts, events, revisions}) {
                for (int i = 0; i < children.length(); i++) {
                    if (!ids.contains(children.getJSONObject(i).getString("projectId"))) throw new IOException("orphan backup record");
                }
            }
            for (int i = 0; i < drafts.length(); i++) {
                JSONObject d = drafts.getJSONObject(i);
                draftRows.add(new DraftEntity(d.getString("projectId"), optionalString(d, "content"), d.getLong("updatedAt")));
            }
            for (int i = 0; i < events.length(); i++) {
                JSONObject e = events.getJSONObject(i);
                ProjectEventEntity event = new ProjectEventEntity(e.getString("projectId"), optionalString(e, "type"), e.getLong("createdAt"));
                event.eventId = e.getLong("eventId");
                eventRows.add(event);
            }
            for (int i = 0; i < revisions.length(); i++) {
                JSONObject r = revisions.getJSONObject(i);
                int number = r.getInt("revision");
                if (number < 1) throw new IOException("invalid revision number");
                revisionRows.add(new ProjectRevisionEntity(r.getString("projectId"), number,
                    optionalString(r, "title"), optionalString(r, "status"), r.getLong("updatedAt")));
            }
            db.runInTransaction(() -> {
                for (String id : ids) if (db.projects().find(id) != null) throw new IllegalStateException("project already exists");
                for (ProjectEntity p : projectRows) db.projects().insert(p);
                for (ProjectRevisionEntity r : revisionRows) db.revisions().insert(r);
                for (DraftEntity d : draftRows) db.drafts().save(d);
                for (ProjectEventEntity e : eventRows) db.events().insert(e);
            });
        } catch (org.json.JSONException error) {
            throw new IOException("invalid backup", error);
        }
    }

    private static String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : digest) hex.append(String.format("%02x", b & 0xff));
            return hex.toString();
        } catch (NoSuchAlgorithmException error) { throw new IllegalStateException(error); }
    }

    private static Object nullable(String value) { return value == null ? JSONObject.NULL : value; }

    private static String optionalString(JSONObject object, String name) throws org.json.JSONException {
        if (!object.has(name)) throw new org.json.JSONException("missing " + name);
        return object.isNull(name) ? null : object.getString(name);
    }
}
