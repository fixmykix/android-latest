package com.app.fixmykix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixmykix.R;
import com.app.fixmykix.adapters.ChatRecyclerAdapter;
import com.app.fixmykix.api_manager.ApiClient;
import com.app.fixmykix.api_manager.ApiInterface;
import com.app.fixmykix.model.ChatDataItem;
import com.app.fixmykix.model.ChatResponse;
import com.app.fixmykix.model.GetAddressListResponse;
import com.app.fixmykix.model.ServiceRequest;
import com.app.fixmykix.storage_manager.LocalStorage;
import com.app.fixmykix.utils.CommonUtils;
import com.app.fixmykix.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends Activity {

    @BindView(R.id.et_message_box)
    EditText edtMessageTxt;


    @BindView(R.id.rv_chat)
    RecyclerView rvChat;

    private List<ChatDataItem> chatDataItems = new ArrayList<>();

    private ChatRecyclerAdapter chatRecyclerAdapter;
    private String senderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            senderId = getIntent().getStringExtra("senderId");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        rvChat.setLayoutManager(layoutManager);
        getAllMessages();
    }

    @OnClick(R.id.iv_back_about_us)
    void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.imgsend)
    void onclickSend() {
        sendChatMessages(edtMessageTxt.getText().toString());
    }

    private void getAllMessages() {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ChatActivity.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                getChatMessages(LocalStorage.getString(ChatActivity.this, LocalStorage.X_USER_TOKEN, ""),
                       senderId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    Log.d("JsonObject Response", jsonObject.toString());
                                    Gson gson = new Gson();
                                    ChatResponse chatResponse = gson.fromJson(jsonObject.toString(), ChatResponse.class);
                                    chatDataItems = chatResponse.getData();
                                    chatRecyclerAdapter = new
                                            ChatRecyclerAdapter(ChatActivity.this, chatDataItems);
                                    rvChat.setAdapter(chatRecyclerAdapter);
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ChatActivity.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }

    private void sendChatMessages(String txt) {
        ProgressDialog progressDialog = CommonUtils.getProgressBar(ChatActivity.this);
        progressDialog.show();

        ApiClient.getClient().create(ApiInterface.class).
                sendChatMessages(LocalStorage.getString(ChatActivity.this, LocalStorage.X_USER_TOKEN, ""),
                        senderId, txt)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressDialog.dismiss();
                        switch (response.code()) {
                            case Constants.SUCCESS_CODE:
                            case Constants.SUCCESS_CODE_SECOND:
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    Log.d("JsonObject Response", jsonObject.toString());
                                    ChatDataItem chatDataItem = new ChatDataItem();
                                    chatDataItem.setText(txt);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        chatDataItem.setSenderId(Math.toIntExact(CommonUtils.getUserData(ChatActivity.this).getId()));
                                    }
                                    chatDataItems.add(chatDataItem);
                                    edtMessageTxt.setText("");
                                    if (chatRecyclerAdapter == null) {
                                        chatRecyclerAdapter = new
                                                ChatRecyclerAdapter(ChatActivity.this, chatDataItems);
                                        rvChat.setAdapter(chatRecyclerAdapter);
                                        return;
                                    }
                                    chatRecyclerAdapter.notifyDataSetChanged();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case Constants.ERROR_CODE_INVALID:
                                finishAffinity();
                                startActivity(new Intent(ChatActivity.this, LoginActivity.class));
                                break;
                            default:
                                try {
                                    Toast.makeText(ChatActivity.this, new JSONObject(response.errorBody().string()).getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
    }

}
