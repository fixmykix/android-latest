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

import com.app.fixmykix.MainActivityArtist;
import com.app.fixmykix.MainActivityUser;
import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
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

public class LoginActivity extends Activity {
    @BindView(R.id.input_text_login_email)
    EditText etUserName;

    @BindView(R.id.input_text_login_password)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_login_create_new_account)
    void onClickLogin() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    @OnClick(R.id.tv_login_done)
    void onClickDone() {
        if (TextUtils.isEmpty(etUserName.getText())) {
            etUserName.setError("Enter your email");
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError("Enter user password");
            return;
        }

        etUserName.setError(null);
        etPassword.setError(null);
        login();
    }

    private void login() {
        final KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(LoginActivity.this);
        kProgressHUD.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                login(etUserName.getText().toString(), etPassword.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                if (response.code() != 200 && response.code() != 201) {
                    try {
                        Toast.makeText(LoginActivity.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String userToken = response.headers().get("X-User-Token");
                        LocalStorage.setString(LoginActivity.this, LocalStorage.X_USER_TOKEN, userToken);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getBoolean("status")) {
                            LocalStorage.setString(LoginActivity.this, LocalStorage.USER_DETAIL, jsonObject.getJSONObject("data").toString());
//                        LocalStorage.setString(LoginActivity.this, LocalStorage.USER_DETAIL, gson.toJson(userDetail));

                            LocalStorage.setBoolean(LoginActivity.this, LocalStorage.IS_USER_LOGGED_IN, true);
                            if (CommonUtils.getUserData(LoginActivity.this).getRole() == Constants.ROLE_ARTIST)
                                startActivity(new Intent(LoginActivity.this, MainActivityArtist.class));
                            else
                                startActivity(new Intent(LoginActivity.this, MainActivityUser.class));

                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        } else
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_back_login)
    void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_login_forget_passoword)
    void onClickForgetPassword() {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }
}
