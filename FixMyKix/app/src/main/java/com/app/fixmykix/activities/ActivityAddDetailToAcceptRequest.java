package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.ServiceRequest;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.ui.home.ActivityArtistRequestList;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddDetailToAcceptRequest extends Activity {
    @BindView(R.id.et_request_price)
    EditText etPrice;

    @BindView(R.id.et_request_tat)
    EditText etTat;

    @BindView(R.id.et_request_desc)
    EditText etDesc;

    ServiceRequest serviceRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_params_to_accept_request);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.KET_SERVICE_REQUEST)) {
            serviceRequest = extras.getParcelable(Constants.KET_SERVICE_REQUEST);
        }
    }

    @OnClick(R.id.iv_back_add_param_to_accept)
    void onClickBack() {
        onBackPressed();
    }

    public void acceptRequest() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityAddDetailToAcceptRequest.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                acceptRequest(LocalStorage.getString(ActivityAddDetailToAcceptRequest.this, LocalStorage.X_USER_TOKEN, ""),
                        "v1/service_requests/" + String.valueOf(serviceRequest.getId()) + "/accept",
                        etDesc.getText().toString(),
                        etPrice.getText().toString(),
                        etTat.getText().toString())
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
                                        setResult(RESULT_OK);
                                        finish();
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityAddDetailToAcceptRequest.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityAddDetailToAcceptRequest.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.tv_accept_request_done)
    void onClickAcceptRequest() {
        if (TextUtils.isEmpty(etDesc.getText().toString())) {
            etDesc.setError("Enter Description");
            return;
        }

        if (TextUtils.isEmpty(etPrice.getText().toString())) {
            etPrice.setError("Enter Price");
            return;
        }
        if (TextUtils.isEmpty(etTat.getText().toString())) {
            etTat.setError("Enter Turn around time");
            return;
        }
        acceptRequest();
    }
}
