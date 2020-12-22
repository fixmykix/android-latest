package com.app.fixmykix.storage_manager;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    public static final String PREF_NAME = "local_storage_fixmykix";
    public static final String IS_USER_LOGGED_IN = "user_logged_in";
    public static final String LOGGED_IN_EMAIL = "logged_in_email";
    public static final String LOGGED_IN_MOBILE = "logged_in_mobile";
    public static final String LATITUDE = "user_latitude";
    public static final String LONGITUDE = "user_longitude";
    public static final String USER_DETAIL = "user_detail";
    public static final String APP_VISITED_ONCE = "app_visited_once";
    public static final String X_USER_TOKEN = "x_user_token";

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value) {
        getPrefs(context).edit().putString(key, value).commit();
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        return getPrefs(context).getString(key, defaultValue);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPrefs(context).getBoolean(key, defaultValue);
    }

    public static void clearPreference(Context context) {
        getPrefs(context).edit().clear().commit();
    }

    public static void logout(Context context) {
        clearPreference(context);
    }
}
