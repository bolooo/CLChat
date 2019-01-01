package org.caiqizhao.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bolo.chat.R;

public class AddContacks extends AppCompatActivity {

    private SearchView searchView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacks);
        toolbar = findViewById(R.id.toolbar_addcontacks);
        setSupportActionBar(toolbar);
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
                Toast.makeText(AddContacks.this, query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
