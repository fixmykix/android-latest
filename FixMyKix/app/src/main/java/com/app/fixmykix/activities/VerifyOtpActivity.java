package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
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

public class VerifyOtpActivity extends Activity {
    String phoneNumber;

    @BindView(R.id.et_activate_account_verification_1)
    EditText etActivateAccountVerification1;

    @BindView(R.id.et_activate_account_verification_2)
    EditText etActivateAccountVerification2;

    @BindView(R.id.et_activate_account_verification_3)
    EditText etActivateAccountVerification3;

    @BindView(R.id.et_activate_account_verification_4)
    EditText etActivateAccountVerification4;

    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);
        setListener();
        getData();
    }

    private void setListener() {
        etActivateAccountVerification1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() > 1) {
                        etActivateAccountVerification2.setText(String.valueOf(s.charAt(1)));
                        etActivateAccountVerification1.setText(String.valueOf(s.charAt(0)));
                    }
                    etActivateAccountVerification1.clearFocus();
                    etActivateAccountVerification2.requestFocus();
                    etActivateAccountVerification2.setSelection(etActivateAccountVerification2.getText().length());
                    etActivateAccountVerification2.setCursorVisible(true);
                } else if (s.length() == 0) {
                    etActivateAccountVerification1.requestFocus();
                    etActivateAccountVerification1.setCursorVisible(true);
                }
            }
        });
        etActivateAccountVerification2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() > 1) {
                        etActivateAccountVerification3.setText(String.valueOf(s.charAt(1)));
                        etActivateAccountVerification2.setText(String.valueOf(s.charAt(0)));
                    }
                    etActivateAccountVerification2.clearFocus();
                    etActivateAccountVerification3.requestFocus();
                    etActivateAccountVerification3.setSelection(etActivateAccountVerification3.getText().length());
                    etActivateAccountVerification3.setCursorVisible(true);
                } else if (s.length() == 0) {
                    etActivateAccountVerification2.clearFocus();
                    etActivateAccountVerification1.requestFocus();
                    etActivateAccountVerification1.setSelection(etActivateAccountVerification1.getText().length());
                    etActivateAccountVerification1.setCursorVisible(true);
                }
            }
        });
        etActivateAccountVerification3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() > 1) {
                        etActivateAccountVerification4.setText(String.valueOf(s.charAt(1)));
                        etActivateAccountVerification3.setText(String.valueOf(s.charAt(0)));
                    }
                    etActivateAccountVerification3.clearFocus();
                    etActivateAccountVerification4.requestFocus();
                    etActivateAccountVerification4.setSelection(etActivateAccountVerification4.getText().length());
                    etActivateAccountVerification4.setCursorVisible(true);

                } else if (s.length() == 0) {
                    etActivateAccountVerification3.clearFocus();
                    etActivateAccountVerification2.requestFocus();
                    etActivateAccountVerification2.setSelection(etActivateAccountVerification2.getText().length());
                    etActivateAccountVerification2.setCursorVisible(true);
                }
            }
        });
        etActivateAccountVerification4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (s.length() > 1)
                        etActivateAccountVerification4.setText(String.valueOf(s.charAt(0)));
                    etActivateAccountVerification4.clearFocus();
                    CommonUtils.hideKeyboard(VerifyOtpActivity.this);
                } else if (s.length() == 0) {
                    etActivateAccountVerification4.clearFocus();
                    etActivateAccountVerification3.requestFocus();
                    etActivateAccountVerification3.setSelection(etActivateAccountVerification3.getText().length());
                    etActivateAccountVerification3.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });/*
        etActivateAccountVerification5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() > 1) {
                        etActivateAccountVerification6.setText(String.valueOf(s.charAt(1)));
                        etActivateAccountVerification5.setText(String.valueOf(s.charAt(0)));
                    }
                    etActivateAccountVerification5.clearFocus();
                    etActivateAccountVerification6.requestFocus();
                    etActivateAccountVerification6.setSelection(etActivateAccountVerification6.getText().length());
                    etActivateAccountVerification6.setCursorVisible(true);
                } else if (s.length() == 0) {
                    etActivateAccountVerification5.clearFocus();
                    etActivateAccountVerification4.requestFocus();
                    etActivateAccountVerification4.setSelection(etActivateAccountVerification4.getText().length());
                    etActivateAccountVerification4.setCursorVisible(true);
                }
            }
        });
        etActivateAccountVerification6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (s.length() > 1)
                        etActivateAccountVerification6.setText(String.valueOf(s.charAt(0)));
                    etActivateAccountVerification6.clearFocus();
                    CommonUtils.hideKeyboard(VerifyOtpActivity.this);
                } else if (s.length() == 0) {
                    etActivateAccountVerification6.clearFocus();
                    etActivateAccountVerification5.requestFocus();
                    etActivateAccountVerification5.setSelection(etActivateAccountVerification5.getText().length());
                    etActivateAccountVerification5.setCursorVisible(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.KEY_PHONE_NUMBER)) {
            phoneNumber = extras.getString(Constants.KEY_PHONE_NUMBER);
        }
    }

    @OnClick(R.id.btn_done_otp)
    void onClickDoneOtp() {
        verifyOtp();
    }

    private void verifyOtp() {
        if (codeIsValid()) {
            final KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(VerifyOtpActivity.this);
            kProgressHUD.show();

            Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                    verifyOtp(phoneNumber, code);
            signup.enqueue(new Callback<ResponseBody>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    kProgressHUD.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getBoolean("status")) {
                            Intent intent = new Intent(VerifyOtpActivity.this, ActivityUpdatePassword.class);
                            intent.putExtra(Constants.KEY_PHONE_NUMBER, phoneNumber);
                            startActivity(intent);
                            finish();
                        } else
                            Toast.makeText(VerifyOtpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    kProgressHUD.dismiss();
                    Toast.makeText(VerifyOtpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "enter valid otp", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean codeIsValid() {
        if (!TextUtils.isEmpty(etActivateAccountVerification1.getText()) &&
                !TextUtils.isEmpty(etActivateAccountVerification2.getText()) &&
                !TextUtils.isEmpty(etActivateAccountVerification3.getText()) &&
                !TextUtils.isEmpty(etActivateAccountVerification4.getText())) {
            code = etActivateAccountVerification1.getText().toString().
                    concat(etActivateAccountVerification2.getText().toString().
                            concat(etActivateAccountVerification3.getText().toString().
                                    concat(etActivateAccountVerification4.getText().toString())));
        }
        if (!TextUtils.isEmpty(code) && code.length() == Constants.VALID_OTP_LENGTH) {
            return true;
        } else {
            return false;
        }
    }

    @OnClick(R.id.tv_resend_otp)
    void onClickResendOtp() {
        final KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(VerifyOtpActivity.this);
        kProgressHUD.show();

        Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                resendOtp(phoneNumber);
        signup.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("status")) {
                        Toast.makeText(VerifyOtpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(VerifyOtpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(VerifyOtpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_back_verify_otp)
    void onClickBack() {
        onBackPressed();
    }
}
