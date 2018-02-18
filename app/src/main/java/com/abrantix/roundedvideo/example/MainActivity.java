package com.abrantix.roundedvideo.example;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.abrantix.roundedvideo.R;
import com.abrantix.roundedvideo.VideoSurfaceView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private VideoSurfaceView mVideoSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int radius = getResources()
                .getDimensionPixelOffset(R.dimen.corner_radius_video);

        final String[] dataSources = new String[] {
                "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
        };

        mVideoSurfaceView = (VideoSurfaceView) findViewById(R.id.video_surface_view1);

        Thread t = new Thread() {
            boolean f=true;
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(f){
                                  /*  LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    mVideoSurfaceView.setLayoutParams(params); */
                                    mVideoSurfaceView.setCornerRadius(0);
                                    mVideoSurfaceView.setFlag(f);
                                    f=false;
                                }
                                else{
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    params.weight=0.5f;
                                    params.gravity= Gravity.CENTER;
                                    //layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
                                    //layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT;

                                    mVideoSurfaceView.setLayoutParams(params);
                                    mVideoSurfaceView.setCornerRadius(radius);
                                    mVideoSurfaceView.setFlag(f);
                                    f=true;
                                }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }

        };

        t.start();


    //    mVideoSurfaceView[1].setCornerRadius(radius);
     //   mVideoSurfaceView[2].setCornerRadius(radius);


            final MediaPlayer mediaPlayer = new MediaPlayer();
            final VideoSurfaceView surfaceView = mVideoSurfaceView;
            final String dataSource = dataSources[0];
            try {
                mediaPlayer.setDataSource(dataSource);
                // the video view will take care of calling prepare and attaching the surface once
                // it becomes available
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        if(surfaceView.getFlag()==true)
                        surfaceView.setVideoAspectRatio((float) mediaPlayer.getVideoWidth() /
                                (float) mediaPlayer.getVideoHeight());
                        else
                            surfaceView.setVideoAspectRatio(2*radius);
                    }
                });
                surfaceView.setMediaPlayer(mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
                mediaPlayer.release();
            }


        // Draw a smooth background gradient that is always changing
        getWindow().getDecorView().setBackgroundDrawable(new WickedGradientDrawable());

        // Animate the top surface up and down so we're sure animations work
       /* mVideoSurfaceView[0].animate()
                .translationY(600f)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) { }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        final float targetY = mVideoSurfaceView[0].getTranslationY() == 0 ?
                                600f : 0;
                        mVideoSurfaceView[0].animate()
                                .translationY(targetY)
                                .setDuration(1999)
                                .setListener(this)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) { }

                    @Override
                    public void onAnimationRepeat(Animator animation) { }
                })
                .start(); */
    }
}
