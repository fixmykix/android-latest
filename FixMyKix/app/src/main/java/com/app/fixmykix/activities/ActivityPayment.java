package com.app.fixmykix.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.fixmykix.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPayment extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back_payment)
    void onClickBack() {
        onBackPressed();
    }
}