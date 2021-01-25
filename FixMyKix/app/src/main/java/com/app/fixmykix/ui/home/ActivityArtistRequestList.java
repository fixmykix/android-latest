package com.app.fixmykix.ui.home;

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
import com.app.fixmykix.activities.LoginActivity;
import com.app.fixmykix.adapters.AdapterArtistServiceRequest;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.ArtistServiceRequestResponse;
import com.app.fixmykix.model.ServiceRequestsItem;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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
                                    Log.d("JsonObject Response", jsonObject.toString());
                                    Gson gson = new Gson();
                                    ArtistServiceRequestResponse artistServiceRequestResponse =
                                            gson.fromJson(jsonObject.toString(), ArtistServiceRequestResponse.class);
                                    if (artistServiceRequestResponse.isStatus()) {
                                        setView(artistServiceRequestResponse.getServiceRequestsDataModel().getServiceRequests());
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

    private void setView(List<ServiceRequestsItem> serviceRequests) {
        if (serviceRequests != null && serviceRequests.size() > 0) {
            recyclerView.setAdapter(new AdapterArtistServiceRequest(this, serviceRequests));
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        }
    }

    public void declineRequest(int serviceRequestId) {
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