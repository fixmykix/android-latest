package com.app.fixmykix.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.SubCategoryActivity;
import com.app.fixmykix.adapters.CategoryAdapter;
import com.app.fixmykix.adapters.SubCategoryAdapter;
import com.app.fixmykix.model.ChildrenItem;
import com.app.fixmykix.model.MultiCheckGenre;
import com.app.fixmykix.model.SingleCheckGenre;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryListActivity extends Activity {

    private List<ChildrenItem> childrenItemArrayList = new ArrayList<>();
    private List<ChildrenItem> subCategoriesList = new ArrayList<>();
    private List<ChildrenItem> subCategoriesListChildern = new ArrayList<>();
    private List<MultiCheckGenre> multiCheckGenreArrayList = new ArrayList<>();
    private List<SingleCheckGenre> singleCheckGenres = new ArrayList<>();
    @BindView(R.id.rv_categories)
    RecyclerView recyclerViewCategories;
    @BindView(R.id.rv_sub_categories)
    RecyclerView recyclerViewSubCategories;
    @BindView(R.id.iv_back_about_us)
    ImageView imgBack;
    private CategoryAdapter categoryAdapter;
    @BindView(R.id.tv_next)
    TextView txtNext;
    private String fromWhichScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_choose_category);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            childrenItemArrayList = getIntent().getParcelableArrayListExtra("CategoryList");
            fromWhichScreen = getIntent().getStringExtra("FromScreen");
            Log.e("categoryLis", "" + childrenItemArrayList.size());
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCategories.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(this, childrenItemArrayList);
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    @OnClick(R.id.tv_next)
    void onCLickNext() {
        Log.e("GETSIZE", ":" + subCategoriesListChildern.size());
        if (subCategoriesListChildern.isEmpty()) {
            Toast.makeText(this, "No Sub Categories Available", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!multiCheckGenreArrayList.isEmpty())
            multiCheckGenreArrayList.clear();
        if (!singleCheckGenres.isEmpty())
            singleCheckGenres.clear();
        for (int i = 0; i < subCategoriesListChildern.size(); i++) {
            MultiCheckGenre multiCheckGenre = new MultiCheckGenre(subCategoriesListChildern.get(i).getName(),
                    subCategoriesListChildern.get(i).getServices());
            SingleCheckGenre singleCheckGenre = new SingleCheckGenre(subCategoriesListChildern.get(i).getName(),
                    subCategoriesListChildern.get(i).getServices());
            multiCheckGenreArrayList.add(multiCheckGenre);
            singleCheckGenres.add(singleCheckGenre);
        }
        try {
            Intent intent = new Intent(this, SubCategoryActivity.class);
            if (!TextUtils.isEmpty(fromWhichScreen)) {
                intent.putParcelableArrayListExtra("SingleSubCategoryList", (ArrayList<? extends Parcelable>) singleCheckGenres);
            } else
                intent.putParcelableArrayListExtra("SubCategoryList", (ArrayList<? extends Parcelable>) multiCheckGenreArrayList);
            Log.e("ListASize", "" + multiCheckGenreArrayList.size());
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(CategoryListActivity.this, "No services available", Toast.LENGTH_SHORT).show();
        }

    }

    public void onSubCategoryClicked(List<ChildrenItem> subChildrenItemList, int type) {
        if (type == 1) {
            subCategoriesListChildern.addAll(subChildrenItemList);
        } else if (type == 2) {
            if (subCategoriesListChildern.containsAll(subChildrenItemList)) {
                subCategoriesListChildern.removeAll(subChildrenItemList);
            }
        }
        HashSet<ChildrenItem> hashSet = new HashSet<ChildrenItem>();
        hashSet.addAll(subCategoriesListChildern);
        subCategoriesListChildern.clear();
        subCategoriesListChildern.addAll(hashSet);
    }

    public void onClickCalled(List<ChildrenItem> childrenItemList) {
        // Call another acitivty here and pass some arguments to it.
        if (!subCategoriesList.isEmpty())
            subCategoriesList.clear();
        if (!subCategoriesListChildern.isEmpty())
            subCategoriesListChildern.clear();
        subCategoriesList.addAll(childrenItemList);
        HashSet<ChildrenItem> hashSet = new HashSet<ChildrenItem>();
        hashSet.addAll(subCategoriesList);
        subCategoriesList.clear();
        subCategoriesList.addAll(hashSet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSubCategories.setLayoutManager(layoutManager);
        recyclerViewSubCategories.setAdapter(new SubCategoryAdapter(this, subCategoriesList));
    }

    public void notifyAdapter() {
        recyclerViewCategories.post(() -> categoryAdapter.notifyDataSetChanged());
    }
}
