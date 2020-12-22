package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterArtist;
import com.app.fixmykix.adapters.AdapterSelectServiceOfArtist;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.Artist;
import com.app.fixmykix.model.ArtistService;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.app.fixmykix.utils.GlideUtils;
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

public class ActivityArtistServiceDetail extends Activity {

    @BindView(R.id.rv_artist_service)
    RecyclerView recyclerView;

    @BindView(R.id.tv_artist_service_artist_name)
    TextView tvArtistName;

    @BindView(R.id.iv_service_artist_service_detail)
    ImageView ivService;

    @BindView(R.id.tv_artist_service_desc)
    TextView tvDesc;

    @BindView(R.id.tv_artist_service_artist_address)
    TextView tvAddress;

    @BindView(R.id.tv_artist_service_artist_done)
    TextView tvAddToCart;


    ArrayList<String> selectedServiceIds = new ArrayList<>();

    private double selectedServiceAmount = 0.0;
    private double total = 0.0;
    private ArrayList<String> selectedServices = new ArrayList<>();

    String artistId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_and_service_detail);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Artist artist = extras.getParcelable(Constants.KEY_ARTIST);
            if (artist != null) {
                artistId = String.valueOf(artist.getId());
                tvArtistName.setText(artist.getFirstName());
                tvAddress.setText(artist.getAddress());
//                tvDesc.setText(artist.get);
                if (artist.getProfileImage() != null && !TextUtils.isEmpty(artist.getProfileImage().getUrl())) {
                    Log.e("ImageUrl", "" + artist.getProfileImage().getUrl());
                    GlideUtils.setImage(ActivityArtistServiceDetail.this, ivService, artist.getProfileImage().getUrl());
                }
                ArrayList<ArtistService> artistServices = artist.getArtistServices();
                if (artistServices != null) {
                    recyclerView.setAdapter(new AdapterSelectServiceOfArtist(this, artistServices));
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                }
            }
        }
    }

    private void addToCart(ArrayList<String> serviceIds) {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityArtistServiceDetail.this);
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).
                addToCart(LocalStorage.getString(ActivityArtistServiceDetail.this, LocalStorage.X_USER_TOKEN, ""), serviceIds.toArray())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    Log.e("AddToCartResponse", jsonObject.toString());
                                    if (jsonObject.optBoolean("status")) {
                                        Toast.makeText(ActivityArtistServiceDetail.this,
                                                jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                                        tvAddToCart.setText(getString(R.string.gotocart));
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityArtistServiceDetail.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityArtistServiceDetail.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.iv_back_artist_service_detail)
    void onclickBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_artist_service_artist_done)
    void onProceedForBookRequest() {
       /* if (tvAddToCart.getText().toString().equalsIgnoreCase(getString(R.string.gotocart))) {
            startActivity(new Intent(ActivityArtistServiceDetail.this, ActivityCart.class));
            return;
        }
        if (selectedServices.isEmpty()) {
            Toast.makeText(this, "Please select atleast one service", Toast.LENGTH_SHORT).show();
            return;
        }
        addToCart(selectedServiceIds);*/

        Intent intent = new Intent(this, ActivityServiceRequestForm.class);
        intent.putExtra(Constants.KEY_SERVICE_IDS, selectedServiceIds);
        intent.putExtra(Constants.KEY_ARTIST_ID, artistId);
        intent.putExtra("Total", total);
        Log.e("Total", "Total" + total);
        intent.putExtra("SelectedServices", selectedServices);
        startActivityForResult(intent, Constants.REQUEST_CODE_BOOK_SERVICE);
    }

    public void selectService(ArtistService artistService) {
        if (!selectedServiceIds.contains(String.valueOf(artistService.getId()))) {
            selectedServiceIds.add(String.valueOf(artistService.getId()));
            selectedServiceAmount = artistService.getPrice();
            total = selectedServiceAmount + total;
            selectedServices.add(artistService.getServiceName());
        }
    }

    public void removeServiceFromList(ArtistService artistService) {
        selectedServiceIds.remove(String.valueOf(artistService.getId()));
        selectedServiceAmount = artistService.getPrice();
        if (total > 0 && total >= selectedServiceAmount)
            total = Math.abs(total - selectedServiceAmount);
        selectedServices.remove(artistService.getServiceName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_BOOK_SERVICE && resultCode == RESULT_OK) {
            finish();
        }
    }
}
