package com.app.fixmykix.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterWhatIsService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityWhatIsService extends Activity {
    @BindView(R.id.rv_what_is_service)
    RecyclerView rvWhatIsService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_is_service);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        rvWhatIsService.setAdapter(new AdapterWhatIsService(this));
        rvWhatIsService.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @OnClick(R.id.iv_back_what_is_service)
    void onClickBack() {
        onBackPressed();
    }
}
