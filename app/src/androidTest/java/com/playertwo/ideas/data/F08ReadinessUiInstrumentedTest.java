package com.playertwo.ideas.data;

import android.widget.TextView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.playertwo.ideas.MainActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class F08ReadinessUiInstrumentedTest {
    @Test public void readinessPanelExposesStatusDimensionsAndActionablePendingState() {
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class)) {
            scenario.onActivity(activity -> {
                TextView status = activity.findViewById(com.playertwo.ideas.R.id.readiness_status);
                TextView pending = activity.findViewById(com.playertwo.ideas.R.id.readiness_pending);
                TextView action = activity.findViewById(com.playertwo.ideas.R.id.readiness_action);
                assertNotNull(status);
                assertNotNull(pending);
                assertNotNull(action);
                assertTrue(status.getText().toString().contains("Readiness"));
                assertTrue(pending.getText().toString().contains("pendente"));
                assertTrue(action.getText().toString().contains("G08"));
            });
        }
    }
}
