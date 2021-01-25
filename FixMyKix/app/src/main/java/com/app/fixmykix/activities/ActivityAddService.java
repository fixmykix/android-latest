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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.app.fixmykix.MainActivityArtist;
import com.app.fixmykix.R;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.ServiceResponse;
import com.app.fixmykix.model.ServicesItem;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddService extends Activity {
    @BindView(R.id.et_add_service_detail_desc)
    EditText etDesc;

    @BindView(R.id.et_add_service_price)
    EditText etPrice;

    @BindView(R.id.et_add_service_days)
    EditText etDays;

    @BindView(R.id.spinner_select_service)
    Spinner spinneService;

    @BindView(R.id.spinner_select_category)
    Spinner spinnerCategory;

    @BindView(R.id.iv_add_service_proof_of_work)
    ImageView ivDocument;

    String imageBase64;

    ServicesItem service = null;

    List<ServiceResponse> serviceResponseArrayList = new ArrayList<>();
    ArrayList<String> serviceSelection = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_detail);
        ButterKnife.bind(this);
        getData();
    }

    private void getData() {
        Bundle extras = getIntent().getExtras();
        if (serviceResponseArrayList != null && serviceResponseArrayList.size() > 0)
            serviceResponseArrayList.clear();
        if (serviceSelection != null && serviceSelection.size() > 0)
            serviceSelection.clear();
        if (extras != null && extras.containsKey(Constants.KEY_SERVICE)) {
            service = extras.getParcelable(Constants.KEY_SERVICE);
            serviceSelection.add(service.getName());
        }
        if (extras != null && extras.containsKey(Constants.KEY_SERVICE_IDS)) {
            serviceResponseArrayList = extras.getParcelableArrayList(Constants.KEY_SERVICE_IDS);
        }

        for (int i = 0; i < serviceResponseArrayList.size(); i++) {
            serviceSelection.add(serviceResponseArrayList.get(i).getName());
        }
        setServiceAdapter(serviceSelection);
        //getDrawerCategoryData();
    }

    @OnClick(R.id.iv_back_add_service_detail)
    void onClickBackAddService() {
        onBackPressed();
    }


    private void setServiceAdapter(ArrayList<String> services) {
        ArrayAdapter<String> adapterService = new ArrayAdapter<String>(ActivityAddService.this, R.layout.screen_width_spinner_selected_item, services) {
            @Override
            public boolean isEnabled(int position) {
                    /*if (position == 0) {
                        // Disable the first item from Spinner
                        // First item will be use for hint
                        return false;
                    } else {
                        return true;
                    }*/
                return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                    /*if (position == 0) {
                        // Set the hint text color gray
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }*/
                return view;
            }
        };
        adapterService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneService.setAdapter(adapterService);
    }

    @OnClick(R.id.tv_add_service_done)
    void onClickAddService() {
        if (TextUtils.isEmpty(etDesc.getText().toString())) {
            etDesc.setError("Enter Description");
            return;
        }

        if (TextUtils.isEmpty(etPrice.getText().toString())) {
            etPrice.setError("Enter Price");
            return;
        }

        if (TextUtils.isEmpty(etDays.getText().toString())) {
            etDays.setError("Enter time required");
            return;
        }
       /* if (TextUtils.isEmpty(imageBase64)) {
            Toast.makeText(this, "Select image", Toast.LENGTH_SHORT).show();
            return;
        }*/
        int serviceId = 0;
        String text = spinneService.getSelectedItem().toString();
        if (serviceResponseArrayList != null && !serviceResponseArrayList.isEmpty()) {
            for (int i = 0; i < serviceResponseArrayList.size(); i++) {
                if (serviceResponseArrayList.get(i).getName().equalsIgnoreCase(text)) {
                    serviceId = serviceResponseArrayList.get(i).getId();
                }
            }
        } else {
            serviceId = service.getId();
        }

        KProgressHUD kProgressHUD = CommonUtils.showProgressWithText(ActivityAddService.this);
        kProgressHUD.show();

        Call<ResponseBody> call = ApiClient.getClient().create(ApiInterface.class).
                addService(LocalStorage.getString(ActivityAddService.this, LocalStorage.X_USER_TOKEN, ""),
                        serviceId,
                        etDesc.getText().toString(),
                        String.valueOf(CommonUtils.getUserData(ActivityAddService.this).getId()),
                        etPrice.getText().toString(),
                        etDays.getText().toString(),
                        imageBase64);

       /* if (service != null) {
            call = ApiClient.getClient().create(ApiInterface.class).
                    updateService(LocalStorage.getString(ActivityAddService.this, LocalStorage.X_USER_TOKEN, ""),
                            "v1/artist_services/" + String.valueOf(((Service) spinneService.getSelectedItem()).getId()),
                            String.valueOf(((Service) spinneService.getSelectedItem()).getId()),
                            etDesc.getText().toString(),
                            String.valueOf(CommonUtils.getUserData(ActivityAddService.this).getId()),
                            etPrice.getText().toString(),
                            etDays.getText().toString(),
                            imageBase64);
        }*/
        call.enqueue(new Callback<ResponseBody>() {
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
                                Toast.makeText(ActivityAddService.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                finishAffinity();
                                startActivity(new Intent(ActivityAddService.this, MainActivityArtist.class));
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    case Constants.ERROR_CODE_INVALID:
                        finishAffinity();
                        startActivity(new Intent(ActivityAddService.this, LoginActivity.class));
                        break;
                    default:
                        try {
                            Toast.makeText(ActivityAddService.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                kProgressHUD.dismiss();
                Toast.makeText(ActivityAddService.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.iv_add_service_proof_of_work)
    void onClickAddImage() {
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] items = {getResources().getString(R.string.take_photo),
                getResources().getString(R.string.choose_from_library)};
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddService.this);
        builder.setTitle(getResources().getString(R.string.add_image));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.take_photo))) {
                    if (ContextCompat.checkSelfPermission(ActivityAddService.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                Constants.PERMISSION_CODE_ACCESS_CAMERA);
                    } else {
                        cameraIntent();
                    }
                } else if (items[item].equals(getResources().getString(R.string.choose_from_library))) {
                    if (ContextCompat.checkSelfPermission(ActivityAddService.this,
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
                bitmap = MediaStore.Images.Media.getBitmap(ActivityAddService.this.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bitmap != null) {
            GlideUtils.setImage(ActivityAddService.this, ivDocument, bitmap);
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
            if (ContextCompat.checkSelfPermission(ActivityAddService.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        Constants.PERMISSION_CODE_ACCESS_CAMERA);
            } else {
                cameraIntent();
            }
        } else if (requestCode == Constants.REQUEST_CODE_FILE) {
            if (ContextCompat.checkSelfPermission(ActivityAddService.this,
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
