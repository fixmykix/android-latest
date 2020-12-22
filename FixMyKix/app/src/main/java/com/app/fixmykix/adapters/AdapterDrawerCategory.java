package com.app.fixmykix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.app.fixmykix.MainActivityUser;
import com.app.fixmykix.R;
import com.app.fixmykix.model.Service;
import com.app.fixmykix.model.ShopingCategory;

import java.util.ArrayList;

public class AdapterDrawerCategory extends BaseExpandableListAdapter {

    private final Context context;
    private final ArrayList<ShopingCategory> listDataHeader;

    public AdapterDrawerCategory(Context context, ArrayList<ShopingCategory> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader == null ? 0 : listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listDataHeader.get(groupPosition).getServices() == null)
            return 0;
        else {
            return listDataHeader.get(groupPosition).getServices().size();
        }
    }

    @Override
    public ShopingCategory getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Service getChild(int groupPosition, int childPosition) {
        assert listDataHeader.get(groupPosition).getServices() != null;
        return listDataHeader.get(groupPosition).getServices().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_main_category_drawer, null);
        }

        TextView tvMainCategory = convertView.findViewById(R.id.tv_item_main_category);
        tvMainCategory.setText(getGroup(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_sub_category_drawer, null);
        }

        TextView txtListChild = convertView
                .findViewById(R.id.tv_item_sub_category);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox_service_of_drawer);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    ((MainActivityUser) context).selectService(String.valueOf(getChild(groupPosition, childPosition).getId()));
                else
                    ((MainActivityUser) context).removeServiceFromList(String.valueOf(getChild(groupPosition, childPosition).getId()));
            }
        });
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
