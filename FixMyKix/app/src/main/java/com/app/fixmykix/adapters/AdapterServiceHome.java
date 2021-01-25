package com.app.fixmykix.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.app.fixmykix.activities.ActivityAddService;
import com.app.fixmykix.activities.ActivityArtistList;
import com.app.fixmykix.model.ServicesItem;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterServiceHome extends RecyclerView.Adapter<AdapterServiceHome.ViewHolder> {

    Context context;
    private List<ServicesItem> list;
    private boolean isArtist;

    public AdapterServiceHome(Context context, List<ServicesItem> list, boolean isArtist) {
        this.context = context;
        this.list = list;
        this.isArtist = isArtist;
    }

    @NonNull
    @Override
    public AdapterServiceHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_service, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterServiceHome.ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());

        holder.llItemService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isArtist) {
                    Intent intent = new Intent(context, ActivityAddService.class);
                    intent.putExtra(Constants.KEY_SERVICE, list.get(position));
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ActivityArtistList.class);
                    ArrayList<String> ids = new ArrayList<>();
                    ids.add(String.valueOf(list.get(position).getId()));
                    intent.putExtra(Constants.KEY_SERVICE_IDS, ids);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_item_service)
        ImageView tvHeading;

        @BindView(R.id.tv_item_service_name)
        TextView tvName;

        @BindView(R.id.ll_container_item_service)
        LinearLayout llItemService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void filterList(ArrayList<ServicesItem> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }
}
