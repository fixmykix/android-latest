package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterSelectService;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiviytSelectService extends Activity {
    @BindView(R.id.rv_select_service)
    RecyclerView rvServices;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);
        ButterKnife.bind(this);
        getServices();
    }

    private void getServices() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActiviytSelectService.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServices().
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    if (jsonObject.getBoolean("status")) {
                                        JSONArray jsonObjectMain = jsonObject.getJSONArray("data");
                                        if (jsonObjectMain != null) {
                                            ArrayList<Service> services = new ArrayList<>();
                                            for (int i = 0; i < jsonObjectMain.length(); i++) {
                                                services.add(new Gson().fromJson(jsonObjectMain.getJSONObject(i).toString(), Service.class));
                                            }
                                            rvServices.setAdapter(new AdapterSelectService(ActiviytSelectService.this, services));
                                            rvServices.setLayoutManager(new LinearLayoutManager(ActiviytSelectService.this, RecyclerView.VERTICAL, false));
                                        }
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActiviytSelectService.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActiviytSelectService.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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
