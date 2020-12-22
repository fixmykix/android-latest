package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AddressRecyclerAdapter;
import com.app.fixmykix.adapters.CartItemsAdapter;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.GetCartItemsResponse;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCart extends Activity {


    @BindView(R.id.orderid)
    TextView txtOrderNo;

    @BindView(R.id.orderupsstatus)
    TextView txtOrderStatus;

    @BindView(R.id.rc_cart_added_services)
    RecyclerView rvCartItems;

    private CartItemsAdapter cartItemsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        getCartItems();
    }

    private void getCartItems() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityCart.this);
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).
                getCartItems(LocalStorage.getString(ActivityCart.this, LocalStorage.X_USER_TOKEN, ""))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    Log.e("GetCartItems", jsonObject.toString());
                                    Gson gson = new Gson();
                                    GetCartItemsResponse getCartItemsResponse =
                                            gson.fromJson(jsonObject.toString(), GetCartItemsResponse.class);
                                    txtOrderNo.setText("Order Number: \n \n" + getCartItemsResponse.getData().getOrderNumber());
                                    txtOrderStatus.setText("UPS Status: \n \n" + getCartItemsResponse.getData().getUpsStatus());
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityCart.this);
                                    rvCartItems.setLayoutManager(layoutManager);
                                    cartItemsAdapter = new CartItemsAdapter(
                                            ActivityCart.this, getCartItemsResponse.getData().getOrderDetailsItems());
                                    rvCartItems.setAdapter(cartItemsAdapter);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityCart.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityCart.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }
}
