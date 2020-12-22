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

public class ActivityDeliveryInstructions extends Activity {

    @BindView(R.id.tvproceed)
    TextView txtProceed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ship_label_instructions);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvproceed)
    void onClickBuyNow() {
        startActivity(new Intent(this, ActivityTimelineOrderStatus.class));
    }
}
