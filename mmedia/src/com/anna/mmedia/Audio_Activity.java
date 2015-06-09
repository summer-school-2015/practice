package com.anna.mmedia;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;


/**
 * Created by User on 08/06/2015.
 */
public class Audio_Activity extends Activity {
    private MediaPlayer mediaPlayer;
    private SeekBar seekbar;
    private SeekBar volume;
    private Handler myHandler = new Handler();
    private CheckBox chbLoop;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);
        mediaPlayer = MediaPlayer.create(this, R.raw.yesterday);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(false);
        seekbar.setMax((int)mediaPlayer.getDuration());
        seekbar.setProgress((int)mediaPlayer.getCurrentPosition());

        volume = (SeekBar)findViewById(R.id.volume);
        volume.setMax(10);
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.setVolume((float)progress/10, (float)progress/10);
            }
        });
        myHandler.postDelayed(UpdateSongTime,100);

        chbLoop=(CheckBox) findViewById(R.id.loop_chb);

        chbLoop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged (CompoundButton buttonView,
                boolean isChecked){
                if (mediaPlayer != null)
                    mediaPlayer.setLooping(isChecked);
            }
            }

            );
        }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.resume_btn:
                mediaPlayer.start();
                break;
            case R.id.pause_btn:
                mediaPlayer.pause();
                break;
            case R.id.forward_btn:
                if(mediaPlayer.getCurrentPosition() + 5000 < mediaPlayer.getDuration()) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
                    seekbar.setProgress((int)mediaPlayer.getCurrentPosition());
                    mediaPlayer.setVolume((float)0.1,(float)0.1);
                    break;
                }
                else {
                    mediaPlayer.seekTo(mediaPlayer.getDuration());
                    break;
                }
            case R.id.backward_btn:
                if(mediaPlayer.getCurrentPosition() - 5000 > 0) {
                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
                    seekbar.setProgress((int) mediaPlayer.getCurrentPosition());
                    mediaPlayer.setVolume((float)1.0,(float)1.0);
                    break;
                }
                else{
                    mediaPlayer.seekTo(0);
                    break;
                }
        }

    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            seekbar.setProgress((int)mediaPlayer.getCurrentPosition());
            myHandler.postDelayed(this, 100);
        }
    };

}
