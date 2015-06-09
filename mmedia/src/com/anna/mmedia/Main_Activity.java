package com.anna.mmedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main_Activity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onClick(View view){
        Intent intent;
        switch (view.getId()) {
            case R.id.audio_btn:
                intent = new Intent(Main_Activity.this, Audio_Activity.class);
                startActivity(intent);
                break;
            case R.id.video_btn:
                intent = new Intent(Main_Activity.this, Video_Activity.class);
                startActivity(intent);
                break;
            case R.id.soundPool_btn:
                intent = new Intent(Main_Activity.this, SoundPool_Activity.class);
                startActivity(intent);
            default:
                break;
        }

    }
}
