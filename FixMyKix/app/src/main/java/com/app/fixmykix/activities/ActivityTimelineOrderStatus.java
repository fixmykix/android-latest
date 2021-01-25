package com.app.fixmykix.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.TimeLineStatusAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityTimelineOrderStatus extends Activity {
    @BindView(R.id.recycler)
    RecyclerView rvOrderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_time_line_order_status);
        ButterKnife.bind(this);
        List<String> items = new ArrayList<>();
        items.add("Order Confirmed");
        items.add("Order Processing");
        items.add("Order Shipped");
        items.add("Order Arrived");
        items.add("Order Recieved");
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityTimelineOrderStatus.this);
        rvOrderView.setLayoutManager(layoutManager);
        TimeLineStatusAdapter timeLineStatusAdapter = new
                TimeLineStatusAdapter(ActivityTimelineOrderStatus.this, items);
        rvOrderView.setAdapter(timeLineStatusAdapter);

    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }
}
