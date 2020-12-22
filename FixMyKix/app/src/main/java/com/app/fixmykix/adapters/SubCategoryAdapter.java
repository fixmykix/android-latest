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
import com.app.fixmykix.model.MultiCheckGenre;
import com.app.fixmykix.ui.home.CategoryListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    Context context;
    private List<ChildrenItem> list;
    private List<ChildrenItem> subCategoryList = new ArrayList<>();

    public SubCategoryAdapter(Context context, List<ChildrenItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new SubCategoryAdapter.ViewHolder(rootView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder holder, int position) {
        holder.txtCategoryname.setText(list.get(position).getName());
        holder.aSwitchCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    if (list != null && list.get(position).getChildren() != null) {
                        subCategoryList.addAll(list.get(position).getChildren());
                        //  MultiCheckGenre multiCheckGenre = new MultiCheckGenre(list.get(position).getName(), subCategoryList);
                        ((CategoryListActivity) context).onSubCategoryClicked(subCategoryList, 1);
                    }
                } else {
                    if (list != null && list.get(position).getChildren() != null) {
                        // MultiCheckGenre multiCheckGenre = new MultiCheckGenre(list.get(position).getName(), list.get(position).getChildren());
                        ((CategoryListActivity) context).onSubCategoryClicked(list.get(position).getChildren(), 2);
                        subCategoryList.removeAll(list.get(position).getChildren());
                    }
                }
            }
        });
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


