package com.app.fixmykix.adapters;

import android.app.Activity;
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
import com.app.fixmykix.activities.ActivityArtistList;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSearchService extends RecyclerView.Adapter<AdapterSearchService.ViewHolder> {

    Context context;
    private ArrayList<Service> list;

    public AdapterSearchService(Context context, ArrayList<Service> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterSearchService.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_search_service, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterSearchService.ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).getName());

        holder.llItemService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                intent.putExtra(Constants.SERVICE_DATA, list.get(position));
                ((Activity) context).setResult(Constants.REQUEST_CODE_SELECT_SERVICE, intent);
                ((Activity) context).finish();*/
                Intent intent = new Intent(context, ActivityArtistList.class);
                ArrayList<String> ids = new ArrayList<>();
                ids.add(String.valueOf(list.get(position).getId()));
                intent.putExtra(Constants.KEY_SERVICE_IDS, ids);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_service_name)
        TextView tvName;

        @BindView(R.id.ll_container_item_service)
        LinearLayout llItemService;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void filterList(ArrayList<Service> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }
}
