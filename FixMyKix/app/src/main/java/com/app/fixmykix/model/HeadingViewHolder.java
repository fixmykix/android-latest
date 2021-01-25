package com.app.fixmykix.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.fixmykix.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class HeadingViewHolder extends GroupViewHolder {

    private TextView genreTitle;
    private ImageView imgArrow;

    public HeadingViewHolder(View itemView) {
        super(itemView);
        genreTitle = itemView.findViewById(R.id.list_item_genre_name);
        imgArrow = itemView.findViewById(R.id.imgarrow);
        imgArrow.setBackgroundResource(R.drawable.ic_arrow_down_24);
    }

    public void setGenreTitle(ExpandableGroup group) {
        genreTitle.setText(group.getTitle());
    }

    @Override
    public void expand() {
        imgArrow.setBackgroundResource(R.drawable.ic_arrow_up_24);


    }

    @Override
    public void collapse() {
        imgArrow.setBackgroundResource(R.drawable.ic_arrow_down_24);
    }
}
