package com.anna.mmedia;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;


/**
 * Created by Анюта on 09.06.2015.
 */
public class SoundPool_Activity extends Activity {
    int MAX_STREAMS = 3;

    SoundPool sp;
    int soundIdShot;
    int soundIdExplosion;
    int soundIdMachineGun;
    RadioGroup radioGroup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spool);

        radioGroup = (RadioGroup) findViewById(R.id.streams_rd);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.num_of_stream2) {
                    MAX_STREAMS = 2;
                } else {
                    MAX_STREAMS = 3;
                }
                sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
                soundIdShot = sp.load(SoundPool_Activity.this, R.raw.shot, 1);
                soundIdExplosion = sp.load(SoundPool_Activity.this, R.raw.explosion, 1);
                soundIdMachineGun = sp.load(SoundPool_Activity.this, R.raw.machine_gun, 1);
            }
        });

        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        soundIdShot = sp.load(SoundPool_Activity.this, R.raw.shot, 1);
        soundIdExplosion = sp.load(SoundPool_Activity.this, R.raw.explosion, 1);
        soundIdMachineGun = sp.load(SoundPool_Activity.this, R.raw.machine_gun, 1);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playSP1_btn:
                sp.play(soundIdShot, 1, 1, 0, 7, 1);
                break;
            case R.id.playSP2_btn:
                sp.play(soundIdExplosion, 1, 1, 0, 3, 1);
                break;
            case R.id.playSP3_btn:
                sp.play(soundIdMachineGun, 1, 1, 0, 3, 1);
                break;
        }
    }
}
