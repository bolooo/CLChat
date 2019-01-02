package org.caiqizhao.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.google.gson.Gson;

import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.util.VariableUtil;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NewFriendWaitingService extends IntentService {

    public NewFriendWaitingService() {
        super("NewFriendWaitingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (true) {
            String user_id = User.user.getUser_id();
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("user_id", user_id)
                    .build();
            Request request = new Request.Builder()
                    .url(VariableUtil.Service_IP + "newFriend")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String str = response.body().string();
                if (!str.equals("0")) {
                    Gson gson = new Gson();

                }
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
