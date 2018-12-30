package org.caiqizhao.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.bolo.chat.R;

import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.fragment.Me;

public class Main extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /**
     * toolbar创建
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
        switch (item.getItemId()){
            case R.id.add_contacks:
                Toast.makeText(this,"contacks",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
