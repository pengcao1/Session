package com.cp.rxjava.activity;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cp.rxjava.R;

public class ActivityThreadSchedule extends AppCompatActivity {
    private static final String TAG = ActivityThreadSchedule.class.getSimpleName();
    private SeekBar mSeekBar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_schedule);
        mSeekBar = findViewById(R.id.seekBarThread);
        mTextView = findViewById(R.id.textView);
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            Thread.sleep(5 * 1000);
            emitter.onNext(Thread.currentThread().getName());
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> mTextView.setText(s + ":" + Thread.currentThread().getName()))
                .observeOn(Schedulers.io())
                .subscribe(s -> Log.e(TAG, "accept: s=" + s));

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextView.setText("From SeekBar " + progress + " " + Thread.currentThread().getName());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
