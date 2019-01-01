package org.caiqizhao.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.fragment.Me;
import org.caiqizhao.service.getFriendMessageService;

import java.util.List;

public class Main extends AppCompatActivity {
    private Toolbar toolbar;
    private List<Fragment> fragmentList;
    private ServiceConnection conn = new MyService();
    private getFriendMessageService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String code = intent.getStringExtra("code");
        switch (Integer.parseInt(code)) {
            case 1:
                replaceFragment(new Chats());
                break;

            case 2:
                replaceFragment(new Contacks());
                break;
            case 3:
                replaceFragment(new Me());
                break;
            default:
                replaceFragment(new Chats());
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_contacks:
                Intent add_contacks = new Intent(this, AddContacks.class);
                startActivity(add_contacks);
                break;
            default:
        }
        return true;
    }

    public void newfriendlayoutclick(View view) {
        switch (view.getId()) {
            case R.id.new_friend_layout:
                Intent newfriend = new Intent(Main.this, AddContacks.class);
                startActivity(newfriend);
        }
    }

    class MyService implements ServiceConnection {
        /***
         * 被绑定时，该方法将被调用
         * 本例通过Binder对象获得Service对象本身
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            service = (IBinder) ((getFriendMessageService.LocalBinder) service).getService();
        }

        /***
         * 绑定非正常解除时，如Service服务被异外销毁时，该方法将被调用
         * 将Service对象置为空
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
