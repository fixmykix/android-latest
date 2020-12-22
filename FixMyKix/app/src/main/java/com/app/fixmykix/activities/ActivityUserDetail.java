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

import com.app.fixmykix.MainActivityUser;
import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
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

public class ActivityUserDetail extends Activity {
    @BindView(R.id.user_first_name)
    EditText name;

    @BindView(R.id.et_user_email)
    EditText email;

    @BindView(R.id.et_muser_mobile)
    EditText mobile;

    @BindView(R.id.et_user_phone_number)
    EditText phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_user_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_back_user_detail)
    void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_submit_user_detail)
    void onClickArtistDone() {

        if (TextUtils.isEmpty(name.getText().toString())) {
            name.setError("Enter Name");
            return;
        }
        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Enter Email");
            return;
        }
        if (TextUtils.isEmpty(mobile.getText().toString())) {
            mobile.setError("Enter Mobile number");
            return;
        }
        name.setError(null);
        email.setError(null);
        mobile.setError(null);
        addDetails();
    }

    private void addDetails() {
        KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(ActivityUserDetail.this);
        kProgressHUD.show();

        Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                addUserDetail(LocalStorage.getString(ActivityUserDetail.this, LocalStorage.X_USER_TOKEN, ""),
                        String.format("v1/users/%d", CommonUtils.getUserData(ActivityUserDetail.this).getId()),
                        email.getText().toString(),
                        mobile.getText().toString(),
                        TextUtils.isEmpty(phone.getText()) ? "" : phone.getText().toString(),
                        name.getText().toString());
        signup.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                if (response.code() != 200 && response.code() != 201) {
                    try {
                        Toast.makeText(ActivityUserDetail.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getBoolean("status")) {
//                            LocalStorage.setString(ActivityArtistDetail.this, LocalStorage.USER_DETAIL, String.valueOf(jsonObject.getJSONObject("data")));
                            startActivity(new Intent(ActivityUserDetail.this, MainActivityUser.class));
                            Toast.makeText(ActivityUserDetail.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        } else
                            Toast.makeText(ActivityUserDetail.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(ActivityUserDetail.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
