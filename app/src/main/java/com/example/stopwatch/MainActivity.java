package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Locale;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0; // int for number of seconds displayed
    private boolean running; // is the stopwatch running?
    private boolean wasRunning; // was the stopwatch running ?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the previous state of the stopwatch if the activity was destroyed or recreated
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        runTimer();
    }

    // Save the state of the stopwatch if it's about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    // if the activity is paused, stop the stopwatch
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    // if the activity is resumed, start the stopwatch again if it was running previously
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    // start the stopwatch when the start button is clicked
    public void onClickStart(View view) {
        running = true;
    }

    // stop the stopwatch when the stop button is clicked
    public void onClickStop(View view) {
        running = false;
    }

    // reset seconds to 0 when reset button is clicked
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    // increment the stopwatch
    public void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
           @Override
           public void run() {
               int hours = seconds/3600;
               int minutes = (seconds%3600)/60;
               int secs = seconds%60;
               String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
               timeView.setText(time);
               if (running) {
                   // This isn't 100% accurate as there will be a slight delay over time.
                   seconds++;
               }
               handler.postDelayed(this, 1000);
           }
        });
    }

}
