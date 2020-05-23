package com.cp.rxjava.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cp.rxjava.R;

import java.util.concurrent.TimeUnit;

public class ActivityInterval extends AppCompatActivity {
    private static final String TAG = ActivityInterval.class.getSimpleName();
    private SeekBar mSeekBar;
    private TextView mTextView;
    private float LARGE_SIZE = 24f;
    private float SMALL_SIZE = 16f;

    private static final int UPDATE_UI = 1;
    private static final String DATA_FROM_HANDLER = "data_from_handler";
    private Handler mHandler;
    HandlerThread handlerThread = new HandlerThread(TAG);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);
        mSeekBar = findViewById(R.id.seekBarInterval);
        mTextView = findViewById(R.id.textViewInterval);
        bindSeekBarWithTextView();
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept: " + aLong);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "subscribe accept: " + aLong);
                        mTextView.setText("From Interval:" + aLong);
                        mTextView.setTextColor(aLong % 2 == 0 ? Color.RED : Color.BLACK);
                        mTextView.setTextSize(aLong % 2 == 0 ? LARGE_SIZE : SMALL_SIZE);
                    }
                });
        createHandlerThread();
    }


    private void bindSeekBarWithTextView() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextView.setText("From Seek Bar: " + progress);
                mTextView.setTextColor(progress % 2 == 0 ? Color.RED : Color.BLACK);
                mTextView.setTextSize(progress % 2 == 0 ? LARGE_SIZE : SMALL_SIZE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startHandler(View view) {
//        mHandler.sendEmptyMessage()
        Message msg = Message.obtain();
        msg.what = UPDATE_UI;
        Bundle bundle = new Bundle();
        bundle.putInt(DATA_FROM_HANDLER, 1);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void createHandlerThread() {
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper()) {

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int date = msg.getData().getInt(DATA_FROM_HANDLER, 1);
                        mTextView.setText("From Handler.. +" + date);
                        try {
                            Thread.sleep(3 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg = Message.obtain();
                        msg.what = UPDATE_UI;
                        Bundle bundle = new Bundle();
                        bundle.putInt(DATA_FROM_HANDLER, date++);
                        msg.setData(bundle);
                        mHandler.sendMessage(msg);
                    }
                });
            }
        };
    }

}
