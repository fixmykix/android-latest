package com.app.fixmykix.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.MainActivityArtist;
import com.app.fixmykix.R;
import com.app.fixmykix.adapters.AdapterImage;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.Image;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.app.fixmykix.utils.GlideUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityServiceRequestForm extends Activity {


    @BindView(R.id.et_service_request_desc)
    EditText etDesc;

    @BindView(R.id.txtServices)
    TextView txtSelectedServices;

    @BindView(R.id.txtprice)
    TextView txtPrice;

    @BindView(R.id.rv_service_request_images)
    RecyclerView rvImages;

    @BindView(R.id.iv_add_images_request_service)
    ImageView ivDocument;

    ArrayList<String> serviceIds;
    String artistId;
    ArrayList<String> getServiceNames = new ArrayList<>();
    String imageBase64;

    private double getPrice = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);
        ButterKnife.bind(this);
        setView();
    }

    private void setView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.containsKey(Constants.KEY_SERVICE_IDS)) {
                serviceIds = extras.getStringArrayList(Constants.KEY_SERVICE_IDS);
            }
            if (extras.containsKey(Constants.KEY_ARTIST_ID))
                artistId = extras.getString(Constants.KEY_ARTIST_ID);

            if (extras.containsKey("SelectedServices"))
                getServiceNames = extras.getStringArrayList("SelectedServices");

            if (extras.containsKey("Total"))
                getPrice = extras.getDouble("Total");
        }
        txtPrice.setText(" $" + getPrice);

        String servicesnames = android.text.TextUtils.join(",", getServiceNames);
        txtSelectedServices.setText(servicesnames);
        /*rvImages.setAdapter(new AdapterImage(ActivityServiceRequestForm.this));
        rvImages.setLayoutManager(new LinearLayoutManager(ActivityServiceRequestForm.this, RecyclerView.HORIZONTAL, false));*/
    }

    @OnClick(R.id.iv_back_service_request_form)
    void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.iv_add_images_request_service)
    void onClickAddImage() {
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {getResources().getString(R.string.take_photo),
                getResources().getString(R.string.choose_from_library)};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityServiceRequestForm.this);
        builder.setTitle(getResources().getString(R.string.add_image));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.take_photo))) {
                    if (ContextCompat.checkSelfPermission(ActivityServiceRequestForm.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                Constants.PERMISSION_CODE_ACCESS_CAMERA);
                    } else {
                        cameraIntent();
                    }
                } else if (items[item].equals(getResources().getString(R.string.choose_from_library))) {
                    if (ContextCompat.checkSelfPermission(ActivityServiceRequestForm.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        galleryIntent();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                Constants.PERMISSION_CODE_READ_EXTERNAL_STORAGE);
                    }
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CODE_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), Constants.REQUEST_CODE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == Constants.REQUEST_CODE_CAMERA || requestCode == Constants.REQUEST_CODE_FILE) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = null;
        if (data.getData() == null) {
            bitmap = (Bitmap) data.getExtras().get("data");
        } else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(ActivityServiceRequestForm.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bitmap != null) {
            GlideUtils.setImage(ActivityServiceRequestForm.this, ivDocument, bitmap);
            ivDocument.setVisibility(View.VISIBLE);
            imageBase64 = CommonUtils.convertBitmapToBase64(bitmap);
        }
    }


    private void convertBitmapToFile(Bitmap bitmap) {
        File documentImageFile = new File(getCacheDir(), "temp".concat(String.valueOf(System.currentTimeMillis())));
        try {
            documentImageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapData = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(documentImageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        fileProfile = documentImageFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_CAMERA) {
            if (ContextCompat.checkSelfPermission(ActivityServiceRequestForm.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        Constants.PERMISSION_CODE_ACCESS_CAMERA);
            } else {
                cameraIntent();
            }
        } else if (requestCode == Constants.REQUEST_CODE_FILE) {
            if (ContextCompat.checkSelfPermission(ActivityServiceRequestForm.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                galleryIntent();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.PERMISSION_CODE_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @OnClick(R.id.tv_service_request_done)
    void onClickSendRequst() {
      //  navigatetoAddressActivity();
        if (TextUtils.isEmpty(etDesc.getText().toString())) {
            etDesc.setError("Enter Description");
            return;
        }
        if (TextUtils.isEmpty(imageBase64)) {
            Toast.makeText(this, "Select image", Toast.LENGTH_SHORT).show();
            return;
        }

        KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(ActivityServiceRequestForm.this);
        kProgressHUD.show();

        Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                bookService(LocalStorage.getString(ActivityServiceRequestForm.this, LocalStorage.X_USER_TOKEN, ""),
                        etDesc.getText().toString(), "", artistId, serviceIds,
                        imageBase64);
        signup.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                switch (response.code()) {
                    case Constants.SUCCESS_CODE:
                    case Constants.SUCCESS_CODE_SECOND:
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            if (jsonObject.getBoolean("status")) {
                                Toast.makeText(ActivityServiceRequestForm.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                                startActivity(new Intent(ActivityServiceRequestForm.this, ActivityConfirmOrder.class));
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case Constants.ERROR_CODE_INVALID:
                        finishAffinity();
                        startActivity(new Intent(ActivityServiceRequestForm.this, LoginActivity.class));
                        break;
                    default:
                        try {
                            Toast.makeText(ActivityServiceRequestForm.this, new JSONObject(response.errorBody().string()).getString("error"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(ActivityServiceRequestForm.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigatetoAddressActivity() {
        startActivity(new Intent(this, ActivityAddAddress.class));
    }
}
