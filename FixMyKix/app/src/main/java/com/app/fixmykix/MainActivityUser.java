package com.app.fixmykix;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.fixmykix.activities.ActivityAboutUs;
import com.app.fixmykix.activities.ActivityArtistList;
import com.app.fixmykix.activities.ActivityCart;
import com.app.fixmykix.activities.ActivityContactUs;
import com.app.fixmykix.activities.ActivityUserOrders;
import com.app.fixmykix.activities.ActiviytSearchService;
import com.app.fixmykix.activities.LoginActivity;
import com.app.fixmykix.adapters.AdapterDrawerItem;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.DrawerItem;
import com.app.fixmykix.model.ShopingCategory;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.ui.home.ServiceListFragment;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityUser extends AppCompatActivity {

    @BindView(R.id.drawer_layout_user)
    DrawerLayout drawer;

    @BindView(R.id.nav_view_user)
    NavigationView navigationView;

    @BindView(R.id.elv_drawer_items)
    ExpandableListView elvDrawerItems;

    @BindView(R.id.user_select_services)
    TextView tvSelectService;

    ArrayList<String> selectedServiceIds = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        ButterKnife.bind(this);
        initView();
        getDrawerCategoryData();
    }

    private void setListener(ArrayList<DrawerItem> drawerItemArrayList) {
        elvDrawerItems.setAdapter(new AdapterDrawerItem(this, drawerItemArrayList));
        elvDrawerItems.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                switch (groupPosition) {
                    case 0:
                        if (drawer.isDrawerOpen(GravityCompat.START))
                            drawer.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (drawer.isDrawerOpen(Gravity.LEFT)) {
                            drawer.closeDrawer(Gravity.LEFT);
                        }
                        startActivity(new Intent(MainActivityUser.this, ActivityUserOrders.class));
                        break;
                    case 2:
                        if (drawer.isDrawerOpen(Gravity.LEFT)) {
                            drawer.closeDrawer(Gravity.LEFT);
                        }
                        startActivity(new Intent(MainActivityUser.this, ActivityAboutUs.class));
                        break;
                    case 3:
                        if (drawer.isDrawerOpen(Gravity.LEFT)) {
                            drawer.closeDrawer(Gravity.LEFT);
                        }
                        startActivity(new Intent(MainActivityUser.this, ActivityContactUs.class));
                        break;
                }
                return false;
            }
        });

        elvDrawerItems.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long id) {
                if (groupPosition == 1 /*&& drawerItemList.get(groupPosition).getCategoryList() != null && drawerItemList.get(groupPosition).getCategoryList().size() > 0*/) {

                }
                return false;
            }
        });
    }

    protected static void addFragment(FragmentManager fragmentManager, @IdRes int containerViewId,
                                      @NonNull Fragment fragment,
                                      @NonNull String fragmentTag) {
        fragmentManager
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }


    private void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_layout_user, new ServiceListFragment()).commit();
    }

    @OnClick(R.id.iv_search_user)
    void onClickSearch() {
        startActivity(new Intent(this, ActiviytSearchService.class));
    }

    @OnClick(R.id.iv_add_cart)
    void onCartItems() {
        startActivity(new Intent(this, ActivityCart.class));
    }

    @OnClick(R.id.iv_hamburger_user)
    void onClickHamburger() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }
    }

    @OnClick(R.id.user_logout)
    void onClickArtistLogout() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        logouitPos();
    }
/*
    @OnClick(R.id.drawer_user_my_order)
    void onClickMyOrder() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityUserOrders.class));
    }

    @OnClick(R.id.drawer_user_about_us)
    void onClickAboutUs() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityAboutUs.class));
    }


    @OnClick(R.id.drawer_user_contact_us)
    void onClickContactUs() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityContactUs.class));
    }*/

    private void logouitPos() {
        new IOSDialog.Builder(this).setTitle("FixMyKix")
                .setMessage("Do you want to logout ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

    private void logout() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait......!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                LocalStorage.clearPreference(MainActivityUser.this);
                progressDialog.dismiss();
                startActivity(new Intent(MainActivityUser.this, LoginActivity.class));
                finishAffinity();
            }
        }, 1000);
    }

    private ArrayList<DrawerItem> getDrawerItem(ArrayList<ShopingCategory> categoryList) {
        ArrayList<DrawerItem> drawerItemArrayList = new ArrayList<>();
        for (int index = 0; index < Constants.DRAWER_ITEM_TEXT.length; index++) {
            DrawerItem drawerItem = new DrawerItem();
            drawerItem.setName(Constants.DRAWER_ITEM_TEXT[index]);
            if (index == 4) {
                drawerItem.setCategoryList(categoryList);
            }
            drawerItemArrayList.add(drawerItem);
        }
        return drawerItemArrayList;
    }

    void getDrawerCategoryData() {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(MainActivityUser.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServiceWithCategory(LocalStorage.getString(MainActivityUser.this, LocalStorage.X_USER_TOKEN, "")).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                switch (response.code()) {
                    case Constants.SUCCESS_CODE:
                    case Constants.SUCCESS_CODE_SECOND:
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.d("JsonObject Response", jsonObject.toString());
                            if (jsonObject.getBoolean("status")) {
                                JSONObject jsonObjectMain = jsonObject.getJSONObject("data");
                                JSONArray jsonArrayCategory = jsonObjectMain.getJSONArray("categories");
                                ArrayList<ShopingCategory> shopingCategories = new ArrayList<>();
                                if (jsonArrayCategory != null && jsonArrayCategory.length() > 0) {
                                    for (int index = 0; index < jsonArrayCategory.length(); index++) {
                                        shopingCategories.add(new Gson().fromJson(jsonArrayCategory.get(index).toString(), ShopingCategory.class));
                                    }
                                    ArrayList<DrawerItem> drawerItem = getDrawerItem(shopingCategories);
                                    setListener(drawerItem);
                                }
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case Constants.ERROR_CODE_INVALID:
                        finishAffinity();
                        startActivity(new Intent(MainActivityUser.this, LoginActivity.class));
                        break;
                    default:
                        try {
                            Toast.makeText(MainActivityUser.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                }

                try {
                    /*JSONObject jsonObject = JsonUtil.mainjson(response);
                    if (jsonObject.getInt(JsonUtil.STATUS) == 1) {
                        String jsonStringMainCategoryList = jsonObject.getString("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<SubCategoriesModel>>() {
                        }.getType();
                        ArrayList<SubCategoriesModel> list = gson.fromJson(jsonStringMainCategoryList, type);
                        setDrawerCatListAdapter(list);
                    } else if (jsonObject.getInt(JsonUtil.STATUS) == 0) {
                        Toast.makeText(NewMainActivity.this, jsonObject.getString(JsonUtil.MESSAGE), Toast.LENGTH_SHORT).show();
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void selectService(String id) {
        if (!selectedServiceIds.contains(id)) {
            selectedServiceIds.add(id);
        }
        if (selectedServiceIds != null && selectedServiceIds.size() > 0)
            tvSelectService.setVisibility(View.VISIBLE);
    }

    public void removeServiceFromList(String id) {
        selectedServiceIds.remove(id);

        if (selectedServiceIds.size() == 0)
            tvSelectService.setVisibility(View.GONE);
    }

    @OnClick(R.id.user_select_services)
    void onClickSelectServices() {
        Intent intent = new Intent(this, ActivityArtistList.class);
        intent.putExtra(Constants.KEY_SERVICE_IDS, selectedServiceIds);
        startActivity(intent);
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
    }
}
