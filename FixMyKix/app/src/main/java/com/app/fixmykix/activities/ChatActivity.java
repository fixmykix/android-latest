package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    private void getRequests() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ChatActivity.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServiceRequests(LocalStorage.getString(ChatActivity.this, LocalStorage.X_USER_TOKEN, ""))
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
                                        }
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ChatActivity.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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
