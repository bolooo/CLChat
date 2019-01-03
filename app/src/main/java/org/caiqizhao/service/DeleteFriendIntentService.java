package org.caiqizhao.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;

import org.caiqizhao.activity.AddContacksActivity;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DeleteFriendIntentService extends IntentService {

    public DeleteFriendIntentService() {
        super("DeleteFriendIntentService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("fachu");
        final String friend_id = intent.getStringExtra("friend_id");
        final String user_id = intent.getStringExtra("user_id");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("user_id",user_id)
                .add("friend_id", friend_id)
                .build();
        Request request = new Request.Builder()
                .url(VariableUtil.Service_IP +"deleteFriend")
                .post(requestBody)
                .build();
        try {
             client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
