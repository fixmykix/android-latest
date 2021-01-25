package com.app.fixmykix.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityPayment;
import com.app.fixmykix.activities.ActivityUserOrders;
import com.app.fixmykix.activities.ChatActivity;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.model.ServiceRequest;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterUserOrder extends RecyclerView.Adapter<AdapterUserOrder.ViewHolder> {

    Context context;
    private ArrayList<ServiceRequest> list;

    public AdapterUserOrder(Context context, ArrayList<ServiceRequest> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterUserOrder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_user_order, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterUserOrder.ViewHolder holder, int position) {
        ServiceRequest serviceRequest = list.get(position);
        String services = "";
        for (Service service : serviceRequest.getService()) {
            services = service.getName().concat(", ");
        }
        if (services.endsWith(", "))
            services = services.substring(0, services.lastIndexOf(","));
        holder.tvHeading.setText(services);
        if (serviceRequest.getPrice() != null)
            holder.tvAmount.setText(String.format("Amount : %d$", serviceRequest.getPrice()));
        holder.tvDate.setText(String.format("Ordered at : %s", CommonUtils.convertUtcToDate(serviceRequest.getCreatedAt())));
        holder.tvStatus.setText(serviceRequest.getStatus());
        if (TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_APPROVED) ||
                TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_ACCEPTED)) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.imgChat.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_REJECTED) ||
                TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_CANCELLED)) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.ivDelete.setVisibility(View.GONE);
            holder.imgChat.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.dark_grey));
            holder.imgChat.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_APPROVED) ||
                        TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_ACCEPTED)) {
                    Intent intent = new Intent(context, ActivityPayment.class);
                    intent.putExtra("serviceID", String.valueOf(serviceRequest.getId()));
                    context.startActivity(intent);
                }
            }
        });


        holder.imgChat.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("senderId", String.valueOf(serviceRequest.getArtistId()));
            context.startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityUserOrders) context).deleteRequest(String.valueOf(serviceRequest.getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_order_date)
        ImageView ivDelete;

        @BindView(R.id.tv_user_order_status)
        TextView tvStatus;

        @BindView(R.id.tv_user_order_amount)
        TextView tvAmount;

        @BindView(R.id.tv_user_order_date)
        TextView tvDate;

        @BindView(R.id.tv_user_order_heading)
        TextView tvHeading;

        @BindView(R.id.chat)
        ImageView imgChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
