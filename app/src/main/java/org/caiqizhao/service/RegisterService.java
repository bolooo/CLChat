package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.bolo.chat.MainActivity;

import org.caiqizhao.activity.Register;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterService extends Service {
    Thread register;
    public RegisterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = intent.getExtras();
        final String username = data.getString("username");
        final String password = data.getString("password");
        register = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",username)
                        .add("password",password)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP+"register")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String str = response.body().string();
                    if(!str.equals("注册成功")){
                        Message message = new Message();
                        message.what = 1;
                        Bundle data = new Bundle();
                        data.putString("register",str);
                        message.setData(data);
                        Register.handler.sendMessage(message);
                    }else {
                        Message message = new Message();
                        message.what = 2;
                        Register.handler.sendMessage(message);
                    }
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        register.start();
        return START_STICKY;
    }
}
