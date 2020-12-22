package com.app.fixmykix.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class GlideUtils {

    public static void setImage(Context context, ImageView imageView, Object imagePath) {
        Glide.with(context).load(imagePath)
                .into(imageView);
    }

    public static void setImageOnCircularView(Context context, CircleImageView imageView, Object imagePath) {
        Glide.with(context).load(imagePath)
                .into(imageView);
    }
}
