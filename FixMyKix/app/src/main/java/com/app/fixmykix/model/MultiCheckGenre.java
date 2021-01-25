package com.app.fixmykix.model;

import android.os.Parcelable;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

public class MultiCheckGenre extends MultiCheckExpandableGroup {

    public MultiCheckGenre(String title, List<ServiceResponse> items) {
        super(title, items);
    }
}
