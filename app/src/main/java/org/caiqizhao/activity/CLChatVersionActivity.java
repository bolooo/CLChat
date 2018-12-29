package org.caiqizhao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.bolo.chat.R;

public class CLChatVersionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clchat_version);
        ImageButton imageButton = findViewById(R.id.version_return_main_activity);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CLChatVersionActivity.this,Main.class);
                intent.putExtra("code",3);
                startActivity(intent);
                CLChatVersionActivity.this.finish();
            }
        });
    }
}
