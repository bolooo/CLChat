package org.caiqizhao.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.bolo.chat.MainActivity;
import com.example.bolo.chat.R;

import org.caiqizhao.service.RegisterService;
import org.caiqizhao.util.ToastUtil;
import org.caiqizhao.util.UsernameAndPasswordByIs;
import org.caiqizhao.util.VerityCode;

public class Register extends AppCompatActivity {

    private EditText uername,password,again_password,verify_on; //界面编辑框控件
    private Button go; //确定注册按钮
    private ImageButton verify,return_login; //验证码刷新以及返回登陆界面按钮

    public static Handler handler; //消息接收

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        init();
    }

    /**
     * 控件初始化
     */
    private void init() {
        //获得控件
        uername = findViewById(R.id.username);
        password = findViewById(R.id.password);
        again_password = findViewById(R.id.password_again);
        verify_on =findViewById(R.id.verify_on);
        go = findViewById(R.id.go_register);
        verify = findViewById(R.id.verify);
        return_login = findViewById(R.id.return_login);

        //消息接收
        handler = new MessageUtil();

        //生产首张验证码
        verify.setImageBitmap(VerityCode.getInstance().createBitmap());

        //按钮响应事件注册
        verify.setOnClickListener(new ReturnLoginCilck());
        go.setOnClickListener(new ReturnLoginCilck());
        return_login.setOnClickListener(new ReturnLoginCilck());

    }

    /**
     * 界面按钮响应事件内部类
     */
    private class ReturnLoginCilck implements View.OnClickListener {
        Intent intent = null;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.return_login:
                    //摧毁本活动返回登陆界面
                    intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    Register.this.finish();
                    break;
                case R.id.go_register:
                    //注册验证
                    if (uername.getText().toString().equals("")){
                        ToastUtil.showToast(Register.this,"账户不能为空");
                        verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                        return;
                    }
                    if(password.getText().toString().equals("")||again_password.getText().toString().equals("")){
                        ToastUtil.showToast(Register.this,"密码不能为空");
                        verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                        return;
                    }
                    if(verify_on.getText().toString().equals("")){
                        ToastUtil.showToast(Register.this,"验证码不能为空");
                        verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                        return;
                    }
                    if(UsernameAndPasswordByIs.checkusername(uername.getText().toString())){
                        if(UsernameAndPasswordByIs.checkPassword(password.getText().toString())){
                            if(password.getText().toString().equals(again_password.getText().toString())){
                                if(verify_on.getText().toString().toLowerCase()
                                        .equals(VerityCode.getInstance().getCode().toLowerCase())){
                                    //启动服务向服务器发送请求验证注册信息
                                    intent = new Intent(Register.this,RegisterService.class);
                                    Bundle data = new Bundle();
                                    data.putString("username",uername.getText().toString());
                                    data.putString("password",password.getText().toString());
                                    intent.putExtras(data);
                                    startService(intent);
                                }else {
                                    ToastUtil.showToast(Register.this,"请输入正确的验证码");
                                    System.out.println(VerityCode.getInstance().getCode());
                                    verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                                }
                            }else {
                                ToastUtil.showToast(Register.this,"两次输入密码不一致");
                                verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                            }
                        }else {
                            ToastUtil.showToast(Register.this,"密码格式不正确");
                            verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                        }
                    }else {
                        ToastUtil.showToast(Register.this,"账户格式不正确");
                        verify.setImageBitmap(VerityCode.getInstance().createBitmap());
                    }
                    break;
                case R.id.verify:
                    //验证码刷新
                    verify.setImageBitmap(null);
                    verify.setImageBitmap(VerityCode.getInstance().createBitmap());

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
            if(msg.what == 1){
                //获得消息中的数据并显示
                Bundle data = msg.getData();
                String str = data.getString("register");
                ToastUtil.showToast(Register.this,str);
                verify.setImageBitmap(VerityCode.getInstance().createBitmap());
            }else if(msg.what == 2){
                //返回登陆界面
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                Register.this.finish();
            }

        }
    }
}
