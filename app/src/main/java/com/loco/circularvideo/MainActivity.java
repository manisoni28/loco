package com.loco.circularvideo;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.LinearLayout;


import java.io.IOException;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private VideoSurfaceView mVideoSurfaceView;
    private int dimensionInDp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT
                                    );
                                    mVideoSurfaceView.setLayoutParams(params);
                                    mVideoSurfaceView.setCornerRadius(0);
                                    mVideoSurfaceView.setFlag(f);
                                    mVideoSurfaceView.invalidate();
                                    f=false;
                                }
                                else{
                                    Random rn = new Random();
                                    int range = 400 - 150 + 1;
                                    int randomNum =  rn.nextInt(range) + 150;
                                   // Toast.makeText(MainActivity.this,"radius="+radius+"radonNum="+randomNum+"",Toast.LENGTH_LONG).show();
                                    dimensionInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, randomNum, getResources().getDisplayMetrics());
                                    mVideoSurfaceView.getLayoutParams().height = dimensionInDp;
                                    mVideoSurfaceView.getLayoutParams().width = dimensionInDp;
                                    mVideoSurfaceView.requestLayout();
                                    mVideoSurfaceView.setCornerRadius(dimensionInDp/2);
                                    mVideoSurfaceView.setFlag(f);
                                    mVideoSurfaceView.invalidate();
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
            final MediaPlayer mediaPlayer = new MediaPlayer();
            final VideoSurfaceView surfaceView = mVideoSurfaceView;
            final String dataSource = dataSources[0];
            try {
                mediaPlayer.setDataSource(dataSource);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        if(surfaceView.getFlag()==true)
                        surfaceView.setVideoAspectRatio((float) mediaPlayer.getVideoWidth() /
                                (float) mediaPlayer.getVideoHeight());
                        else
                            surfaceView.setVideoAspectRatio(dimensionInDp);
                    }
                });
                surfaceView.setMediaPlayer(mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
                mediaPlayer.release();
            }
    }
}
