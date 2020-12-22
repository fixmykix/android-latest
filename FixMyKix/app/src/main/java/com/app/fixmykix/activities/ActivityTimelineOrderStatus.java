package com.app.fixmykix.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterUserOrder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTimelineOrderStatus extends Activity {
    public static final String ORIENTATION = "orientation";
    @BindView(R.id.recycler)
    RecyclerView rvOrderView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_time_line_order_status);
        ButterKnife.bind(this);
        int orientation = getIntent().getIntExtra(ORIENTATION, LinearLayoutManager.VERTICAL);

        rvOrderView.setLayoutManager(new LinearLayoutManager(this, orientation, false));
        List<String> items = new ArrayList<>();
        items.add("Order Confirmed");
        items.add("Order Processing");
        items.add("Order Shipped");
        items.add("Order Arrived");
        items.add("Order Recieved");
        /*AdapterUserOrder adapter = new TimeLineAdapter(orientation, items);
        rvServices.setAdapter(adapter);*/
    }
}
