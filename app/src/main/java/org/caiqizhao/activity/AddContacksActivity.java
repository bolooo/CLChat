package org.caiqizhao.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bolo.chat.R;
import com.google.gson.Gson;

import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.util.ToastUtil;

import java.util.Collections;


public class AddContacksActivity extends AppCompatActivity {

    private SearchView searchView;
    private Toolbar toolbar;
    private LinearLayout friendinfo;
    public static Handler handler;
    private Button addContacksB;
    UserFriend friend = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacks);
        friendinfo = findViewById(R.id.friendinfo_layout);
        toolbar = findViewById(R.id.toolbar_addcontacks);
        setSupportActionBar(toolbar);
        addContacksB = findViewById(R.id.add_contacksB);
        addContacksB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String friend_id = friend.getFriend_id();
                String user_id = User.user.getUser_id();
                String code = "1";
                Intent AddContacksService = new Intent(AddContacksActivity.this, org.caiqizhao.service.AddContacksService.class);
                AddContacksService.putExtra("friend_id",friend_id);
                AddContacksService.putExtra("user_id", user_id);
                AddContacksService.putExtra("code", code);
                startService(AddContacksService);
            }
        });
        handler = new MyHandler();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Username");
        SearchView.SearchAutoComplete mSearchAutoComplete = (SearchView.SearchAutoComplete)
                searchView.findViewById(R.id.search_src_text);

        //设置输入框提示文字样式
        mSearchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white,null));//设置提示文字颜色
        mSearchAutoComplete.setTextColor(getResources().getColor(android.R.color.white,null));//设置内容文字颜色

        //设置最右侧的提交按钮
        searchView.setSubmitButtonEnabled(true);
        searchView.setInputType(1);// .setImeOptions(SearchView.);
        //展开SearchView
        searchItem.expandActionView();
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });

        //添加好友响应
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String friend_id = query;
                if (Collections.binarySearch(UserFriend.userFriendList, friend_id) < 0) {
                    String user_id = User.user.getUser_id();
                    String code = "3";
                    Intent AddContacksService = new Intent(AddContacksActivity.this, org.caiqizhao.service.AddContacksService.class);
                    AddContacksService.putExtra("friend_id", friend_id);
                    AddContacksService.putExtra("user_id", user_id);
                    AddContacksService.putExtra("code", code);
                    startService(AddContacksService);
                } else{
                    ToastUtil.showToast(AddContacksActivity.this, "已添加该好友");
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String str = data.getString("addContacks");
            if(msg.what == 0x002){
                Gson gson = new Gson();
                friend =  gson.fromJson(str, UserFriend.class);
                friendinfo.setVisibility(View.VISIBLE);
                TextView friend_name =  friendinfo.findViewById(R.id.add_contacksT);
                friend_name.setText(friend.getFriend_name());
            }else {
                //获得消息中的数据并显示
                ToastUtil.showToast(AddContacksActivity.this,str);
            }

        }
    }
}
