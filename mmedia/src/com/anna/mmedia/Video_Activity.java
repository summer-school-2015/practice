package com.anna.mmedia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by User on 08/06/2015.
 */
public class Video_Activity extends Activity {
    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private SeekBar seekBarVideo;
    private Handler myHandler = new Handler();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        myVideoView = (VideoView) findViewById(R.id.video_view);
        myHandler.postDelayed(UpdateVideoTime,100);
        try {
            myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cat));
        } catch (Exception e) {
            e.printStackTrace();
        }
        myVideoView.requestFocus();
        seekBarVideo = (SeekBar)findViewById(R.id.seekBarVideo);
        seekBarVideo.setClickable(false);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.playVideo_btn:
                myVideoView.start();
                seekBarVideo.setMax((int)myVideoView.getDuration());
                seekBarVideo.setProgress((int)myVideoView.getCurrentPosition());
                break;
            case R.id.pauseVideo_btn:
                myVideoView.pause();
                break;
            case R.id.forwVideo_btn:
                if(myVideoView.getCurrentPosition() + 5000 < myVideoView.getDuration()) {
                    myVideoView.seekTo(myVideoView.getCurrentPosition() + 5000);
                    seekBarVideo.setProgress((int)myVideoView.getCurrentPosition());
                    break;
                }
                else {
                    myVideoView.seekTo(myVideoView.getDuration());
                    break;
                }
            case R.id.backVideo_btn:
                if(myVideoView.getCurrentPosition() - 5000 > 0) {
                    myVideoView.seekTo(myVideoView.getCurrentPosition() - 5000);
                    seekBarVideo.setProgress((int) myVideoView.getCurrentPosition());
                    break;
                }
                else{
                    myVideoView.seekTo(0);
                    break;
                }
        }

    }

    private Runnable UpdateVideoTime = new Runnable() {
        public void run() {
            seekBarVideo.setProgress((int)myVideoView.getCurrentPosition());
            myHandler.postDelayed(this, 100);
        }
    };
}
