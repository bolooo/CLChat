package com.example.bolo.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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

import org.caiqizhao.activity.Register;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.service.LoginService;
import org.caiqizhao.util.ToastUtil;
import org.caiqizhao.util.UsernameAndPasswordByIs;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button go; //登陆
    private CheckBox login_no_password; //记住密码
    private TextView username,password; //账户密码输入框
    private TextView register,login_to_password; //注册以及忘记密码
    private String user_name=null,user_password=null;
    public static Handler handler; //消息接收
    private Fragment chats;
    private Fragment contacks;
    private Fragment me;

    /**
     * 注册底部控件响应事件
     * taobolisb
     * Study Git
     */
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_Chats:
//                    replaceFragment(chats);
//                    return true;
//                case R.id.navigation_Contacts:
//                    replaceFragment(contacks);
//                    return true;
//                case R.id.navigation_Me:
//                    replaceFragment(me);
//                    return true;
//            }
//            return false;
//        }
//    };

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
//        setContentView(R.layout.activity_main);
        //initFragment();

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
     * 初始化fragment
     */
//    public void initFragment(){
//        chats = new Chats();
//        contacks = new Contacks();
//        me = new Me();
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,chats).show(chats).commit();
//    }

//    /**
//     * fragment替换事件
//     * @param fragment
//     */
//    public void replaceFragment(Fragment fragment){
//        FragmentManager ft = getSupportFragmentManager();
//        FragmentTransaction ftr= ft.beginTransaction();
//        ftr.replace(R.id.main_frame, fragment);
//        ftr.commit();
//    }

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
                data.putString("username",username.getText().toString());
                data.putString("password",password.getText().toString());
                loginservice.putExtras(data);
                startService(loginservice);

            }else {

                ToastUtil.showToast(MainActivity.this,"账户或密码格式不正确");
                username.setText("");
                password.setText("");
            }

        }
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
                        ArrayList<org.caiqizhao.entity.Message> messageList =
                                gson.fromJson(message,new TypeToken<List<org.caiqizhao.entity.Message>>(){}.getType());
                        org.caiqizhao.entity.Message.messageHasMap
                                .put(s, messageList);
                        messageList.clear();
                    }
                }


            }else {
                //获得消息中的数据并显示
                ToastUtil.showToast(MainActivity.this,str);
            }
        }
    }

//    public void
}
