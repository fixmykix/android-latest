package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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

public class SignUpActivity extends Activity {
    @BindView(R.id.input_text_signup_first_name)
    EditText etFirstName;

    @BindView(R.id.input_text_signup_last_name)
    EditText etLastName;

    @BindView(R.id.input_text_signup_email)
    EditText etEmail;

    @BindView(R.id.input_text_signup_password)
    EditText etPassword;

    @BindView(R.id.input_text_signup_password_confirm)
    EditText etPasswordConfirm;

    @BindView(R.id.tv_signup_costomer)
    TextView tvCustomer;

    @BindView(R.id.tv_signup_artist)
    TextView tvArtist;

    int selectedRole = Constants.ROLE_CUSTOMER;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_sign_up_activity);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.tv_signup_costomer)
    void onClickCustomer() {
        selectedRole = Constants.ROLE_CUSTOMER;
        tvCustomer.setTextAppearance(R.style.TextStyleBlueButtonBlue);
        tvArtist.setTextAppearance(R.style.TextStyleBorder);
        tvCustomer.setBackground(getResources().getDrawable(R.drawable.bg_boutton_blue_rectangle));
        tvArtist.setBackground(getResources().getDrawable(R.drawable.bg_boutton_white));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.tv_signup_artist)
    void onClickArtist() {
        selectedRole = Constants.ROLE_ARTIST;
        tvArtist.setTextAppearance(R.style.TextStyleBlueButtonBlue);
        tvCustomer.setTextAppearance(R.style.TextStyleBorder);
        tvArtist.setBackground(getResources().getDrawable(R.drawable.bg_boutton_blue_rectangle));
        tvCustomer.setBackground(getResources().getDrawable(R.drawable.bg_boutton_white));
    }

    @OnClick(R.id.tv_signup_done)
    void onClickDoneSignup() {
        signup();
    }

    private void signup() {
        if (TextUtils.isEmpty(etFirstName.getText().toString())) {
            etFirstName.setError(getString(R.string.enter_first_name));
            return;
        }
        if (TextUtils.isEmpty(etLastName.getText().toString())) {
            etLastName.setError(getString(R.string.enter_last_name));
            return;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError(getString(R.string.enter_email));
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(getString(R.string.enter_password));
            return;
        }
        if (etPassword.getText().length() < 6) {
            etPassword.setError("Password should have at least 6 characters.");
            return;
        }
        if (TextUtils.isEmpty(etPasswordConfirm.getText().toString())) {
            etPasswordConfirm.setError(getString(R.string.confirm_password));
            return;
        }
        if (!TextUtils.equals(etPassword.getText(), etPasswordConfirm.getText().toString())) {
            Toast.makeText(SignUpActivity.this, "Password do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        etFirstName.setError(null);
        etLastName.setError(null);
        etEmail.setError(null);
        etPassword.setError(null);
        etPasswordConfirm.setError(null);

        KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(SignUpActivity.this);
        kProgressHUD.show();

        Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                signup(etEmail.getText().toString(),
                        etPassword.getText().toString(),
                        etPasswordConfirm.getText().toString(),
                        selectedRole);
        signup.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                if (response.code() != 200 && response.code() != 201) {
                    try {
                        Toast.makeText(SignUpActivity.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String userToken = response.headers().get("X-User-Token");
                        LocalStorage.setString(SignUpActivity.this, LocalStorage.X_USER_TOKEN, userToken);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getBoolean("status")) {
                            LocalStorage.setString(SignUpActivity.this, LocalStorage.USER_DETAIL, String.valueOf(jsonObject.getJSONObject("data")));

                            if (CommonUtils.getUserData(SignUpActivity.this).getRole() == Constants.ROLE_ARTIST) {
                                startActivity(new Intent(SignUpActivity.this, ActivityArtistDetail.class));
                            } else {
                                startActivity(new Intent(SignUpActivity.this, ActivityUserDetail.class));
                            }
                            Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            finishAffinity();
                        } else
                            Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(SignUpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setListeners() {
        /*etLoginPhoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signup();
                    return true;
                }
                return false;
            }
        });*/
    }

    /*@RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.tv_login_phone_number)
    void onClickNumberChooseTv() {
        String number = CommonUtils.getPhoneNumber(SignUpActivity.this);
        if (TextUtils.isEmpty(number)) {
            etLoginPhoneNumber.setVisibility(View.VISIBLE);
            tvLoginPhoneNumber.setVisibility(View.GONE);
            etLoginPhoneNumber.requestFocus();
        } else {
            openChooseNumberDialog(number);
        }
    }*/

    /*private void openChooseNumberDialog(String pickedNumber) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_select_phone_number);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvPickedNumber = dialog.findViewById(R.id.tv_select_phone_number_picked_no);
        tvPickedNumber.setText(pickedNumber);
        dialog.findViewById(R.id.tv_select_phone_choose_another).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etLoginPhoneNumber.setVisibility(View.VISIBLE);
                tvLoginPhoneNumber.setVisibility(View.GONE);
                etLoginPhoneNumber.requestFocus();
                dialog.dismiss();
            }
        });

        tvPickedNumber.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                LocalStorage.setString(SignUpActivity.this, LocalStorage.LOGGED_IN_MOBILE, pickedNumber);
                dialog.dismiss();
                startActivity(new Intent(SignUpActivity.this, VerifyOtpActivity.class));
                finishAffinity();
            }
        });

        dialog.show();
    }

    private void signup() {
        if (TextUtils.isEmpty(etLoginPhoneNumber.getText().toString())) {
            etLoginPhoneNumber.setError(getString(R.string.enter_mobile_no));
            return;
        }
        if (etLoginPhoneNumber.getText().toString().length() < 10) {
            etLoginPhoneNumber.setError(getString(R.string.enter_valid_mobile_no));
            return;
        }
        etLoginPhoneNumber.setError(null);

        KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(SignUpActivity.this);
        kProgressHUD.show();

        Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                signup(etLoginPhoneNumber.getText().toString());
        signup.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                LocalStorage.setString(SignUpActivity.this, LocalStorage.LOGGED_IN_MOBILE, etLoginPhoneNumber.getText().toString());
                kProgressHUD.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    if (jsonObject.getBoolean("status")) {

                        Gson gson = new Gson();

                        Type type = new TypeToken<ResponseSignup>() {
                        }.getType();
                        ResponseSignup responseSignup = gson.fromJson(String.valueOf(jsonObject), type);

                        *//*Type = 0 successful login
                        Type = 1 mobile not verifed otp screen
                                Type = 2 update password*//*

                        if (responseSignup.getType() == 1) {
                            Intent intent = new Intent(SignUpActivity.this, VerifyOtpActivity.class);
                            intent.putExtra(Constants.KEY_PHONE_NUMBER, etLoginPhoneNumber.getText().toString());
                            startActivity(intent);
                        } else if (responseSignup.getType() == 2) {
                            Intent intent = new Intent(SignUpActivity.this, ActivityUpdatePassword.class);
                            intent.putExtra(Constants.KEY_PHONE_NUMBER, etLoginPhoneNumber.getText().toString());
                            startActivity(intent);
                        } else if(responseSignup.getType() == 0) {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        finishAffinity();
                    } else
                        Toast.makeText(SignUpActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(SignUpActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick(R.id.tv_signup_login)
    void onClickLogin() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finishAffinity();
    }

    @OnClick(R.id.iv_back_signup)
    void onClickBack() {
        onBackPressed();
    }
}
