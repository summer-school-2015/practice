package com.practice.farmatholin.backgroundservices;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Vladislav on 09.06.2015.
 */
public class BackgroundService extends IntentService {

    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.practice.farmatholin.backgroundservices.service.receiver";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundService(String name) {
        super(name);
    }
    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i <= 10; i++) {
            MainActivity.doFakeWork();
        }
        publishResults("DONE");
    }

    private void publishResults(String result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }
}
