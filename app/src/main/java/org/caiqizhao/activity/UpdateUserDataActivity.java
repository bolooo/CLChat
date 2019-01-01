package org.caiqizhao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.User;
import org.caiqizhao.service.UpdateUserDataService;
import org.caiqizhao.util.PasswordMD5Util;
import org.caiqizhao.util.PasswordSHA1Util;
import org.caiqizhao.util.ToastUtil;

public class UpdateUserDataActivity extends AppCompatActivity {

    private LinearLayout update_user_name;
    private LinearLayout update_user_password;
    private LinearLayout update_user_sex;
    private LinearLayout update_user_introduce;
    private ImageButton update_return_main_activity;
    private TextView user_name, user_sex,user_introduce;

    public static Handler handler; //消息接收

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_data);
        init();
    }

    private void init() {

        handler = new MessageHanlerUtil();

        //返回图标响应事件
        update_return_main_activity = findViewById(R.id.update_return_main_activity);
        update_return_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateUserDataActivity.this,Main.class);
                intent.putExtra("code",3);
                startActivity(intent);
                UpdateUserDataActivity.this.finish();
            }
        });

        initUpadteUserName();
        initUpdateUserPassword();
        initUpdateUserSex();
        initUpdateuserIntroduce();

    }

    /**
     * 初始化变更网名事件
     */
    private void initUpadteUserName(){
        //更新用户昵称（网名）
        update_user_name = findViewById(R.id.update_nuser_name);
        user_name = findViewById(R.id.user_name);
        user_name.setText(User.user.getUser_name());
        update_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //依附于对话框的编辑栏
                final EditText newname = new EditText(UpdateUserDataActivity.this);

                //新建对话框
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(UpdateUserDataActivity.this);
                inputDialog.setTitle("请输入新的用户昵称");
                inputDialog.setView(newname);  //设置对话框中的控件

                //取消按钮响应事件
                inputDialog.setNegativeButton("取消",null);

                //确定按钮响应事件
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启服务，连接服务器更改信息
                        if(!newname.getText().toString().trim().equals("")) {
                            Intent update_name_service = new Intent(UpdateUserDataActivity.this,UpdateUserDataService.class);
                            Bundle data = new Bundle();
                            data.putInt("code",1);      //服务识别码用于通知服务器执行哪类更改业务
                            data.putString("user_name",newname.getText().toString().trim());
                            update_name_service.putExtras(data);
                            startService(update_name_service);

                        }
                    }
                });
                inputDialog.show();
            }
        });
    }


    /**
     * 初始化更改密码事件
     */
    private void initUpdateUserPassword(){
        //更改密码
        update_user_password = findViewById(R.id.update_user_password);
        update_user_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.update_password,
                        (ViewGroup) findViewById(R.id.dialog));
                final AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(UpdateUserDataActivity.this);
                inputDialog.setTitle("更改密码");
                inputDialog.setView(layout);
                inputDialog.setNegativeButton("取消",null);
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String password_str = ((TextView)(layout.findViewById(R.id.user_password))).getText().toString();
                        String newpassword_str = ((TextView)layout.findViewById(R.id.new_password)).getText().toString();
                        String newpassword_agin_str = ((TextView)layout.findViewById(R.id.agin_new_passwoed)).getText().toString();

                        //客户端对密码的验证
                        if(password_str.trim().equals("")||
                                newpassword_str.trim().equals("")||
                                newpassword_agin_str.trim().equals("")){
                            ToastUtil.showToast(UpdateUserDataActivity.this,"密码编辑框不能为空白");
                        }else {
                            password_str = PasswordMD5Util.generateMD5(password_str);
                            password_str = PasswordSHA1Util.generateSHA1(password_str);
                            if(password_str.equals(User.user.getUser_password())){
                                if(newpassword_str.equals(newpassword_agin_str)){
                                    Intent update_user_password = new Intent(UpdateUserDataActivity.this,UpdateUserDataService.class);
                                    Bundle data = new Bundle();
                                    data.putInt("code",3);
                                    data.putString("user_password",PasswordMD5Util.generateMD5(newpassword_agin_str));
                                    update_user_password.putExtras(data);
                                    startService(update_user_password);
                                }else {
                                    ToastUtil.showToast(UpdateUserDataActivity.this,"两次输入密码不一致");
                                }
                            }else {
                                ToastUtil.showToast(UpdateUserDataActivity.this,"旧密码不正确");
                            }
                        }
                    }
                });
                inputDialog.show();
            }
        });
    }


    /**
     * 性别设置
     */
    private void initUpdateUserSex() {
        final int[] yourChoice = {0}; //用于确定选择项
        update_user_sex = findViewById(R.id.update_user_sex);
        user_sex = findViewById(R.id.user_sex);
        user_sex.setText(User.user.getUser_sex());
        update_user_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] items = {"男", "女"};
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(UpdateUserDataActivity.this);
                singleChoiceDialog.setTitle("请选择");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice[0] = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (yourChoice[0] != -1) {
                                    Intent update_user_sex = new Intent(UpdateUserDataActivity.this, UpdateUserDataService.class);
                                    Bundle data = new Bundle();
                                    data.putInt("code", 2);
                                    data.putString("user_sex", items[yourChoice[0]]);
                                    update_user_sex.putExtras(data);
                                    startService(update_user_sex);
                                }
                            }
                        });
                singleChoiceDialog.show();
            }

        });

    }


    /**
     * 初始化用户的个性签名
     */
    private void initUpdateuserIntroduce(){
        update_user_introduce = findViewById(R.id.update_user_introduce);
        user_introduce = findViewById(R.id.user_introduce);
        user_introduce.setText(User.user.getUser_introduce());
        update_user_introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //依附于对话框的编辑栏
                final EditText new_user_introduce = new EditText(UpdateUserDataActivity.this);

                //新建对话框
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(UpdateUserDataActivity.this);
                inputDialog.setTitle("个性签名");
                inputDialog.setView(new_user_introduce);  //设置对话框中的控件

                //取消按钮响应事件
                inputDialog.setNegativeButton("取消",null);

                //确定按钮响应事件
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //开启服务，连接服务器更改信息
                        if(!new_user_introduce.getText().toString().trim().equals("")) {
                            Intent update_name_service = new Intent(UpdateUserDataActivity.this,UpdateUserDataService.class);
                            Bundle data = new Bundle();
                            data.putInt("code",4);      //服务识别码用于通知服务器执行哪类更改业务
                            data.putString("user_introduce",new_user_introduce.getText().toString().trim());
                            update_name_service.putExtras(data);
                            startService(update_name_service);

                        }
                    }
                });
                inputDialog.show();
            }
        });

    }


    private class MessageHanlerUtil extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            switch (msg.what){
                case 0x001:
                    User.user.setUser_name(data.getString("user_name"));
                    user_name.setText(User.user.getUser_name());
                    LocalBroadcastManager localBroadcastManager =
                            LocalBroadcastManager.getInstance(UpdateUserDataActivity.this);
                    Intent intent = new Intent("com.example.mycloud.UPDATA_USER");
                    intent.putExtra("user_name",User.user.getUser_name());
                    localBroadcastManager.sendBroadcast(intent);
                    break;
                case 0x002:
                    User.user.setUser_sex(data.getString("user_sex"));
                    user_sex.setText(User.user.getUser_sex());
                    break;
                case 0x003:
                    ToastUtil.showToast(UpdateUserDataActivity.this,"密码更改成功");
                    break;
                case 0x004:
                    User.user.setUser_introduce(data.getString("user_introduce"));
                    user_introduce.setText(User.user.getUser_introduce());
                    break;
                case 0x005:
                    ToastUtil.showToast(UpdateUserDataActivity.this,"密码更改失败，请重新尝试");
                    break;
            }

        }
    }

}
