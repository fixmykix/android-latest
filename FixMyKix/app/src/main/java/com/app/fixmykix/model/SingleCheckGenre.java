package com.app.fixmykix.model;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;

import java.util.List;

public class SingleCheckGenre extends SingleCheckExpandableGroup {
    public SingleCheckGenre(String title, List<ServiceResponse> items) {
        super(title, items);
    }
}
