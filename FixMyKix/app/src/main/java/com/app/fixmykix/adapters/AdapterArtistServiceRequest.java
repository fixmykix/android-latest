package com.app.fixmykix.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityAddDetailToAcceptRequest;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.model.ServiceRequest;
import com.app.fixmykix.ui.home.ActivityArtistRequestList;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterArtistServiceRequest extends RecyclerView.Adapter<AdapterArtistServiceRequest.ViewHolder> {

    Context context;
    private ArrayList<ServiceRequest> list;

    public AdapterArtistServiceRequest(Context context, ArrayList<ServiceRequest> list) {
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
        ServiceRequest serviceRequest = list.get(position);

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
                ((ActivityArtistRequestList) context).declineRequest(String.valueOf(serviceRequest.getId()));
            }
        });

        String services = "";
        for (Service service : serviceRequest.getService()) {
            services = service.getName().concat(", ");
        }
        if (services.endsWith(", "))
            services = services.substring(0, services.lastIndexOf(","));
        holder.services.setText(services);
        holder.tvAmount.setText(String.format("Amount : %d$", serviceRequest.getPrice()));
        holder.tvDate.setText(String.format("Ordered at : %s", CommonUtils.convertUtcToDate(serviceRequest.getCreatedAt())));
        holder.tvStatus.setText(serviceRequest.getStatus());
        holder.tvName.setText(serviceRequest.getUserData().getFirstName()/*.concat(serviceRequest.getUserData().getLastName())*/);
        if (TextUtils.equals(serviceRequest.getStatus(), Constants.SERVICE_PENDING)) {
            holder.llContainerStatus.setVisibility(View.VISIBLE);
            holder.tvStatus.setVisibility(View.GONE);
        } else {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.llContainerStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        /*return list == null ? 0 : list.size();*/
        return 4;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
