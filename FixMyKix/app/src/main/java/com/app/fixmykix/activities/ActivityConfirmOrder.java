package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterImage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityConfirmOrder extends Activity {

    @BindView(R.id.rv_order_confirm_images)
    RecyclerView rvImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        rvImages.setAdapter(new AdapterImage(ActivityConfirmOrder.this));
        rvImages.setLayoutManager(new LinearLayoutManager(ActivityConfirmOrder.this, RecyclerView.HORIZONTAL, false));
    }

    @OnClick(R.id.iv_back_order_confirm_form)
    void onClickBack() {
        onBackPressed();
    }


    @OnClick(R.id.tv_order_confirm_done)
    void onClickDone() {
      //  startActivity(new Intent(this, ActivityPayment.class));
    }
}
