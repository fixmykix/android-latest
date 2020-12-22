package com.app.fixmykix.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.app.fixmykix.MainActivityArtist;
import com.app.fixmykix.MainActivityUser;
import com.app.fixmykix.R;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;

public class SplashActivity extends Activity {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        requestForPermissions();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestForPermissions() {
        if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE},
                    Constants.PERMISSION_REQUEST_PHONE_STATE);
        } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.PERMISSION_REQUEST_LOCATION);
        } else proceed();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_REQUEST_PHONE_STATE) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_PHONE_NUMBERS, Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE},
                        Constants.PERMISSION_REQUEST_PHONE_STATE);
            } else if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        Constants.PERMISSION_REQUEST_LOCATION);
            }
        } else if (requestCode == Constants.PERMISSION_REQUEST_LOCATION) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                proceed();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void proceed() {
        int SPLASH_TIME_OUT = 800;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LocalStorage.getBoolean(SplashActivity.this, LocalStorage.APP_VISITED_ONCE, false)) {
                    if (LocalStorage.getBoolean(SplashActivity.this, LocalStorage.IS_USER_LOGGED_IN, false)) {
                        if (CommonUtils.getUserData(SplashActivity.this).getRole() == Constants.ROLE_ARTIST)
                            startActivity(new Intent(SplashActivity.this, MainActivityArtist.class));
                        else
                            startActivity(new Intent(SplashActivity.this, MainActivityUser.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, SignUpActivity.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
