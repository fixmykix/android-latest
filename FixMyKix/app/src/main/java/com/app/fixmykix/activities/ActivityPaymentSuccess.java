package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.fixmykix.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityPaymentSuccess extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_payment);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    @OnClick(R.id.upsstatus)
    void onClickPay() {
        startActivity(new Intent(ActivityPaymentSuccess.this, ActivityCheckOut.class));
    }
}
