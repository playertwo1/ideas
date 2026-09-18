package com.playertwo.ideas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;

public final class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        TextView view = new TextView(this);
        view.setText("Idea\n\nNenhum projeto ainda.");
        view.setContentDescription("Idea home");
        Log.i("Idea", "home_opened");
        setContentView(view);
    }
}
