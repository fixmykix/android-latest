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
import com.app.fixmykix.activities.ActivityRequestOrder;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.AddressDataItem;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;

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
    private List<AddressDataItem> addressDataItemList;


    public AddressRecyclerAdapter(Context mContext, List<AddressDataItem> data) {
        this.context = mContext;
        this.addressDataItemList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_address_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressDataItem addressDataItem = addressDataItemList.get(position);
        holder.txtStreet.setText(addressDataItem.getStreet());
        holder.txtCity.setText(addressDataItem.getCity());
        holder.txtState.setText(addressDataItem.getState());
        holder.txtPostalCode.setText(addressDataItem.getZip());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onValidateAddress(addressDataItem.getCity(), addressDataItem.getZip(), "", addressDataItem.getId());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDeleteAddress(addressDataItem.getId(), position);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityAddAddress) context).showDialogAddress(addressDataItem);
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
        addressDataItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addressDataItemList.size());
    }

    void onValidateAddress(String city, String postalcode, String stateprovincecode, int id) {

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
                switch (response.code()) {
                    case Constants.SUCCESS_CODE:
                    case Constants.SUCCESS_CODE_SECOND:
                        try {
                            // user detail got
                            if (response != null && response.body() != null) {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                Log.d("JsonObject Response", jsonObject.toString());
                                addAdressToOrder(id);
                                // context.startActivity(new Intent(context, ChatActivity.class));
                                //((ActivityAddAddress) context).finish();
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        try {
                            Toast.makeText(context, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    void addAdressToOrder(int id) {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(context);
        progressDialog.show();
        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                addAddressInorder(LocalStorage.getString(context, LocalStorage.X_USER_TOKEN, ""),
                        "v1/orders/" +
                                LocalStorage.getString(context, LocalStorage.ORDER_ID, "") + "/address", String.valueOf(id));
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
                                context.startActivity(new Intent(context, ActivityRequestOrder.class));
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        try {
                            Toast.makeText(context, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
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
        return addressDataItemList == null ? 0 : addressDataItemList.size();
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


