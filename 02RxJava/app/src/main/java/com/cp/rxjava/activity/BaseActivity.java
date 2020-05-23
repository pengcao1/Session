package com.cp.rxjava.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cp.rxjava.R;
import com.java.Node;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected ProgressDialog loadingDialog;
    protected boolean isLoading = false;
    private Button btnStartRecycle;
    private Button btnStartInteger;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
        btnStartRecycle = findViewById(R.id.btn_start_recycle);
        btnStartInteger = findViewById(R.id.btn_start_integer);
        btnStartInteger.setOnClickListener(this);
        btnStartRecycle.setOnClickListener(this);

        Node<Double> node = new Node<Double>(4.0);
        node.setValue(5.0);
    }

    public void startRecycleView() {
        startActivity(ActivityGetNetworkData.class);
    }

    public void startIntegerActivity() {
        startActivity(MainActivityInteger.class);
    }

    private void startActivity(Class activityName) {
        Intent intent = new Intent();
        intent.setClass(this, activityName);
        startActivity(intent);
    }

    public void startThreadSchedule(View view) {
        startActivity(ActivityThreadSchedule.class);
    }

    public void startInternal(View view) {
        startActivity(ActivityInterval.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void loading(String msg) {
        loaded();
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(msg);
        loadingDialog.setIndeterminate(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            loadingDialog.show();
        }
        isLoading = true;

    }

    protected void loaded() {
        if (loadingDialog != null && !isFinishing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
            isLoading = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start_recycle) {
            startRecycleView();
        } else if (v.getId() == R.id.btn_start_integer) {
            startIntegerActivity();
        }
    }
}
