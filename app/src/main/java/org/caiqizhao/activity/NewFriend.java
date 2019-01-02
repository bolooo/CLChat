 package org.caiqizhao.activity;

import android.os.Message;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.view.View;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.util.VariableUtil;

import org.caiqizhao.adapter.NewFriendListAdapter;


 public class NewFriend extends AppCompatActivity {
     private SearchView searchView;
     private Toolbar toolbar;
     private RecyclerView newFriendRecycleView;

     @Override
     protected void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         System.out.println("进来了"+UserFriend.friend_add_user);
         setContentView(R.layout.activity_new_friend);
         VariableUtil.frien_add_user = 0;
         Message message = new Message();
         message.what = 0x002;
         Main.handler.sendMessage(message);
         Message message2 = new Message();
         Contacks.handler.sendMessage(message2);
         initToolbar();
         initAdapter();
     }

     public void initAdapter() {
         newFriendRecycleView = findViewById(R.id.new_friend_list);
         LinearLayoutManager layoutManager = new LinearLayoutManager(this);
         newFriendRecycleView.setLayoutManager(layoutManager);
         NewFriendListAdapter adapter = new NewFriendListAdapter();
         newFriendRecycleView.setAdapter(adapter);
     }

     public void initToolbar() {
         toolbar = findViewById(R.id.toolbar_newfriend);
         setSupportActionBar(toolbar);


         /*返回事件*/
         toolbar.setNavigationOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(NewFriend.this, Main.class);
                 startActivity(intent);
                 NewFriend.this.finish();
             }
         });

     }
 }
