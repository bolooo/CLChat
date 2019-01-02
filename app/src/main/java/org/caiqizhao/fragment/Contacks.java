package org.caiqizhao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bolo.chat.R;

import org.caiqizhao.activity.AddContacks;
import org.caiqizhao.activity.Main;
import org.caiqizhao.adapter.FriendListAdapter;

public class Contacks extends Fragment {
    private View view;
    public  Context context;
    private LinearLayout newfriendlayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*初始化通讯录页面的list内容*/
        RecyclerView friendlist = view.findViewById(R.id.friend_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        friendlist.setLayoutManager(linearLayoutManager);
        friendlist.findViewById(R.id.friendlist_photo);
        FriendListAdapter friendListAdapter = new FriendListAdapter();
        friendlist.setAdapter(friendListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacks, container, false);
        Log.v("fragment", "friendlist");
        return view;
    }

}
