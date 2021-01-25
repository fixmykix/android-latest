package com.app.fixmykix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeLineStatusAdapter extends RecyclerView.Adapter<TimeLineStatusAdapter.ViewHolder> {

    Context context;
    private List<String> items;


    public TimeLineStatusAdapter(Context mContext, List<String> data) {
        this.context = mContext;
        this.items = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_horizontal, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ordrTxt.setText(items.get(position));
        if (position == items.size() - 1) {
            holder.verticalLine.setVisibility(View.GONE);
        } else {
            holder.verticalLine.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderstatus)
        TextView ordrTxt;
        @BindView(R.id.verticalLine)
        View verticalLine;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
