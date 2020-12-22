package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends Activity {
    @BindView(R.id.input_text_forget_password_username)
    EditText etEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pssword);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_forget_password_done)
    void onClickForgetPassword() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Enter email");
            return;
        }
        etEmail.setError(null);
        forgetPassword(etEmail.getText().toString());
    }

    void forgetPassword(String emaail) {
        final KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(ForgetPasswordActivity.this);
        kProgressHUD.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                forgetPassword(emaail);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Toast.makeText(ForgetPasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    /*Intent intent = new Intent(ForgetPasswordActivity.this, VerifyOtpActivity.class);
                    intent.putExtra(Constants.KEY_PHONE_NUMBER, numberForForgetPassword);
                    startActivity(intent);*/
                    finish();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(ForgetPasswordActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_back_forget_password)
    void onClickBackForgetPasword() {
        onBackPressed();
    }
}
