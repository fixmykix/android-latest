package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.fixmykix.MainActivityUser;
import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityRequestOrder extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_order);
        ButterKnife.bind(this);
        addAdressToOrder();
    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    @OnClick(R.id.iv_back_my_order)
    void onClickPay() {
//        startActivity(new Intent(ActivityRequestOrder.this, ActivityConfirmOrder.class));
        startActivity(new Intent(ActivityRequestOrder.this, MainActivityUser.class));
        finishAffinity();
    }

    void addAdressToOrder() {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityRequestOrder.this);
        progressDialog.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                addOrderDemand(LocalStorage.getString(ActivityRequestOrder.this, LocalStorage.X_USER_TOKEN, ""),
                        "v1/orders/" +
                                LocalStorage.getString(ActivityRequestOrder.this, LocalStorage.ORDER_ID, "")
                                + "/service_demand");
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    // user detail got
                    if (response != null && response.body() != null) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("JsonObject Response", jsonObject.toString());

                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityRequestOrder.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
