package com.app.fixmykix;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.app.fixmykix.activities.ActivityAboutUs;
import com.app.fixmykix.activities.ActivityArtistDetail;
import com.app.fixmykix.activities.ActivityContactUs;
import com.app.fixmykix.activities.LoginActivity;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.ui.home.ActivityArtistRequestList;
import com.app.fixmykix.ui.home.ArtistServiceListFragment;

import android.os.Handler;
import android.view.Gravity;

import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.ligl.android.widget.iosdialog.IOSDialog;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityArtist extends AppCompatActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.frame_layout_artist)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_artist);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.frame_layout_artist, new ArtistServiceListFragment()).commit();
    }

    @OnClick(R.id.iv_hamburger)
    void onClickHamburger() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }
    }

    @OnClick(R.id.artist_drawer_my_request)
    void onClickArtistRequest() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityArtistRequestList.class));
    }

    @OnClick(R.id.artist_drawer_artist_profile)
    void onClickArtistProfile() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityArtistDetail.class));
    }

    @OnClick(R.id.artist_logout)
    void onClickArtistLogout() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        logouitPos();
    }

    @OnClick(R.id.artist_drawer_about_us)
    void onClickAboutUs() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityAboutUs.class));
    }


    @OnClick(R.id.artist_drawer_contact_us)
    void onClickContactUs() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        }
        startActivity(new Intent(this, ActivityContactUs.class));
    }

    private void logouitPos() {
        new IOSDialog.Builder(this).setTitle("FixMyKix")
                .setMessage("Do you want to logout ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();

    }

    private void logout() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait......!");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                LocalStorage.clearPreference(MainActivityArtist.this);
                progressDialog.dismiss();
                startActivity(new Intent(MainActivityArtist.this, LoginActivity.class));
                finishAffinity();
            }
        }, 1000);
    }
}
