package com.lh.danmakuview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.lh.danmakulibrary.BiliBiliDanmakuParser;
import com.lh.danmakulibrary.Danmaku;
import com.lh.danmakulibrary.DanmakuView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Danmaku> ds;
    private DanmakuView danmakuView;
    private Button btnPlay, btnPause, btnResume, btnStop;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreat_ac");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        danmakuView = (DanmakuView) findViewById(R.id.danmak_view);
        btnPlay = (Button) findViewById(R.id.play);
        btnPause = (Button) findViewById(R.id.pause);
        btnResume = (Button) findViewById(R.id.resume);
        btnStop = (Button) findViewById(R.id.stop);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        BiliBiliDanmakuParser parse = new BiliBiliDanmakuParser();
        try {
            ds = parse.parse(getResources().openRawResource(R.raw.test_danmaku));
        } catch (Exception e) {
            e.printStackTrace();
        }
        danmakuView.setShowDebugInfo(true);
        danmakuView.post(new Runnable() {
            @Override
            public void run() {
                danmakuView.setDanmakuSource(ds);
            }
        });
        danmakuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (danmakuView.isShow()) {
                    danmakuView.hide();
                } else {
                    danmakuView.show();
                }
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.start();
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.pause();
            }
        });
        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.resume();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                danmakuView.stop();
            }
        });
        seekBar.setMax((int) ds.get(ds.size() - 1).getTime());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                danmakuView.seekTo(seekBar.getProgress());
            }
        });
        System.out.println("Creat_ac_finish");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView.isPrepared()) {
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (danmakuView.isPrepared()) {
            danmakuView.release();
        }
    }
}
