package com.app.fixmykix.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityAddAddress;
import com.app.fixmykix.activities.ActivityDeliveryOptions;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.DataItem;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {

    Context context;
    private List<DataItem> dataItemList;


    public AddressRecyclerAdapter(Context mContext, List<DataItem> data) {
        this.context = mContext;
        this.dataItemList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_address_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem dataItem = dataItemList.get(position);
        holder.txtStreet.setText(dataItem.getStreet());
        holder.txtCity.setText(dataItem.getCity());
        holder.txtState.setText(dataItem.getState());
        holder.txtPostalCode.setText(dataItem.getZip());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onValidateAddress(dataItem.getCity(), dataItem.getZip(), "");
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteAddress(dataItem.getId(), position);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityAddAddress) context).showDialogAddress(dataItem);
            }
        });
    }

    void onDeleteAddress(int id, int position) {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(context);
        progressDialog.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                deleteAddresses(id, LocalStorage.getString(context, LocalStorage.X_USER_TOKEN, ""));
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
                        removeAt(position);
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeAt(int position) {
        dataItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataItemList.size());
    }

    void onValidateAddress(String city, String postalcode, String stateprovincecode) {

        ProgressDialog progressDialog = CommonUtils.getProgressBar(context);
        progressDialog.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                validateAddress(LocalStorage.getString(context, LocalStorage.X_USER_TOKEN, ""),
                        city, postalcode, stateprovincecode);
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
                        ((ActivityAddAddress) context).finish();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvstreet)
        TextView txtStreet;
        @BindView(R.id.tvpostalcode)
        TextView txtPostalCode;
        @BindView(R.id.tvstate)
        TextView txtState;
        @BindView(R.id.tvcity)
        TextView txtCity;
        @BindView(R.id.imgedit)
        ImageView imgEdit;
        @BindView(R.id.imgdelete)
        ImageView imgDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


