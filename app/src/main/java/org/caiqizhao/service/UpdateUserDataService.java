package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import com.example.bolo.chat.MainActivity;

import org.caiqizhao.activity.UpdateUserDataActivity;
import org.caiqizhao.entity.User;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateUserDataService extends Service {
    private  Thread update_user_data;
    private Bundle data;
    public UpdateUserDataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        data = intent.getExtras();
        int code = data.getInt("code");
        switch (code){
            case 1:
                update_user_data = new Thread(new UpdateUserName());
                update_user_data.start();
                break;
            case 2:
                update_user_data = new Thread(new UpdateUserSex());
                update_user_data.start();
                break;
            case 3:
                update_user_data = new Thread(new UpdateUserPassword());
                update_user_data.start();
                break;
            case 4:
                update_user_data = new Thread(new UpdateUserIntroduce());
                update_user_data.start();
                break;

        }
        return START_REDELIVER_INTENT;
    }

    private class UpdateUserName implements Runnable {

        @Override
        public void run() {
            String user_name = data.getString("user_name");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("code",1+"")
                    .add("user_name",user_name)
                    .add("user_id",User.user.getUser_id())
                    .build();
            Request request = new Request.Builder()
                    .url(VariableUtil.Service_IP +"update_user_data")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String str = response.body().string();

                Message message = new Message();
                Bundle data = new Bundle();
                data.putString("user_name",user_name);
                message.setData(data);
                if(Integer.parseInt(str)==1){
                    message.what = 0x001;
                    UpdateUserDataActivity.handler.sendMessage(message);
                }
                stopSelf();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateUserSex implements Runnable {
        @Override
        public void run() {
            String user_sex = data.getString("user_sex");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("code", 2 + "")
                    .add("user_sex", user_sex)
                    .add("user_id",User.user.getUser_id())
                    .build();
            Request request = new Request.Builder()
                    .url(VariableUtil.Service_IP + "update_user_data")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String str = response.body().string();

                Message message = new Message();
                Bundle data = new Bundle();
                data.putString("user_sex",user_sex);
                message.setData(data);
                if(Integer.parseInt(str)==1){
                    message.what = 0x002;
                    UpdateUserDataActivity.handler.sendMessage(message);
                }
                stopSelf();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateUserPassword implements Runnable{
        @Override
        public void run() {
            String user_password = data.getString("user_password");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("code", 3 + "")
                    .add("user_password", user_password)
                    .add("user_id",User.user.getUser_id())
                    .build();
            Request request = new Request.Builder()
                    .url(VariableUtil.Service_IP + "update_user_data")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String str = response.body().string();
                Message message = new Message();
                if(Integer.parseInt(str)==1){
                    message.what = 0x003;
                    UpdateUserDataActivity.handler.sendMessage(message);
                }else {
                    message.what = 0x004;
                    UpdateUserDataActivity.handler.sendMessage(message);
                }
                stopSelf();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateUserIntroduce implements Runnable{
        @Override
        public void run() {
            String user_introduce = data.getString("user_introduce");
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("code", 4 + "")
                    .add("user_introduce", user_introduce)
                    .add("user_id",User.user.getUser_id())
                    .build();
            Request request = new Request.Builder()
                    .url(VariableUtil.Service_IP + "update_user_data")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String str = response.body().string();
                Message message = new Message();
                Bundle data = new Bundle();
                data.putString("user_introduce",user_introduce);
                message.setData(data);
                if(Integer.parseInt(str)==1){
                    message.what = 0x004;
                    UpdateUserDataActivity.handler.sendMessage(message);
                }
                stopSelf();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
