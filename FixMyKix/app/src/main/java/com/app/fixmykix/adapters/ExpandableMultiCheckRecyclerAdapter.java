package com.app.fixmykix.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.fixmykix.R;
import com.app.fixmykix.model.CategoriesViewHolder;
import com.app.fixmykix.model.ChildrenItem;
import com.app.fixmykix.model.HeadingViewHolder;
import com.app.fixmykix.model.MultiCheckGenre;
import com.app.fixmykix.model.ServiceResponse;
import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ExpandableMultiCheckRecyclerAdapter extends CheckableChildRecyclerViewAdapter<HeadingViewHolder, CategoriesViewHolder> {


    public ExpandableMultiCheckRecyclerAdapter(List<MultiCheckGenre> groups) {
        super(groups);
    }

    @Override
    public HeadingViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_heading, parent, false);
        return new HeadingViewHolder(view);
    }


    @Override
    public CategoriesViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindCheckChildViewHolder(CategoriesViewHolder holder, int flatPosition, CheckedExpandableGroup group, int childIndex) {
        final ServiceResponse artist = (ServiceResponse) group.getItems().get(childIndex);
        holder.setArtistName(artist.getName());
    }

    @Override
    public void onBindGroupViewHolder(HeadingViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGenreTitle(group);
    }
}
