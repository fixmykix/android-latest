package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.ExpandableMultiCheckRecyclerAdapter;
import com.app.fixmykix.adapters.SingleCheckRecyclerAdapter;
import com.app.fixmykix.model.MultiCheckGenre;
import com.app.fixmykix.model.ServiceResponse;
import com.app.fixmykix.model.SingleCheckGenre;
import com.app.fixmykix.utils.Constants;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubCategoryActivity extends Activity {

    @BindView(R.id.rv_sub_categories_level_3)
    RecyclerView recyclerViewSubCategories;
    @BindView(R.id.tv_apply)
    TextView txtApply;
    private ExpandableMultiCheckRecyclerAdapter expandableRecyclerViewAdapter;
    private SingleCheckRecyclerAdapter singleCheckRecyclerAdapter;

    private List<MultiCheckGenre> childrenItemArrayList = new ArrayList<>();
    private List<SingleCheckGenre> singleCheckGenreArrayList = new ArrayList<>();
    private List<ServiceResponse> childrenItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory_level_3);
        ButterKnife.bind(this);
        childrenItemArrayList = getIntent().getParcelableArrayListExtra("SubCategoryList");
        singleCheckGenreArrayList = getIntent().getParcelableArrayListExtra("SingleSubCategoryList");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_sub_categories_level_3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (childrenItemArrayList != null && !childrenItemArrayList.isEmpty()) {
            expandableRecyclerViewAdapter = new ExpandableMultiCheckRecyclerAdapter(childrenItemArrayList);
            recyclerView.setAdapter(expandableRecyclerViewAdapter);
            expandableRecyclerViewAdapter.setChildClickListener(new OnCheckChildClickListener() {
                @Override
                public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                    Log.e("Checked", "Checked" + group.selectedChildren + group.isChildChecked(childIndex));
                    childrenItems = group.getItems();
                    for (int i = 0; i < childrenItems.size(); i++) {
                        if (checked) {
                            if (childIndex == i) {
                                services.add(childrenItems.get(i));
                            }

                        }
                    }

                }
            });
        } else {
            singleCheckRecyclerAdapter = new SingleCheckRecyclerAdapter(singleCheckGenreArrayList);
            recyclerView.setAdapter(singleCheckRecyclerAdapter);
            singleCheckRecyclerAdapter.setChildClickListener(new OnCheckChildClickListener() {
                @Override
                public void onCheckChildCLick(View v, boolean checked, CheckedExpandableGroup group, int childIndex) {
                    Log.e("Checked", "Checked" + group.selectedChildren + group.isChildChecked(childIndex));
                    childrenItems = group.getItems();
                    for (int i = 0; i < childrenItems.size(); i++) {
                        if (checked) {
                            if (childIndex == i) {
                                services.add(childrenItems.get(i));
                            }

                        }
                    }

                }
            });
        }

    }

    List<ServiceResponse> services = new ArrayList<>();

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    @OnClick(R.id.tv_apply)
    void onClickApply() {
        ArrayList<String> intIdArray = new ArrayList<>();
        HashSet<ServiceResponse> hashSet = new HashSet<ServiceResponse>(services);
        services.clear();
        services.addAll(hashSet);
        if (services.isEmpty()) {
            Toast.makeText(this, "Please check atleast one subcategory", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < services.size(); i++) {
            intIdArray.add(i, String.valueOf(services.get(i).getId()));
        }
        HashSet<String> hashSet1 = new HashSet<String>(intIdArray);
        intIdArray.clear();
        intIdArray.addAll(hashSet1);
        if (singleCheckGenreArrayList != null && !singleCheckGenreArrayList.isEmpty()) {
            Intent intent = new Intent(this, ActivityAddService.class);
            intent.putParcelableArrayListExtra(Constants.KEY_SERVICE_IDS, (ArrayList<? extends Parcelable>) services);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ActivityArtistList.class);
            intent.putExtra(Constants.KEY_SERVICE_IDS, intIdArray);
            startActivity(intent);
        }

       /* String idArray = android.text.TextUtils.join(",", intIdArray);
        Log.e("SelectedIds", ":" + idArray);*/
// If you want a `primitive` type array
    }
}
