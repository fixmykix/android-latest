package com.app.fixmykix.ui.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityAddService;
import com.app.fixmykix.activities.ActivityUserOrders;
import com.app.fixmykix.activities.LoginActivity;
import com.app.fixmykix.adapters.AdapterArtistServiceRequest;
import com.app.fixmykix.adapters.AdapterServiceHome;
import com.app.fixmykix.adapters.AdapterUserOrder;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.Service;
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

public class ActivityArtistRequestList extends Activity {

    @BindView(R.id.rv_requests)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_artist_request_list);
        ButterKnife.bind(this);
        getRequests();
    }

    @OnClick(R.id.iv_back_artist_request)
    void onClickBack() {
        onBackPressed();
    }

    private void getRequests() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityArtistRequestList.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServiceRequests(LocalStorage.getString(ActivityArtistRequestList.this, LocalStorage.X_USER_TOKEN, ""))
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
                                        JSONObject jsosMain = jsonObject.getJSONObject("data");
                                        JSONArray jsonArrayServiceRequest = jsosMain.getJSONArray("service_requests");
                                        if (jsonArrayServiceRequest != null) {
                                            ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();
                                            for (int index = 0; index < jsonArrayServiceRequest.length(); index++) {
                                                serviceRequests.add(new Gson().fromJson(jsonArrayServiceRequest.getJSONObject(index).toString(), ServiceRequest.class));
                                            }
                                            setView(serviceRequests);
                                        }
                                        Toast.makeText(ActivityArtistRequestList.this, "No Artist for this service", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityArtistRequestList.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityArtistRequestList.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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
            recyclerView.setAdapter(new AdapterArtistServiceRequest(this, serviceRequests));
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
    }

    public void declineRequest(String serviceRequestId) {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityArtistRequestList.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                changeRequestStatus(LocalStorage.getString(ActivityArtistRequestList.this, LocalStorage.X_USER_TOKEN, ""), "v1/service_requests/" + serviceRequestId + "/reject")
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
                                startActivity(new Intent(ActivityArtistRequestList.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityArtistRequestList.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ACCEPT_SERVICE && resultCode == RESULT_OK) {
            getRequests();
        }
    }
}