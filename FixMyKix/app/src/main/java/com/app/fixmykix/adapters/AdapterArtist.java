package com.app.fixmykix.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityArtistServiceDetail;
import com.app.fixmykix.model.Artist;
import com.app.fixmykix.model.ArtistService;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterArtist extends RecyclerView.Adapter<AdapterArtist.ViewHolder> {

    Context context;
    private ArrayList<Artist> list;
    private List<ArtistService> artistServiceList;
    private Double aDouble = 0.0;
    private Double total = 0.0;

    public AdapterArtist(Context context, ArrayList<Artist> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterArtist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_artist, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterArtist.ViewHolder holder, int position) {
        if (!TextUtils.isEmpty(list.get(position).getFirstName()))
            holder.tvName.setText(list.get(position).getFirstName());
        else if (!TextUtils.isEmpty(list.get(position).getEmail())) {
            holder.tvName.setText(list.get(position).getEmail());
        }
        holder.tvAdd.setText(list.get(position).getAddress());
        artistServiceList = list.get(position).getArtistServices();
        Double total = 0.0;
        for (int i = 0; i < artistServiceList.size(); i++) {
            Double aDouble = artistServiceList.get(i).getPrice();
            Log.e("Price", "" + aDouble);
            total = aDouble + total;
            Log.e("CompletePrice", "" + total);
        }
        holder.tvCost.setText("" + total);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(((Activity) context), ActivityArtistServiceDetail.class);
                intent.putExtra(Constants.KEY_ARTIST, list.get(position));
                ((Activity) context).startActivityForResult(intent, Constants.REQUEST_CODE_BOOK_SERVICE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cv_container_artist)
        CardView cv;

        @BindView(R.id.tv_item_artist_artist_name)
        TextView tvName;

        @BindView(R.id.tv_item_artist_add)
        TextView tvAdd;

        @BindView(R.id.tv_item_artist_cost)
        TextView tvCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void filterList(ArrayList<Artist> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }
}
