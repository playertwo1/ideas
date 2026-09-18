package com.playertwo.ideas.data;

import android.widget.EditText;
import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.playertwo.ideas.MainActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AutosaveUiInstrumentedTest {
    @Test public void editedDraftShowsSavedAndReopensWithConfirmedText() throws Exception {
        try (ActivityScenario<MainActivity> first = ActivityScenario.launch(MainActivity.class)) {
            first.onActivity(activity -> {
                EditText editor = activity.findViewById(com.playertwo.ideas.R.id.draft_editor);
                assertNotNull(editor);
                editor.setText("rascunho confirmado");
            });
            Thread.sleep(700);
            first.onActivity(activity -> {
                TextView status = activity.findViewById(com.playertwo.ideas.R.id.save_status);
                assertEquals("Salvo", status.getText().toString());
            });
        }
        try (ActivityScenario<MainActivity> second = ActivityScenario.launch(MainActivity.class)) {
            Thread.sleep(700);
            second.onActivity(activity -> {
                EditText editor = activity.findViewById(com.playertwo.ideas.R.id.draft_editor);
                assertEquals("rascunho confirmado", editor.getText().toString());
            });
        }
    }
}
