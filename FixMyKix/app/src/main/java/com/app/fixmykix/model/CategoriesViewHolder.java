package com.app.fixmykix.model;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.app.fixmykix.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class CategoriesViewHolder extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;

    public CategoriesViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView = (CheckedTextView) itemView.findViewById(R.id.list_item_multicheck_artist_name);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setArtistName(String artistName) {
        childCheckedTextView.setText(artistName);
    }
}