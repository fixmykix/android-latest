package com.app.fixmykix.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.app.fixmykix.R;
import com.app.fixmykix.activities.ActivityArtistList;
import com.app.fixmykix.custom_view.CustomExpListView;
import com.app.fixmykix.model.DrawerItem;
import com.app.fixmykix.model.ShopingCategory;

import java.util.ArrayList;

public class AdapterDrawerItem extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<DrawerItem> drawerItemList;

    public AdapterDrawerItem(Context context, ArrayList<DrawerItem> drawerItemList) {
        this.context = context;
        this.drawerItemList = drawerItemList;
    }

    @Override
    public int getGroupCount() {
        return drawerItemList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public DrawerItem getGroup(int groupPosition) {
        return drawerItemList.get(groupPosition);
    }

    @Override
    public ArrayList<ShopingCategory> getChild(int groupPosition, int childPosition) {
        if (drawerItemList.get(groupPosition).getCategoryList() != null && drawerItemList.get(groupPosition).getCategoryList().size() > 0) {
            return drawerItemList.get(groupPosition).getCategoryList();
        }
        return null;
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
            convertView = infalInflater.inflate(R.layout.item_drawer_items, null);
        }

        TextView tvItemDrawer = convertView.findViewById(R.id.tv_item_main_category_with_image);
        tvItemDrawer.setText(getGroup(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ArrayList<ShopingCategory> list = getChild(groupPosition, childPosition);

        final CustomExpListView secondLevelExpListView = new CustomExpListView(context);
        secondLevelExpListView.setAdapter(new AdapterDrawerCategory(context, list));
        secondLevelExpListView.setGroupIndicator(null);
        secondLevelExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(context, ActivityArtistList.class);
                context.startActivity(intent);

                return false;
            }
        });
        secondLevelExpListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    secondLevelExpListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        return secondLevelExpListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
