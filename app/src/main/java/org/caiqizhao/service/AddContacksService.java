package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import org.caiqizhao.activity.AddContacksActivity;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddContacksService extends Service {
    Thread addContacks;

    public AddContacksService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String friend_id = intent.getStringExtra("friend_id");
        final String user_id = intent.getStringExtra("user_id");
        final String code = intent.getStringExtra("code");
        addContacks = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id",user_id)
                        .add("friend_id", friend_id)
                        .add("code", code)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"addFriend")
                        .post(requestBody)
                        .build();
                try {
                    Response response =  client.newCall(request).execute();
                    if(code.equals("3")) {
                        String res = response.body().string();
                        Message message = new Message();
                        Bundle data = new Bundle();
                        data.putString("addContacks", res);
                        message.setData(data);
                        if (res.equals("用户不存在")) {
                            message.what = 0x001;
                            AddContacksActivity.handler.sendMessage(message);
                        } else {
                            message.what = 0x002;
                            AddContacksActivity.handler.sendMessage(message);
                        }
                    }
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        addContacks.start();
        return START_STICKY;
    }
}
