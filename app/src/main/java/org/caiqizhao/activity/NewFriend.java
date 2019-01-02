 package org.caiqizhao.activity;

import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bolo.chat.R;

import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.util.VariableUtil;

 public class NewFriend extends AppCompatActivity {
     private SearchView searchView;
     private Toolbar toolbar;

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_new_friend);
         VariableUtil.frien_add_user = 0;
         Message message = new Message();
         message.what = 0x002;
         Main.handler.sendMessage(message);
         Contacks.handler.sendMessage(message);
         toolbar = findViewById(R.id.toolbar_newfriend);
         setSupportActionBar(toolbar);
     }


}
