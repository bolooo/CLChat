package com.example.bolo.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.caiqizhao.activity.Register;
import org.caiqizhao.chatview.Msg;
import org.caiqizhao.chatview.MsgAdapter;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.fragment.Me;
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
    private Fragment me;//
    /**
     * 注册底部控件响应事件
     * taobolisb
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_Chats:
                    replaceFragment(chats);
                    return true;
                case R.id.navigation_Contacts:
                    replaceFragment(contacks);
                    return true;
                case R.id.navigation_Me:
                    replaceFragment(me);
                    return true;
            }
            return false;
        }
    };

    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.chatview);
        initMsgs();
        setChatView();

        /* 三个fragment
        setContentView(R.layout.activity_main);
        initFragment();
        */

        /*  服务器连接
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
        */
    }

    /**
     * 初始化消息数据
     */
    public void initMsgs(){
        Msg msg1 = new Msg("hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("hello guy.", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("yo.", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

    /**
     * 初始化聊天界面
     */
    public void setChatView(){
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
    }


    /**
     * 初始化fragment
     */
    public void initFragment(){
        chats = new Chats();
        contacks = new Contacks();
        me = new Me();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,chats).show(chats).commit();
    }

    /**
     * fragment替换事件
     * @param fragment
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction ftr= ft.beginTransaction();
        ftr.replace(R.id.main_frame, fragment);
        ftr.commit();
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
    private class MessageUtil extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String str = data.getString("login");
            if(msg.what == 0x003){
                //获得消息中的数据并显示
            }else {
                ToastUtil.showToast(MainActivity.this,str);
            }
        }
    }
}
