package com.app.fixmykix.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityServiceRequestForm;
import com.app.fixmykix.model.OrderDetailsItem;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    Context context;
    private List<OrderDetailsItem> dataItemList;


    public CartItemsAdapter(Context mContext, List<OrderDetailsItem> data) {
        this.context = mContext;
        this.dataItemList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_cart_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailsItem orderDetailsItem = dataItemList.get(position);
        holder.txtArtistDesc.setText(orderDetailsItem.getArtistService().getDescription());
        holder.txtCost.setText("Price : " + orderDetailsItem.getArtistService().getPrice());
        holder.txtOrderID.setText("Order Id : " + orderDetailsItem.getOrderId());
        holder.txtOrderQuantity.setText("Quantity : " + orderDetailsItem.getQuantity());
        holder.imgEditService.setOnClickListener(view -> {
            ArrayList<String> selectedServices = new ArrayList<>();
            selectedServices.add(String.valueOf(orderDetailsItem.getArtistServiceId()));
            Intent intent = new Intent(context, ActivityServiceRequestForm.class);
            intent.putExtra(Constants.KEY_SERVICE_IDS, selectedServices);
            intent.putExtra("EditOrder", "EditOrder");
            intent.putExtra("orderId", String.valueOf(orderDetailsItem.getArtistService().getServiceId()));
            intent.putExtra(Constants.KEY_ARTIST_ID, String.valueOf(orderDetailsItem.getArtistService().getArtistId()));
            intent.putExtra("Total", orderDetailsItem.getArtistService().getPrice());
            Log.e("Total", "Total" + orderDetailsItem.getArtistService().getPrice());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_artist_artist_desc)
        TextView txtArtistDesc;
        @BindView(R.id.tv_item_order_id)
        TextView txtOrderID;
        @BindView(R.id.tv_item_order_quantity)
        TextView txtOrderQuantity;
        @BindView(R.id.tv_item_artist_cost)
        TextView txtCost;
        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.imgeditbookedservice)
        ImageView imgEditService;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}