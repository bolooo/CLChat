package org.caiqizhao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.bolo.chat.R;

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


    }
}
