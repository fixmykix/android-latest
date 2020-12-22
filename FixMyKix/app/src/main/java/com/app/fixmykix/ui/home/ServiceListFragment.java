package com.app.fixmykix.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.LoginActivity;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.CategoriesItem;
import com.app.fixmykix.model.CategoriesResponse;
import com.app.fixmykix.model.ChildrenItem;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

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

public class ServiceListFragment extends Fragment {

    @BindView(R.id.tv_restoration)
    TextView txtRestoration;
    @BindView(R.id.tv_custom)
    TextView txtCustom;
    private List<CategoriesItem> categoriesItemList;
    private List<ChildrenItem> cateChildrenItemList;

    public static final String ORIENTATION = "orientation";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_service_categories, container, false);
        ButterKnife.bind(this, root);
        getServices();
        return root;
    }

    @OnClick(R.id.tv_restoration)
    void onCLickRestoration() {
        navigateToCategoryListActivity(categoriesItemList.get(0).getChildren());
    }

    @OnClick(R.id.tv_custom)
    void onCLickCustom() {
        navigateToCategoryListActivity(categoriesItemList.get(1).getChildren());
    }

    private void navigateToCategoryListActivity(List<ChildrenItem> childrenItemList) {
        Intent intent = new Intent(requireActivity(), CategoryListActivity.class);
        intent.putParcelableArrayListExtra("CategoryList", (ArrayList<? extends Parcelable>) childrenItemList);
        startActivity(intent);
    }

    private void getServices() {
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
                            if (!categoriesItemList.isEmpty()) {
                                txtRestoration.setText(categoriesItemList.get(0).getName());
                                txtRestoration.setId(categoriesItemList.get(0).getId());
                                txtCustom.setText(categoriesItemList.get(1).getName());
                                txtCustom.setId(categoriesItemList.get(1).getId());
                            }


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

    /*@OnClick(R.id.tv_logout)
    void onClickLogout() {
        LocalStorage.logout(getActivity());
        startActivity(new Intent(getActivity(), LoginActivity.class));
        Objects.requireNonNull(getActivity()).finishAffinity();
    }*/
}