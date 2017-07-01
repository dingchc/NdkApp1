package com.dcc.ndkapp1;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    Button btn_play;
    private int test1Int = 0;
    private int test1Float = 0;
    private int test2Int = 0;

    private int test1V3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Example of a call to a native method

        surfaceView = (SurfaceView) findViewById(R.id.sv_video);
        btn_play = (Button) findViewById(R.id.btn_play);

        final String inputPath = Environment.getExternalStorageDirectory() + File.separator + "1.mp4";

        String outputPath = Environment.getExternalStorageDirectory() + File.separator + "1.mp4.output";


//        decode(inputPath, outputPath);


        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                render(inputPath, surfaceView.getHolder().getSurface());
                play(inputPath);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * 创建一个AudioTrac对象，用于播放
     *
     * @return
     */
    public AudioTrack createAudioTrack(int sampleRateInHz, int nb_channels) {
        //固定格式的音频码流
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        Log.i("jason", "nb_channels:" + nb_channels);
        //声道布局
        int channelConfig;
        if (nb_channels == 1) {
            channelConfig = android.media.AudioFormat.CHANNEL_OUT_MONO;
        } else if (nb_channels == 2) {
            channelConfig = android.media.AudioFormat.CHANNEL_OUT_STEREO;
        } else {
            channelConfig = android.media.AudioFormat.CHANNEL_OUT_STEREO;
        }

        int bufferSizeInBytes = AudioTrack.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRateInHz, channelConfig,
                audioFormat,
                bufferSizeInBytes, AudioTrack.MODE_STREAM);
        //播放
        //audioTrack.play();
        //写入PCM
        //audioTrack.write(audioData, offsetInBytes, sizeInBytes);
        //播放完调用stop即可

        audioTrack.stop();
        return audioTrack;
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native void decode(String input, String output);

    public native void render(String input, Surface surface);

    public native void sound(String input, String output);

    public native void play(String input);

    // Used to load the 'native-lib' library on application startup.
    static {

        System.loadLibrary("avutil-54");
        System.loadLibrary("swresample-1");
        System.loadLibrary("avcodec-56");
        System.loadLibrary("avformat-56");
        System.loadLibrary("swscale-3");
        System.loadLibrary("postproc-53");
        System.loadLibrary("avfilter-5");
        System.loadLibrary("avdevice-56");
        System.loadLibrary("native-lib");
    }
}
