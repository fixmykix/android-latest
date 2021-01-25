package com.app.fixmykix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.model.ChatDataItem;
import com.app.fixmykix.utils.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {

    Context context;
    private List<ChatDataItem> dataItemList;
    private String userId;


    public ChatRecyclerAdapter(Context mContext, List<ChatDataItem> data) {
        this.context = mContext;
        this.dataItemList = data;
        userId = String.valueOf(CommonUtils.getUserData(context).getId());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.chat_adapter_item, parent, false);
        return new ViewHolder(rootView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (String.valueOf(dataItemList.get(position).getSenderId()).equalsIgnoreCase(userId)) {
            holder.chatTxtRight.setVisibility(View.GONE);
            holder.chatTxtLeft.setVisibility(View.VISIBLE);
            holder.chatTxtLeft.setText(dataItemList.get(position).getText());
        } else {
            holder.chatTxtLeft.setVisibility(View.GONE);
            holder.chatTxtRight.setVisibility(View.VISIBLE);
            holder.chatTxtRight.setText(dataItemList.get(position).getText());
        }
    }


    @Override
    public int getItemCount() {
        return dataItemList == null ? 0 : dataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chattxtleft)
        TextView chatTxtLeft;

        @BindView(R.id.chattxtright)
        TextView chatTxtRight;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}