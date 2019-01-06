package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.bolo.chat.MainActivity;

import org.caiqizhao.util.VariableUtil;

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
        final String user_ip = data.getString("user_ip");
        login = new Thread(new Runnable() {
            @Override
            public void run() {
                    OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username",username)
                        .add("password",password)
                        .add("user_ip",user_ip)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"login")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String str = response.body().string();

                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString("login",str);
                    message.setData(data);
                    if(str.equals("密码错误")){
                        message.what = 0x001;
                        MainActivity.handler.sendMessage(message);
                    }else if (str.equals("账户不存在")){
                        message.what = 0x002;
                        MainActivity.handler.sendMessage(message);
                    }else {
                        message.what = 0x003;
                        MainActivity.handler.sendMessage(message);
                    }
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        login.start();
        return START_STICKY;
    }
}
