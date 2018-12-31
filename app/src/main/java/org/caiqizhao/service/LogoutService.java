package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.bolo.chat.MainActivity;

import org.caiqizhao.entity.User;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogoutService extends Service {

    Thread logout;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String user_id = User.user.getUser_id();
        logout = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id",user_id)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"logout")
                        .post(requestBody)
                        .build();
                try {
                    client.newCall(request).execute();
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        logout.start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
