package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.fixmykix.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDeliveryOptions extends Activity {
    @BindView(R.id.tv_buynow)
    TextView txtBuyNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_delivery);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    @OnClick(R.id.tv_buynow)
    void onClickBuyNow() {
        startActivity(new Intent(this, ActivityDeliveryInstructions.class));
    }
}
