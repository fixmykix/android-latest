package com.app.fixmykix.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityAddDetailToAcceptRequest;
import com.app.fixmykix.activities.ChatActivity;
import com.app.fixmykix.model.ServiceInfoItem;
import com.app.fixmykix.model.ServiceRequestsItem;
import com.app.fixmykix.ui.home.ActivityArtistRequestList;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterArtistServiceRequest extends RecyclerView.Adapter<AdapterArtistServiceRequest.ViewHolder> {

    Context context;
    private List<ServiceRequestsItem> list;

    public AdapterArtistServiceRequest(Context context, List<ServiceRequestsItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterArtistServiceRequest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_artist_service_request, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterArtistServiceRequest.ViewHolder holder, int position) {
        ServiceRequestsItem serviceRequest = list.get(position);

        holder.tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityAddDetailToAcceptRequest.class);
                intent.putExtra(Constants.KET_SERVICE_REQUEST, serviceRequest);
                ((Activity) context).startActivityForResult(intent, Constants.REQUEST_CODE_ACCEPT_SERVICE);
            }
        });

        holder.tvDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(String.valueOf(serviceRequest.getOrderId())))
                    ((ActivityArtistRequestList) context).declineRequest(serviceRequest.getId());
            }
        });
        Log.e("CheckServiceNameSIZE", "" + serviceRequest.getServiceInfo().size());
        String services = "";
        for (ServiceInfoItem serviceRequestsItem : serviceRequest.getServiceInfo()) {
            Log.e("CheckServiceName", serviceRequestsItem.getName());
            services = serviceRequestsItem.getName().concat(", ");
        }
//        if (services.endsWith(", "))
//            services = services.substring(0, services.lastIndexOf(","));
        holder.services.setText(services);
        holder.tvAmount.setText(String.format("Amount : %d$", serviceRequest.getPrice()));
        holder.tvDate.setText(String.format("Ordered at : %s", CommonUtils.convertUtcToDate(serviceRequest.getCreatedAt())));
        holder.tvStatus.setText(serviceRequest.getStatus());
        if (TextUtils.isEmpty(serviceRequest.getUserInfo().getFirstName())) {
            holder.tvName.setText(serviceRequest.getUserInfo().getEmail());
        } else
            holder.tvName.setText(serviceRequest.getUserInfo().getFirstName()/*.concat(serviceRequest.getUserData().getLastName())*/);
        if (TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_PENDING)) {
            holder.llContainerStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.llContainerStatus.setVisibility(View.GONE);
        }

        if (holder.tvStatus.getText().toString().equalsIgnoreCase(Constants.SERVICE_APPROVED)) {
            holder.ivChat.setVisibility(View.VISIBLE);
        } else {
            holder.ivChat.setVisibility(View.GONE);
        }

        holder.ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("senderId", String.valueOf(serviceRequest.getUserId()));
                Log.e("ArtistID", String.valueOf(serviceRequest.getArtistId()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_artist_service_request_click)
        ImageView ivClick;

        @BindView(R.id.tv_item_artist_request_services)
        TextView services;

        @BindView(R.id.tv_item_artist_request_accept)
        TextView tvAccept;

        @BindView(R.id.tv_item_artist_request_decline)
        TextView tvDecline;

        @BindView(R.id.tv_item_artist_request_name)
        TextView tvName;

        @BindView(R.id.tv_item_artist_request_date)
        TextView tvDate;

        @BindView(R.id.tv_item_artist_request_amount)
        TextView tvAmount;

        @BindView(R.id.tv_item_artist_request_status)
        TextView tvStatus;

        @BindView(R.id.ll_container_request_status)
        LinearLayout llContainerStatus;

        @BindView(R.id.iv_item_chat)
        ImageView ivChat;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
