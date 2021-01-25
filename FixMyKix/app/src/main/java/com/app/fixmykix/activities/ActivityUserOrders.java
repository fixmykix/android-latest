package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterArtist;
import com.app.fixmykix.adapters.AdapterUserOrder;
import com.app.fixmykix.adapters.AdapterWhatIsService;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.Artist;
import com.app.fixmykix.model.ServiceRequest;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

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

public class ActivityUserOrders extends Activity {
    @BindView(R.id.rv_my_order)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order);
        ButterKnife.bind(this);
        getRequests();
    }

    @OnClick(R.id.iv_back_my_order)
    void onClickBack() {
        onBackPressed();
    }

    private void getRequests() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityUserOrders.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServiceRequests(LocalStorage.getString(ActivityUserOrders.this, LocalStorage.X_USER_TOKEN, ""))
                .enqueue(new Callback<ResponseBody>() {
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
                                        JSONObject jsosMain = jsonObject.getJSONObject("data");
                                        JSONArray jsonArrayServiceRequest = jsosMain.getJSONArray("service_requests");
                                        if (jsonArrayServiceRequest != null) {
                                            ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();
                                            for (int index = 0; index < jsonArrayServiceRequest.length(); index++) {
                                                serviceRequests.add(new Gson().fromJson(jsonArrayServiceRequest.getJSONObject(index).toString(), ServiceRequest.class));
                                            }
                                            setView(serviceRequests);
                                        }
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityUserOrders.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityUserOrders.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    private void setView(ArrayList<ServiceRequest> serviceRequests) {
        if (serviceRequests != null && serviceRequests.size() > 0) {
            recyclerView.setAdapter(new AdapterUserOrder(ActivityUserOrders.this, serviceRequests));
            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityUserOrders.this, RecyclerView.VERTICAL, false));
        }
    }

    public void deleteRequest(String serviceRequestId) {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityUserOrders.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                changeRequestStatus(LocalStorage.getString(ActivityUserOrders.this, LocalStorage.X_USER_TOKEN, ""), "v1/service_requests/" + serviceRequestId + "/cancel")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getBoolean("status")) {
                                        getRequests();
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityUserOrders.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityUserOrders.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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
