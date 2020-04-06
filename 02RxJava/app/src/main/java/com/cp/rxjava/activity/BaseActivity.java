package com.cp.rxjava.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog loadingDialog;
    protected boolean isLoading = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
