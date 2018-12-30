package com.example.bolo.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.caiqizhao.activity.Main;
import org.caiqizhao.activity.Register;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.service.LoginService;
import org.caiqizhao.util.PasswordMD5Util;
import org.caiqizhao.util.ToastUtil;
import org.caiqizhao.util.UsernameAndPasswordByIs;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button go; //登陆
    private CheckBox login_no_password; //记住密码
    private TextView username,password; //账户密码输入框
    private TextView register,login_to_password; //注册以及忘记密码
    private String user_name=null,user_password=null;
    public static Handler handler; //消息接收

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);


        go = findViewById(R.id.login_go);
        login_no_password = findViewById(R.id.login_no_password);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        register = findViewById(R.id.login_no_register);
        login_to_password = findViewById(R.id.login_to_password);

        go.setOnClickListener(new go_Click());

        register.setOnClickListener(new register_Click());

        handler = new MessageUtil();

    }




    /**
     * 注册按钮响应时间
     */
    private class register_Click implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        }
    }

    /**
     * 登陆按钮响应时间
     */
    private class go_Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(UsernameAndPasswordByIs.checkusername(username.getText().toString())
                    && UsernameAndPasswordByIs.checkPassword(password.getText().toString())){
                Intent loginservice = new Intent(MainActivity.this,LoginService.class);
                Bundle data = new Bundle();
                data.putString("user_ip",getIPAddress(MainActivity.this));
                data.putString("username",username.getText().toString());
                data.putString("password",PasswordMD5Util.generateMD5(password.getText().toString()));
                loginservice.putExtras(data);
                startService(loginservice);
            }else {

                ToastUtil.showToast(MainActivity.this,"账户或密码格式不正确");
                username.setText("");
                password.setText("");
            }

        }
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


    /**
     * 消息处理内部类
     */
    @SuppressLint("HandlerLeak")
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String str = data.getString("login");
            if(msg.what == 0x003){
                Gson gson = new Gson();

                //解开第一层
                JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();

                //用户添加其他人的信息（未确认）
                JsonArray user_add_friend = jsonObject.getAsJsonArray("user_add_frend");
                if(user_add_friend!=null){
                    UserFriend.uer_add_friend = gson.fromJson(user_add_friend,new TypeToken<List<UserFriend>>(){}.getType());
                }

                //其他人添加用户的信息(没确认)
                JsonArray friend_add_user = jsonObject.getAsJsonArray("friend_add_user");
                if(friend_add_user!=null){
                    UserFriend.friend_add_user = gson.fromJson(friend_add_user,new TypeToken<List<UserFriend>>(){}.getType());
                }

                //得到用户信息
                JsonElement user = jsonObject.get("user");
                User.user = gson.fromJson(user,User.class);


                //得到好友信息
                JsonArray user_friend = jsonObject.getAsJsonArray("friend_name");
                if(user_friend!=null){
                    UserFriend.userFriendList = gson.fromJson(user_friend,new TypeToken<List<UserFriend>>(){}.getType());
                }



                //得道聊天记录
                jsonObject = jsonObject.getAsJsonObject("friend_message");
                if(jsonObject!=null){
                    //跟哪些朋友有聊天记录
                    JsonArray message_friend_id = jsonObject.getAsJsonArray("message_friend_id");
                    List<String> stringList = gson.fromJson(message_friend_id,new TypeToken<List<String>>(){}.getType());

                    //循环分别得到好友的聊天记录
                    for (String s:stringList){
                        JsonArray message = jsonObject.getAsJsonArray(s);
                        ArrayList<org.caiqizhao.entity.Message> messageList = new ArrayList<org.caiqizhao.entity.Message>();
                        messageList = gson.fromJson(message,new TypeToken<List<org.caiqizhao.entity.Message>>(){}.getType());
                        org.caiqizhao.entity.Message.messageHasMap
                                .put(s, messageList);

                    }
                }

                Intent go_main = new Intent(MainActivity.this,Main.class);
                startActivity(go_main);
                MainActivity.this.finish();

            }else {
                //获得消息中的数据并显示
                ToastUtil.showToast(MainActivity.this,str);
            }
        }
    }
}
