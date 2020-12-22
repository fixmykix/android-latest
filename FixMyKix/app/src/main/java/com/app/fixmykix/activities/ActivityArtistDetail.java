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

import com.app.fixmykix.MainActivityArtist;
import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityArtistDetail extends Activity {
    @BindView(R.id.artist_business_name)
    EditText etBusinessNam;

    @BindView(R.id.input_text_artist_service_description)
    EditText desc;

    @BindView(R.id.input_text_artist_facebook_link)
    EditText fbLink;

    @BindView(R.id.input_text_artist_instagram_link)
    EditText instaLink;

    @BindView(R.id.input_text_artist_snapchat_link)
    EditText snapchatLink;

    @BindView(R.id.input_text_artist_twitter_link)
    EditText twitterLink;

    @BindView(R.id.input_text_artist_youtube_link)
    EditText youtubeLink;

    @BindView(R.id.iv_artist_detail)
    ImageView ivDocument;

    @BindView(R.id.tv_add_iamge_artist_detail)
    TextView tvAddImage;

    String imageBase64;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_artist_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_submit_artist_detail)
    void onClickArtistDone() {

        if (TextUtils.isEmpty(etBusinessNam.getText().toString())) {
            etBusinessNam.setError("Enter Business Name");
            return;
        }
        if (TextUtils.isEmpty(desc.getText().toString())) {
            desc.setError("Enter Description");
            return;
        }
        etBusinessNam.setError(null);
        desc.setError(null);

        if (TextUtils.isEmpty(imageBase64)) {
            Toast.makeText(this, "Select image!", Toast.LENGTH_SHORT).show();
            return;
        }

        addDetails();
    }

    @OnClick(R.id.iv_back_artist_detail)
    void onClickBack() {
        onBackPressed();
    }

    private void addDetails() {
        KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(ActivityArtistDetail.this);
        kProgressHUD.show();

        Call<ResponseBody> signup = ApiClient.getClient().create(ApiInterface.class).
                addArtistDetail(LocalStorage.getString(ActivityArtistDetail.this, LocalStorage.X_USER_TOKEN, ""),
                        etBusinessNam.getText().toString(),
                        desc.getText().toString(),
                        twitterLink.getText().toString(),
                        youtubeLink.getText().toString(),
                        fbLink.getText().toString(),
                        snapchatLink.getText().toString(),
                        instaLink.getText().toString(),
                        imageBase64);
        signup.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                kProgressHUD.dismiss();
                if (response.code() != 200 && response.code() != 201) {
                    try {
                        Toast.makeText(ActivityArtistDetail.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.getBoolean("status")) {
                            LocalStorage.setBoolean(ActivityArtistDetail.this, LocalStorage.IS_USER_LOGGED_IN, true);
                            startActivity(new Intent(ActivityArtistDetail.this, MainActivityArtist.class));
                            Toast.makeText(ActivityArtistDetail.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            finishAffinity();
                        } else
                            Toast.makeText(ActivityArtistDetail.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(ActivityArtistDetail.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.tv_add_iamge_artist_detail)
    void addImage() {
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {getResources().getString(R.string.take_photo),
                getResources().getString(R.string.choose_from_library)};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityArtistDetail.this);
        builder.setTitle(getResources().getString(R.string.add_image));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.take_photo))) {
                    if (ContextCompat.checkSelfPermission(ActivityArtistDetail.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                Constants.PERMISSION_CODE_ACCESS_CAMERA);
                    } else {
                        cameraIntent();
                    }
                } else if (items[item].equals(getResources().getString(R.string.choose_from_library))) {
                    if (ContextCompat.checkSelfPermission(ActivityArtistDetail.this,
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
                bitmap = MediaStore.Images.Media.getBitmap(ActivityArtistDetail.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bitmap != null) {
            GlideUtils.setImage(ActivityArtistDetail.this, ivDocument, bitmap);
            tvAddImage.setText("Change Image");
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
            if (ContextCompat.checkSelfPermission(ActivityArtistDetail.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        Constants.PERMISSION_CODE_ACCESS_CAMERA);
            } else {
                cameraIntent();
            }
        } else if (requestCode == Constants.REQUEST_CODE_FILE) {
            if (ContextCompat.checkSelfPermission(ActivityArtistDetail.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                galleryIntent();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.PERMISSION_CODE_READ_EXTERNAL_STORAGE);
            }
        }
    }
}
