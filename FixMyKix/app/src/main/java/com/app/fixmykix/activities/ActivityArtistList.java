package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterArtist;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.Artist;
import com.app.fixmykix.model.ArtistService;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityArtistList extends Activity {
    @BindView(R.id.rv_artist_list)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(Constants.KEY_SERVICE_IDS)) {
            getArtist(extras.getStringArrayList(Constants.KEY_SERVICE_IDS));
        }
    }

    @OnClick(R.id.iv_back_artist_list)
    void onClickBack() {
        onBackPressed();
    }

    private void getArtist(ArrayList<String> serviceIds) {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ActivityArtistList.this);
        progressDialog.show();
        ApiClient.getClient().create(ApiInterface.class).
                getArtistByService(LocalStorage.getString(ActivityArtistList.this, LocalStorage.X_USER_TOKEN, ""), serviceIds.toArray())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    Log.e("ArtistResponse", jsonObject.toString());
                                    if (jsonObject.getBoolean("status")) {
                                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                                        if (jsonArray != null && jsonArray.length() > 0) {
                                            ArrayList<Artist> artists = new ArrayList<>();
                                            for (int index = 0; index < jsonArray.length(); index++) {
                                                artists.add(new Gson().fromJson(jsonArray.getJSONObject(index).toString(), Artist.class));
                                            }
                                            recyclerView.setAdapter(new AdapterArtist(ActivityArtistList.this, artists));
                                            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityArtistList.this, RecyclerView.VERTICAL, false));
                                        } else
                                            Toast.makeText(ActivityArtistList.this, "No Artist for this service", Toast.LENGTH_SHORT).show();

                                    } else
                                        Toast.makeText(ActivityArtistList.this, "No Artist for this service", Toast.LENGTH_SHORT).show();

                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ActivityArtistList.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ActivityArtistList.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_BOOK_SERVICE && resultCode == RESULT_OK) {
            finish();
        }
    }

}
