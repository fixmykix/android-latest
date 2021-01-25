package com.app.fixmykix.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityAddService;
import com.app.fixmykix.activities.ActiviytSearchService;
import com.app.fixmykix.activities.LoginActivity;
import com.app.fixmykix.adapters.AdapterServiceHome;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.ArtistServicesModelResponse;
import com.app.fixmykix.model.CategoriesItem;
import com.app.fixmykix.model.CategoriesResponse;
import com.app.fixmykix.model.Service;
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

import static android.app.Activity.RESULT_OK;

public class ArtistServiceListFragment extends Fragment {

    @BindView(R.id.rv_services)
    RecyclerView rvServices;
    private List<CategoriesItem> categoriesItemList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_artist_service_list, container, false);
        ButterKnife.bind(this, root);
        getServices();
        getCategoriesServices();
        return root;
    }

    private void getServices() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(getActivity());
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServices().
                enqueue(new Callback<ResponseBody>() {
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
                                    ArtistServicesModelResponse artistServicesModelResponse =
                                            gson.fromJson(jsonObject.toString(), ArtistServicesModelResponse.class);
                                    if (artistServicesModelResponse.isStatus()) {
                                        rvServices.setAdapter(new
                                                AdapterServiceHome(requireActivity(),
                                                artistServicesModelResponse.getArtistServiceData().getServices(), true));
                                        rvServices.setLayoutManager(new GridLayoutManager(getActivity(), 3, RecyclerView.VERTICAL, false));
                                    }
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                getActivity().finishAffinity();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(getActivity(), new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    private void getCategoriesServices() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(getActivity());
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getServiceWithCategory(LocalStorage.getString(requireActivity(), LocalStorage.X_USER_TOKEN, "")).enqueue(new Callback<ResponseBody>() {
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
                            CategoriesResponse categoriesResponse = gson.fromJson(jsonObject.toString(), CategoriesResponse.class);
                            categoriesItemList = categoriesResponse.getCategoriesData().getCategories();
                            Log.e("Size", "SIze" + categoriesItemList.size());


                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case Constants.ERROR_CODE_INVALID:
                        requireActivity().finishAffinity();
                        startActivity(new Intent(requireActivity(), LoginActivity.class));
                        break;
                    default:
                        try {
                            Toast.makeText(requireActivity(), new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.tv_add_service)
    void onClickAddService() {
        ItemListDialogFragment itemListDialogFragment =
                ItemListDialogFragment.newInstance((ArrayList<CategoriesItem>) categoriesItemList);
        itemListDialogFragment.show(requireFragmentManager(),
                ItemListDialogFragment.class.getName());
        // startActivityForResult(new Intent(getActivity(), ActivityAddService.class), Constants.REQUEST_CODE_ADD_SERVICE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CODE_ACCEPT_SERVICE && resultCode == RESULT_OK) {
            getServices();
        }
    }


}