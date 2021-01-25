package com.app.fixmykix.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityArtistServiceDetail;
import com.app.fixmykix.model.ArtistService;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSelectServiceOfArtist extends RecyclerView.Adapter<AdapterSelectServiceOfArtist.ViewHolder> {

    Context context;
    private ArrayList<ArtistService> list;

    public AdapterSelectServiceOfArtist(Context context, ArrayList<ArtistService> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterSelectServiceOfArtist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_select_service_of_artist, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterSelectServiceOfArtist.ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getServiceName());
        holder.tvCost.setText("Service Cost: " + String.valueOf(list.get(position).getPrice()).concat("$"));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    ((ActivityArtistServiceDetail) context).selectService(list.get(position));
                } else {
                    ((ActivityArtistServiceDetail) context).removeServiceFromList(list.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_service_of_artist_name)
        TextView tvName;

        @BindView(R.id.tv_item_service_of_artist_cost)
        TextView tvCost;

        @BindView(R.id.checkbox_service_of_artist)
        CheckBox checkBox;

        @BindView(R.id.ll_container_item_service)
        LinearLayout llItemService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
