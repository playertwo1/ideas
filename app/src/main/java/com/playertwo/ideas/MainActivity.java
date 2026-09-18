package com.playertwo.ideas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;
import android.text.Editable;
import android.text.TextWatcher;
import com.playertwo.ideas.data.DraftEntity;
import com.playertwo.ideas.data.IdeaDatabase;
import com.playertwo.ideas.data.ProjectEntity;
import com.playertwo.ideas.data.ProjectRepository;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MainActivity extends Activity {
    private final ExecutorService storage = Executors.newSingleThreadExecutor();
    private IdeaDatabase database;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        EditText editor = findViewById(R.id.draft_editor);
        TextView status = findViewById(R.id.save_status);
        editor.setEnabled(false);
        String projectId = getPreferences(MODE_PRIVATE).getString("activeProjectId", null);
        if (projectId == null) {
            projectId = UUID.randomUUID().toString();
            if (!getPreferences(MODE_PRIVATE).edit().putString("activeProjectId", projectId).commit()) {
                status.setText("Erro ao preparar o rascunho");
                return;
            }
        }
        String selectedId = projectId;
        storage.execute(() -> {
            try {
                database = IdeaDatabase.open(this);
                if (database.projects().find(selectedId) == null)
                    database.projects().insert(new ProjectEntity(selectedId, "Rascunho", "ACTIVE", System.currentTimeMillis()));
                DraftEntity draft = database.drafts().find(selectedId);
                runOnUiThread(() -> {
                    if (draft != null) editor.setText(draft.content);
                    status.setText("Salvo");
                    editor.setEnabled(true);
                    editor.addTextChangedListener(new TextWatcher() {
                        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                            status.setText("Salvando");
                            String content = s.toString();
                            storage.execute(() -> {
                                try {
                                    new ProjectRepository(database).saveDraft(selectedId, content, System.currentTimeMillis());
                                    runOnUiThread(() -> status.setText("Salvo"));
                                } catch (RuntimeException error) {
                                    Log.e("Idea", "draft_save_failed");
                                    runOnUiThread(() -> status.setText("Não foi possível salvar"));
                                }
                            });
                        }
                        @Override public void afterTextChanged(Editable s) { }
                    });
                });
                Log.i("Idea", "home_opened");
            } catch (RuntimeException error) {
                Log.e("Idea", "home_open_failed");
                runOnUiThread(() -> status.setText("Não foi possível abrir o rascunho"));
            }
        });
    }

    @Override protected void onDestroy() {
        storage.execute(() -> { if (database != null) database.close(); });
        storage.shutdown();
        super.onDestroy();
    }
}
