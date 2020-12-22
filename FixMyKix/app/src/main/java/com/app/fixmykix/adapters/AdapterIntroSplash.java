package com.app.fixmykix.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.app.fixmykix.R;
import com.app.fixmykix.utils.GlideUtils;

public class AdapterIntroSplash extends PagerAdapter {

    private Context context;

    public AdapterIntroSplash(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = LayoutInflater.from(context).inflate(R.layout.item_pager_splash, view, false);
        view.addView(myImageLayout, 0);

        TextView textViewHeading = myImageLayout.findViewById(R.id.item_pager_splash_heading);
        TextView tvDesc = myImageLayout.findViewById(R.id.tv_item_pager_splash_desc);
        ImageView ivIntro = myImageLayout.findViewById(R.id.iv_item_pager_splash);

        switch (position) {
            case 0:
                tvDesc.setText(context.getString(R.string.intro_text_1));
                GlideUtils.setImage(context, ivIntro, context.getDrawable(R.drawable.jordan_blue));
                break;
            case 1:
                tvDesc.setText(context.getString(R.string.intro_text_2));
                GlideUtils.setImage(context, ivIntro, context.getDrawable(R.drawable.img3));
                break;
            case 2:
                tvDesc.setText(context.getString(R.string.intro_text_3));
                GlideUtils.setImage(context, ivIntro, context.getDrawable(R.drawable.img7));
                break;
        }

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }
}