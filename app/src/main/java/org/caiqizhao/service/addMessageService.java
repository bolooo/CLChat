package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.bolo.chat.MainActivity;

import org.caiqizhao.util.VariableUtil;

import java.io.EOFException;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addMessageService extends Service {
    public addMessageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String json = intent.getStringExtra("json");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("message",json)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"addMessage")
                        .post(requestBody)
                        .build();
                try {
                    client.newCall(request).execute();
                }  catch (Exception e){}
            }
        }).start();
        return START_REDELIVER_INTENT;
    }
}
