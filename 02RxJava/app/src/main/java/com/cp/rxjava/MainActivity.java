package com.cp.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.core.Flowable;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_helloWord);
        RxJavaHelloWord();
    }

    private void RxJavaHelloWord() {
        Flowable.just("Hello Word RxJava").subscribe(s -> {
            textView.setText(s);
        });
    }
}
