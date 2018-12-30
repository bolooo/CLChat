package org.caiqizhao.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.User;
import org.caiqizhao.service.UpdateUserDataService;

public class UpdateUserDataActivity extends AppCompatActivity {

    private LinearLayout update_user_name;
    private LinearLayout update_user_password;
    private LinearLayout getUpdate_user_sex;
    private LinearLayout update_user_introduce;
    private ImageButton update_return_main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user_data);
        init();
    }

    private void init() {

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

        //更新用户昵称（网名）
        update_user_name = findViewById(R.id.update_nuser_name);
        update_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText newname = new EditText(UpdateUserDataActivity.this);
                AlertDialog.Builder inputDialog =
                        new AlertDialog.Builder(UpdateUserDataActivity.this);
                inputDialog.setTitle("请输入新的用户昵称");
                inputDialog.setView(newname);
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!newname.getText().toString().trim().equals("")) {
                            Intent update_name_service = new Intent(UpdateUserDataActivity.this,UpdateUserDataService.class);
                            Bundle data = new Bundle();
                            data.putInt("code",1);
                            data.putString("user_id",User.user.getUser_name());
                            data.putString("user_name",newname.getText().toString());
                            update_name_service.putExtras(data);
                            startService(update_name_service);

                        }
                    }
                });
                inputDialog.show();
            }
        });

    }
}
