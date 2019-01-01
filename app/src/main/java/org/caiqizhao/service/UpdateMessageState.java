package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UpdateMessageState extends Service {

    Thread update;

    public UpdateMessageState() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String friend_id = intent.getStringExtra("friend_id");
        final String user_id = User.user.getUser_id();

        update = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id",user_id)
                        .add("friend_id", friend_id)
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"updateMessageState")
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
        update.start();
        return START_STICKY;
    }
}
