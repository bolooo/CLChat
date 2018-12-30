package org.caiqizhao.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bolo.chat.R;

import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.fragment.Me;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        int code = intent.getIntExtra("code",1);
        switch (code){
            case 1:
                replaceFragment(new Chats());
                break;

            case 2:
                replaceFragment(new Contacks());
                break;
            case  3:
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
     * @param fragment
     */
    public void replaceFragment(Fragment fragment){
        FragmentManager ft = getSupportFragmentManager();
        FragmentTransaction ftr= ft.beginTransaction();
        ftr.replace(R.id.main_frame, fragment);
        ftr.commit();
    }
}
