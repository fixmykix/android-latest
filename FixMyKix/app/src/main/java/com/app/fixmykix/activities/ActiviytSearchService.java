package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterSearchService;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiviytSearchService extends Activity {
    @BindView(R.id.rv_search_service)
    RecyclerView rvServices;

    @BindView(R.id.et_search_service)
    EditText etSearch;

    ArrayList<Service> services;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service);
        ButterKnife.bind(this);
        setListener();

        getServices();
    }

    private void setListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterList(editable.toString());
            }
        });
    }

    @OnClick(R.id.iv_back_add_search_service)
    void onClickBackSearch() {
        onBackPressed();
    }

    private void getServices() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActiviytSearchService.this);
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
                                    if (jsonObject.getInt("status") == 200) {
                                        JSONArray jsonObjectMain = jsonObject.getJSONArray("data");
                                        if (jsonObjectMain != null) {
                                            services = new ArrayList<>();
                                            for (int i = 0; i < jsonObjectMain.length(); i++) {
                                                services.add(new Gson().fromJson(jsonObjectMain.getJSONObject(i).toString(), Service.class));
                                            }
                                            rvServices.setAdapter(new AdapterSearchService(ActiviytSearchService.this, services));
                                            rvServices.setLayoutManager(new LinearLayoutManager(ActiviytSearchService.this, RecyclerView.VERTICAL, false));
                                        }
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActiviytSearchService.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActiviytSearchService.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    private void filterList(String text) {
        if (services != null && services.size() > 0) {
            ArrayList<Service> filteredList = new ArrayList<>();

            for (Service document : services) {
                if (document.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(document);
                }
            }
            if (rvServices.getAdapter() == null) {
                rvServices.setAdapter(new AdapterSearchService(ActiviytSearchService.this, filteredList));
            }
            ((AdapterSearchService) rvServices.getAdapter()).filterList(filteredList);
        }
    }
}
