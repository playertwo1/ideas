package com.playertwo.ideas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import com.playertwo.ideas.data.DraftEntity;
import com.playertwo.ideas.data.IdeaDatabase;
import com.playertwo.ideas.data.ProjectIntakeEntity;
import com.playertwo.ideas.data.ProjectIntakeRepository;
import com.playertwo.ideas.data.ProjectInterpretationService;
import com.playertwo.ideas.data.ProjectEntity;
import com.playertwo.ideas.data.ProjectRepository;
import com.playertwo.ideas.domain.ai.CancellationToken;
import com.playertwo.ideas.domain.ai.FakeAiProvider;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Small offline-first intake screen; all durable decisions stay in the repository. */
public final class MainActivity extends Activity {
    private final ExecutorService storage = Executors.newSingleThreadExecutor();
    private IdeaDatabase database;
    private EditText editor;
    private TextView status;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        editor = findViewById(R.id.draft_editor);
        status = findViewById(R.id.save_status);
        editor.setEnabled(false);
        String selectedId = getPreferences(MODE_PRIVATE).getString("activeProjectId", null);
        if (selectedId == null) {
            selectedId = UUID.randomUUID().toString();
            if (!getPreferences(MODE_PRIVATE).edit().putString("activeProjectId", selectedId).commit()) {
                status.setText("Erro ao preparar o rascunho"); return;
            }
        }
        final String projectId = selectedId;
        storage.execute(() -> openProject(projectId));
    }

    private void openProject(String projectId) {
        try {
            database = IdeaDatabase.open(this);
            ProjectIntakeRepository intake = new ProjectIntakeRepository(database);
            if (database.projects().find(projectId) != null) intake.ensureFromLegacy(projectId);
            DraftEntity draft = database.drafts().find(projectId);
            ProjectIntakeEntity snapshot = intake.find(projectId);
            runOnUiThread(() -> bind(projectId, snapshot, draft));
            Log.i("Idea", "home_opened");
        } catch (RuntimeException error) {
            Log.e("Idea", "home_open_failed");
            runOnUiThread(() -> status.setText("Não foi possível abrir o projeto"));
        }
    }

    private void bind(String projectId, ProjectIntakeEntity snapshot, DraftEntity draft) {
        EditText title = findViewById(R.id.title_editor);
        EditText limits = findViewById(R.id.limits_editor);
        TextView interpretation = findViewById(R.id.interpretation_view);
        TextView type = findViewById(R.id.type_view);
        TextView depth = findViewById(R.id.depth_view);
        TextView progress = findViewById(R.id.progress_view);
        TextView next = findViewById(R.id.next_action_view);
        boolean newCapture = snapshot == null;
        title.setEnabled(newCapture); limits.setEnabled(newCapture);
        title.setText(snapshot == null ? "" : snapshot.originalTitle);
        limits.setText(snapshot == null ? "" : snapshot.originalLimits);
        if (draft != null && draft.content != null) editor.setText(draft.content);
        if (snapshot == null) {
            interpretation.setText("Interpretação: capture a ideia para começar");
            type.setText("Tipo: —"); depth.setText("Modo: —"); progress.setText("Progresso: 0%");
            next.setText("Próxima ação: informar a ideia");
        } else render(snapshot, interpretation, type, depth, progress, next);
        status.setText("Salvo"); editor.setEnabled(true);
        editor.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                status.setText("Salvando"); String content = s.toString();
                String titleValue = title.getText().toString();
                String limitsValue = limits.getText().toString();
                storage.execute(() -> {
                    try {
                        ProjectIntakeRepository repository = new ProjectIntakeRepository(database);
                        if (repository.find(projectId) == null) {
                            if (database.projects().find(projectId) == null)
                                repository.create(projectId, titleValue.trim().isEmpty() ? "Rascunho" : titleValue, content, limitsValue);
                            else repository.createForLegacy(projectId, titleValue.trim().isEmpty() ? "Rascunho" : titleValue, content, limitsValue);
                            ProjectIntakeEntity created = repository.find(projectId);
                            runOnUiThread(() -> {
                                title.setEnabled(false); limits.setEnabled(false);
                                render(created, interpretation, type, depth, progress, next);
                            });
                        } else new ProjectRepository(database).saveDraft(projectId, content, System.currentTimeMillis());
                        runOnUiThread(() -> status.setText("Salvo"));
                    } catch (RuntimeException error) { Log.e("Idea", "draft_save_failed");
                        runOnUiThread(() -> status.setText("Não foi possível salvar")); }
                });
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        Button suggest = findViewById(R.id.suggest_button);
        Button accept = findViewById(R.id.accept_button);
        Button reject = findViewById(R.id.reject_button);
        suggest.setOnClickListener(v -> storage.execute(() -> {
            try {
                new ProjectInterpretationService(new ProjectIntakeRepository(database))
                    .suggest(projectId, new FakeAiProvider(), CancellationToken.never());
                ProjectIntakeEntity updated = new ProjectIntakeRepository(database).find(projectId);
                runOnUiThread(() -> render(updated, interpretation, type, depth, progress, next));
            } catch (RuntimeException error) { Log.e("Idea", "suggestion_failed");
                runOnUiThread(() -> status.setText("Não foi possível sugerir")); }
        }));
        accept.setOnClickListener(v -> storage.execute(() -> {
            try {
                ProjectIntakeRepository repository = new ProjectIntakeRepository(database);
                repository.acceptSuggestion(projectId);
                ProjectIntakeEntity updated = repository.find(projectId);
                runOnUiThread(() -> render(updated, interpretation, type, depth, progress, next));
            } catch (RuntimeException error) { runOnUiThread(() -> status.setText("Aceite uma sugestão válida")); }
        }));
        reject.setOnClickListener(v -> storage.execute(() -> {
            try {
                ProjectIntakeRepository repository = new ProjectIntakeRepository(database);
                repository.rejectSuggestion(projectId);
                ProjectIntakeEntity updated = repository.find(projectId);
                runOnUiThread(() -> render(updated, interpretation, type, depth, progress, next));
            } catch (RuntimeException error) { runOnUiThread(() -> status.setText("Não foi possível rejeitar")); }
        }));
    }

    private static void render(ProjectIntakeEntity value, TextView interpretation, TextView type,
                               TextView depth, TextView progress, TextView next) {
        interpretation.setText("Interpretação: " + (value.interpretation == null ? "ainda não sugerida" : value.interpretation));
        type.setText("Tipo: " + (value.projectType == null ? "—" : value.projectType));
        depth.setText("Modo: " + (value.depthMode == null ? "—" : value.depthMode));
        progress.setText("Progresso: " + value.progressPercent + "%");
        next.setText("Próxima ação: " + value.nextAction);
    }

    @Override protected void onDestroy() {
        storage.execute(() -> { if (database != null) database.close(); });
        storage.shutdown(); super.onDestroy();
    }
}
