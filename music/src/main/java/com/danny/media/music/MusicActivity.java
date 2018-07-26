package com.danny.media.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.danny.media.R;

import java.io.IOException;

public class MusicActivity extends AppCompatActivity {
    private static final String TAG = "MusicActivity";
    private Button mStart;
    private MediaPlayer mediaPlayer;
    private MusicActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mStart=findViewById(R.id.start);
        mActivity=this;
        mediaPlayer=new MediaPlayer();
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                try {
//                    mediaPlayer=MediaPlayer.create(mActivity,R.raw.hello);
//                    mediaPlayer.start();
                    mediaPlayer.reset();
                    //"http://m10.music.126.net/20180410141606/d0d7f29a090ffb503b6efa453ece9662/ymusic/18df/0903/e89d/f5ca352e64bff21cd6915aae6a10d540.mp3"
                    mediaPlayer.setDataSource("url");
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.start();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            int time=mp.getDuration();
                            Log.d("MainActivity", "onPrepared: "+time);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
