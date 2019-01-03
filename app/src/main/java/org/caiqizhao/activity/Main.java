package org.caiqizhao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bolo.chat.R;

import org.caiqizhao.adapter.FriendListAdapter;
import org.caiqizhao.adapter.MessageListAdepter;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.fragment.Me;
import org.caiqizhao.service.DeleteFriendIntentService;
import org.caiqizhao.service.LogoutService;
import org.caiqizhao.service.NewFriendWaitingService;
import org.caiqizhao.service.getFriendMessgaeIntentService;
import org.caiqizhao.util.VariableUtil;

import q.rorbin.badgeview.QBadgeView;

public class Main extends AppCompatActivity {
    private Toolbar toolbar;  //toolbar顶部菜单栏
    BottomNavigationView navigation;   //底部菜单栏
    public static Handler handler ;
    QBadgeView badgeView_chat,new_friend ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        badgeView_chat = new QBadgeView(Main.this); //消息角标
        new_friend = new QBadgeView(Main.this);
        handler = new MessageUtil();
        replaceFragment(new Chats());   //初始化fragment

        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        badgeView_chat.bindTarget(navigation.findViewById(R.id.navigation_Contacts));
        badgeView_chat.setBadgeNumber(VariableUtil.frien_add_user);
        badgeView_chat.setBadgeTextColor(Color.RED);
        badgeView_chat.setBadgeTextColor(Color.WHITE);
        badgeView_chat.setBadgeGravity(Gravity.END | Gravity.TOP);


        //启动后台服务接收好友聊天消息
        Intent getMessage = new Intent(this,getFriendMessgaeIntentService.class) ;
        startService(getMessage);

        //开启后台服务接收好友添加消息
        Intent getNewFriend = new Intent(this,NewFriendWaitingService.class);
        startService(getNewFriend);


    }



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
                    replaceFragment(new Chats());
                    return true;
                case R.id.navigation_Contacts:
                    replaceFragment(new Contacks());
                    return true;
                case R.id.navigation_Me:
                    replaceFragment(new Me());
                    return true;
            }
            return false;
        }
    };


    /**
     * fragment替换事件
     *
     * @param fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction ftr = ft.beginTransaction();
        ftr.replace(R.id.main_frame, fragment);
        ftr.commit();
    }


    /**
     * toolbar创建
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = (MenuItem) menu.findItem(R.id.main_search);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(Main.this, SearchActivity.class));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * toolbar(添加好友)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contacks:
                Intent add_contacks = new Intent(this, AddContacksActivity.class);
                startActivity(add_contacks);
                break;
            default:
        }
        return true;
    }


    /**
     * 新好友列表（被添加）
     * @param view
     */
    public void newfriendlayoutclick(View view) {
        switch (view.getId()) {
            case R.id.new_friend_layout:
                Intent newfriend = new Intent(Main.this, NewFriend.class);
                startActivity(newfriend);
        }
    }


    //销毁活动时传递退出消息给服务器
    @Override
    protected void onDestroy() {
        super.onDestroy();


        //通知服务器退出账户
        Intent intent = new Intent(this,LogoutService.class);
        startService(intent);
    }

    private class MessageUtil extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x001:
                    badgeView_chat.bindTarget(navigation.findViewById(R.id.navigation_Chats));
                    badgeView_chat.setBadgeNumber(MessageListAdepter.count);
                    badgeView_chat.setBadgeTextColor(Color.RED);
                    badgeView_chat.setBadgeTextColor(Color.WHITE);
                    badgeView_chat.setBadgeGravity(Gravity.END | Gravity.TOP);
                    break;

                case 0x002:
                    badgeView_chat.bindTarget(navigation.findViewById(R.id.navigation_Contacts));
                    badgeView_chat.setBadgeNumber(VariableUtil.frien_add_user);
                    badgeView_chat.setBadgeTextColor(Color.RED);
                    badgeView_chat.setBadgeTextColor(Color.WHITE);
                    badgeView_chat.setBadgeGravity(Gravity.END | Gravity.TOP);
                    break;

            }
        }
    }


}
