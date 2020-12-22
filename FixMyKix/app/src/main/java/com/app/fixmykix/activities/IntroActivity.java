package com.app.fixmykix.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.app.fixmykix.MainActivityArtist;
import com.app.fixmykix.MainActivityUser;
import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterIntroSplash;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends Activity {
    @BindView(R.id.tab_layout_dots)
    TabLayout tabLayoutDots;

    @BindView(R.id.view_pager_splash)
    ViewPager viewPager;

    @BindView(R.id.tv_splash_next)
    TextView tvSplashNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        if (LocalStorage.getBoolean(this, LocalStorage.APP_VISITED_ONCE, false)) {
            if (LocalStorage.getBoolean(this, LocalStorage.IS_USER_LOGGED_IN, false)) {
                if (CommonUtils.getUserData(IntroActivity.this).getRole() == Constants.ROLE_ARTIST)
                    startActivity(new Intent(this, MainActivityArtist.class));
                else
                    startActivity(new Intent(this, MainActivityUser.class));
                finish();
            } else {
                startActivity(new Intent(this, SignUpActivity.class));
                finish();
            }
        } else {
            LocalStorage.setBoolean(this, LocalStorage.APP_VISITED_ONCE, true);
            setPager();
        }
    }

    private void setPager() {
        viewPager.setAdapter(new AdapterIntroSplash(this));
        tabLayoutDots.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tvSplashNext.setText("Done");
                } else {
                    tvSplashNext.setText("Next");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.tv_splash_skip)
    void onClickSkip() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    @OnClick(R.id.tv_splash_next)
    void onClickNext() {
        if (viewPager.getCurrentItem() < 2) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        } else {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        }
    }
}
