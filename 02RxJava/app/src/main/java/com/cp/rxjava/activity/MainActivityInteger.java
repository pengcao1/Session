package com.cp.rxjava.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


import com.cp.rxjava.R;

public class MainActivityInteger extends AppCompatActivity {
    private static final String TAG = MainActivityInteger.class.getSimpleName();
    private static final int UNIT_SLEEP = 5;
    private static final int EVENT_COUNT = 8;
    private TextView mTextView;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_integer);
        mTextView = findViewById(R.id.txt_integer);
        mSeekBar = findViewById(R.id.seekbar);
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
        mSeekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mTextView.setText(progress + ">" + Thread.currentThread().getName());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };


    public static Observable<String> getObservable() {
        return Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < EVENT_COUNT; i++) {
                emitter.onNext(i);
                Thread.sleep(UNIT_SLEEP * 1000);
            }
            emitter.onComplete();
        }).map(i -> i + ">" + Thread.currentThread().getName());
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                mTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                mTextView.setText("completed" + ":" + Thread.currentThread().getName());
            }
        };
    }
}
