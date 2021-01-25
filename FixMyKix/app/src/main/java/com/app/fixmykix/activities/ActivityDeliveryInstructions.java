package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.app.fixmykix.R;

import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityDeliveryInstructions extends Activity {

    @BindView(R.id.tvproceed)
    TextView txtProceed;

    @BindView(R.id.tv_order_instructions_steps)
    TextView txtOrderInstructions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ship_label_instructions);
        ButterKnife.bind(this);
        String[] mTestArray = getResources().getStringArray(R.array.order_instructions);
        txtOrderInstructions.setText(Arrays.toString(mTestArray));
        txtOrderInstructions.setText(Arrays.toString(mTestArray).replaceAll("\\[|\\]", ""));
        txtOrderInstructions.setText(txtOrderInstructions.getText().toString().replaceAll(",", ""));
    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    @OnClick(R.id.tvproceed)
    void onClickBuyNow() {
        startActivity(new Intent(this, ActivityTimelineOrderStatus.class));
    }
}
