package org.caiqizhao.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.caiqizhao.activity.Main;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.fragment.Contacks;
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
                Gson gson = new Gson();

                //解开第一层
                JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();

                //用户添加其他人的信息（未确认）
                JsonArray user_add_friend = jsonObject.getAsJsonArray("user_add_frend");
                List<UserFriend> friendList = null;
                if(user_add_friend!=null){
                     friendList = gson.fromJson(user_add_friend,new TypeToken<List<UserFriend>>(){}.getType());
                    if(friendList.size()!=UserFriend.uer_add_friend.size()){
                        UserFriend.uer_add_friend.clear();
                        UserFriend.uer_add_friend = friendList;
                    }
                }

                //其他人添加用户的信息(没确认)
                JsonArray friend_add_user = jsonObject.getAsJsonArray("friend_add_user");
                if(friend_add_user!=null){
                    friendList.clear();
                    friendList = gson.fromJson(friend_add_user,new TypeToken<List<UserFriend>>(){}.getType());
                    if (friendList.size() > UserFriend.friend_add_user.size()){
                        VariableUtil.frien_add_user = friendList.size() - UserFriend.friend_add_user.size();
                        UserFriend.friend_add_user.clear();
                        UserFriend.friend_add_user = friendList;
                        Message message = new Message();
                        message.what = 0x002;
                        Contacks.handler.sendMessage(message);
                        Main.handler.sendMessage(message);
                    }
                }
                //得到好友信息
                JsonArray user_friend = jsonObject.getAsJsonArray("friend_name");
                if(user_friend!=null){
                    friendList.clear();
                    friendList = gson.fromJson(user_friend,new TypeToken<List<UserFriend>>(){}.getType());
                    if(UserFriend.userFriendList.size()!=friendList.size()){
                        UserFriend.userFriendList.clear();
                        UserFriend.userFriendList = friendList;
                        Message message = new Message();
                        Contacks.handler.sendMessage(message);
                    }
                }

                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
