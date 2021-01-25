package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.stripe.android.Stripe;
import com.stripe.android.exception.APIConnectionException;
import com.stripe.android.exception.APIException;
import com.stripe.android.exception.AuthenticationException;
import com.stripe.android.exception.CardException;
import com.stripe.android.exception.InvalidRequestException;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

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

public class ActivityPayment extends Activity {

    @BindView(R.id.cardInputWidget)
    CardInputWidget cardInputWidget;
    Stripe stripe;
    String serviceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (getIntent().getExtras() != null) {
            serviceId = getIntent().getStringExtra("serviceID");
        }
        stripe = new Stripe(this,
                "pk_test_VkjodKoGO7d5zxsdV9wYWx11008HTX1bBB");


    }

    private void submitCard() {
        Token token = null;
        try {
            token = stripe.createCardTokenSynchronous(Objects.requireNonNull(cardInputWidget.getCardParams()));
        } catch (AuthenticationException | APIException | InvalidRequestException | APIConnectionException | CardException e) {
            e.printStackTrace();
        }
        assert token != null;
        Log.w("Sourc", "Sourc" + Objects.requireNonNull(token.getId()));
        onValidatePayment(token.getId());

    }

    void onValidatePayment(String cardtoken) {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(this);
        progressDialog.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                validatePayment(LocalStorage.getString(this, LocalStorage.X_USER_TOKEN, ""),
                        serviceId,
                        cardtoken, true);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                switch (response.code()) {
                    case Constants.SUCCESS_CODE:
                    case Constants.SUCCESS_CODE_SECOND:
                        try {
                            // user detail got
                            if (response != null && response.body() != null) {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Log.d("JsonObject Response", jsonObject.toString());
                                startActivity(new Intent(ActivityPayment.this, ActivityPaymentSuccess.class));
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        try {
                            Toast.makeText(ActivityPayment.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ActivityPayment.this, ActivityPaymentSuccess.class));
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            startActivity(new Intent(ActivityPayment.this, ActivityPaymentSuccess.class));
                        }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
               // Toast.makeText(ActivityPayment.this, t.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ActivityPayment.this, ActivityPaymentSuccess.class));
            }
        });
    }

    @OnClick(R.id.iv_back_payment)
    void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.payButton)
    void onPayCLick() {
        submitCard();
    }
}
