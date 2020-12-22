package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityUpdatePassword extends Activity {
    @BindView(R.id.input_text_update_password_pw)
    TextInputEditText etPassword;


    @BindView(R.id.input_text_update_password_confirm__password)
    TextInputEditText etConfirmPassword;
    private String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.KEY_PHONE_NUMBER)) {
            phoneNumber = extras.getString(Constants.KEY_PHONE_NUMBER);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.tv_update_password_done)
    void onClickUpdatePassword() {

        final KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(ActivityUpdatePassword.this);
        kProgressHUD.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                updatePassword(phoneNumber,
                        Objects.requireNonNull(etPassword.getText()).toString());
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                try {
                    // user detail got
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("status")) {
                        startActivity(new Intent(ActivityUpdatePassword.this, LoginActivity.class));
                        finish();
                    }
                    Toast.makeText(ActivityUpdatePassword.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(ActivityUpdatePassword.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_back_update_password)
    void onClickBack() {
        onBackPressed();
    }
}
