package com.app.fixmykix.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterWhatIsService extends RecyclerView.Adapter<AdapterWhatIsService.ViewHolder> {

    Context context;

    public AdapterWhatIsService(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterWhatIsService.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.item_what_is_service, parent, false);
        return new ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull AdapterWhatIsService.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        /*return list == null ? 0 : list.size();*/
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
