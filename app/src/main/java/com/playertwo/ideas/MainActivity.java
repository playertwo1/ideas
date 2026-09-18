package com.playertwo.ideas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public final class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        TextView view = new TextView(this);
        view.setText("Idea");
        view.setContentDescription("Idea home");
        setContentView(view);
    }
}
