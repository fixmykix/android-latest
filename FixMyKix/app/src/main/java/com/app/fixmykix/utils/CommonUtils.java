package com.app.fixmykix.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresApi;

import com.app.fixmykix.model.UserData;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CommonUtils {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getPhoneNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (context.checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED &&
                context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }
        assert tMgr != null;
        return (tMgr.getLine1Number());
    }

    public static KProgressHUD showProgressWithText(Context context) {
        KProgressHUD progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setBackgroundColor(Color.parseColor("#FCB300"))
                .setCancellable(true)
                .setAnimationSpeed(2);
        return progressHUD;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) ((Activity) context).getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((Activity) context).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getAddressByLatLong(Context context, String latitude, String longitude) {

        if (latitude == null && longitude == null)
            return "Vijay Nagar, Indore";
        else {
            Geocoder geocoder;
            List<Address> addressesList = null;
            String address = null;
            geocoder = new Geocoder(context, Locale.getDefault());

            try {
                addressesList = geocoder.getFromLocation(Double.valueOf(latitude), Double.valueOf(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressesList != null) {
                String addressLine1 = addressesList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addressesList.get(0).getLocality();
                String state = addressesList.get(0).getAdminArea();
                String country = addressesList.get(0).getCountryName();
                String postalCode = addressesList.get(0).getPostalCode();
                String knownName = addressesList.get(0).getFeatureName();
                address = addressLine1 + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName;
            }

            return address;
        }
    }

    public static String getColonyCityByLatLong(Context context, double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addressesList = null;
        String address = null;
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addressesList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addressesList != null) {
            String addressLine1 = addressesList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addressesList.get(0).getLocality();
            String state = addressesList.get(0).getAdminArea();
            String country = addressesList.get(0).getCountryName();
            String postalCode = addressesList.get(0).getPostalCode();
            String knownName = addressesList.get(0).getFeatureName();
            address = addressLine1 + " " + city;
        }

        return address;
    }

    public static UserData getUserData(Context context) {
        String stringUserDetail = LocalStorage.getString(context, LocalStorage.USER_DETAIL, "");
        Gson gson = new Gson();
        Type type = new TypeToken<UserData>() {
        }.getType();
        return gson.fromJson(stringUserDetail, type);
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static ProgressDialog getProgressBar(Context context) {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage("please wait..");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }

    public static String convertUtcToDate(String inputDate) {
//        SimpleDateFormat destinationDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat destinationDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        if (!TextUtils.isEmpty(inputDate)) {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dtf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            try {
                date = dtf.parse(inputDate);
//                String part1 =  destinationDateFormat.format(date);
                String part2 = destinationDateFormat1.format(date);
                return part2;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
