package com.practice.farmatholin.backgroundservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private ProgressBar progress;
    private ProgressBar progressAsync;
    private TextView text;
    private TextView textMainThread;
    private TextView textAsync;
    private TextView textService;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(BackgroundService.RESULT);
                textService.setText(string);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar1);
        progressAsync = (ProgressBar) findViewById(R.id.progressBarAsync);
        text = (TextView) findViewById(R.id.textView1);
        textMainThread = (TextView) findViewById(R.id.textViewMainThread);
        textAsync = (TextView) findViewById(R.id.textViewAsyncRes);
        textService = (TextView) findViewById(R.id.textViewServiceRes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(BackgroundService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void startProgressMain(View view) {
        textMainThread.setText("Start");
        for (int i = 0; i <= 10; i++) {
            doFakeWork();
        }
        textMainThread.setText("DONE");
    }

    public void startProgress(View view) {
        // do something long
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    final int value = i;
                    doFakeWork();
                    progress.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setText("Updating");
                            progress.setProgress(value);
                            if (value == 10) {
                                text.setText("DONE");
                            }
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    public void startProgressAsync(View view) {
        textAsync.setText("Start");
        ProcessAsyncTask task = new ProcessAsyncTask();
        task.execute();
    }

    public void startProgressService(View view) {
        Intent intent = new Intent(this, BackgroundService.class);
        startService(intent);
        textService.setText("Start");
    }

    private class ProcessAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... words) {
            for (int i = 0; i <= 10; i++) {
                progressAsync.setProgress(i);
                doFakeWork();
            }
            return "DONE";
        }

        @Override
        protected void onPostExecute(String result) {
            textAsync.setText(result);
        }
    }

    // Simulating something timeconsuming
    public static void doFakeWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
