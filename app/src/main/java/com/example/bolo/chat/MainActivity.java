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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.caiqizhao.activity.Register;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.fragment.Me;
import org.caiqizhao.service.LoginService;
import org.caiqizhao.util.ToastUtil;
import org.caiqizhao.util.UsernameAndPasswordByIs;


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

        //setContentView(R.layout.login);
        setContentView(R.layout.activity_main);
        //initFragment();
        /*
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
