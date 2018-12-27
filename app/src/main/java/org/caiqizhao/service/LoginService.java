package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.bolo.chat.MainActivity;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginService extends Service {
    Thread login;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = intent.getExtras();
        final String username = data.getString("username");
        final String password = data.getString("password");
        login = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",username)
                        .add("password",password)
                        .build();
                Request request = new Request.Builder()
                        .url("http://192.168.253.5:8080/login")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String str = response.body().string();

                    if(!str.equals("登陆成功")){
                        Message message = new Message();
                        message.what = 0x001;
                        Bundle data = new Bundle();
                        data.putString("login",str);
                        message.setData(data);
                        MainActivity.handler.sendMessage(message);
                    }else {
                        Message message = new Message();
                        message.what = 0x002;
                        Bundle data = new Bundle();
                        data.putString("login",str);
                        message.setData(data);
                        MainActivity.handler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        login.start();
        return START_STICKY;
    }


}
