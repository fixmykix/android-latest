package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AddressRecyclerAdapter;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.AddressDataItem;
import com.app.fixmykix.model.GetAddressListResponse;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

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

public class ActivityAddAddress extends Activity {

    @BindView(R.id.tv_add_address)
    TextView txtAddress;

    @BindView(R.id.rv_address)
    RecyclerView rvAddress;

    private AddressRecyclerAdapter addressRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_add_address);
        ButterKnife.bind(this);
        getServices();
    }

    @OnClick(R.id.tv_add_address)
    void onCLickAddress() {
        showDialogAddress(null);
    }

    @OnClick(R.id.iv_back_about_us)
    void onCLickBack() {
        finish();
    }

    void onEditAddress(int id, String street, String city, String state, String postalcode) {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(this);
        progressDialog.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                editAddresses(id, LocalStorage.getString(this, LocalStorage.X_USER_TOKEN, ""), street,
                        city, state, postalcode);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    // user detail got
                    if (response != null && response.body() != null) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.d("JsonObject Response", jsonObject.toString());
                        getServices();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityAddAddress.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialogAddress(AddressDataItem addressDataItem) {
        final Dialog dialog = new Dialog(this, R.style.WideDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.address_add);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.tv_save);
        EditText edtStreet = (EditText) dialog.findViewById(R.id.et_add_street);
        EditText edtCity = (EditText) dialog.findViewById(R.id.et_add_city);
        EditText edtState = (EditText) dialog.findViewById(R.id.et_add_state);
        EditText edtZip = (EditText) dialog.findViewById(R.id.et_postalcode);
        if (addressDataItem != null) {
            edtStreet.setText(addressDataItem.getStreet());
            edtCity.setText(addressDataItem.getCity());
            edtState.setText(addressDataItem.getState());
            edtZip.setText(addressDataItem.getZip());

        }

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addressDataItem != null && validateAddress()) {
                    onEditAddress(addressDataItem.getId(), edtStreet.getText().toString(), edtCity.getText().toString(),
                            edtState.getText().toString(), edtZip.getText().toString());
                } else {

                    if (validateAddress()) {
                        onSaveAddress(edtStreet.getText().toString(), edtCity.getText().toString(),
                                edtState.getText().toString(), edtZip.getText().toString());
                    }
                }
                dialog.dismiss();
            }

            private boolean validateAddress() {
                if (TextUtils.isEmpty(edtStreet.getText().toString())) {
                    Toast.makeText(ActivityAddAddress.this, "Enter street", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(edtCity.getText().toString())) {
                    Toast.makeText(ActivityAddAddress.this, "Enter city", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(edtState.getText().toString())) {
                    Toast.makeText(ActivityAddAddress.this, "Enter state", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(edtZip.getText().toString())) {
                    Toast.makeText(ActivityAddAddress.this, "Enter zip code", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });

        dialog.show();

    }

    void onSaveAddress(String street, String city, String state, String zip) {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(this);
        progressDialog.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                saveAddresses(LocalStorage.getString(this, LocalStorage.X_USER_TOKEN, ""), street, city, state, zip);
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                try {
                    // user detail got
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("JsonObject Response", jsonObject.toString());
                    getServices();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ActivityAddAddress.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getServices() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getAddresses(LocalStorage.getString(this, LocalStorage.X_USER_TOKEN, "")).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                progressDialog.dismiss();
                switch (response.code()) {
                    case Constants.SUCCESS_CODE:
                    case Constants.SUCCESS_CODE_SECOND:
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.d("JsonObject Response", jsonObject.toString());
                            Gson gson = new Gson();
                            GetAddressListResponse getAddressListResponse = gson.fromJson(jsonObject.toString(), GetAddressListResponse.class);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityAddAddress.this);
                            rvAddress.setLayoutManager(layoutManager);
                            addressRecyclerAdapter = new AddressRecyclerAdapter(
                                    ActivityAddAddress.this, getAddressListResponse.getData());
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvAddress.getContext(),
                                    layoutManager.getOrientation());
                            rvAddress.addItemDecoration(dividerItemDecoration);
                            rvAddress.setAdapter(addressRecyclerAdapter);

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case Constants.ERROR_CODE_INVALID:
                        finishAffinity();
                        startActivity(new Intent(ActivityAddAddress.this, LoginActivity.class));
                        break;
                    default:
                        try {
                            Toast.makeText(ActivityAddAddress.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
