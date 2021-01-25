package com.app.fixmykix.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.model.ChildrenItem;
import com.app.fixmykix.ui.home.CategoryListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    private List<ChildrenItem> list;


    public CategoryAdapter(Context context, List<ChildrenItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(rootView);
    }

    private int selectedPosition = -1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategoryname.setText(list.get(position).getName());
        holder.aSwitchCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                selectedPosition = holder.getAdapterPosition();
                if (isChecked) {
                    ((CategoryListActivity) context).onClickCalled(list.get(selectedPosition).getChildren());
                } else {
                    ((CategoryListActivity) context).onClickCalled(new ArrayList<>());
                }
                ((CategoryListActivity) context).notifyAdapter();

            }
        });
        if (selectedPosition == position) {
            holder.aSwitchCategory.setChecked(true);
        } else {
            holder.aSwitchCategory.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_name)
        TextView txtCategoryname;
        @BindView(R.id.switchcategory)
        Switch aSwitchCategory;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

