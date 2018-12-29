package org.caiqizhao.fragment;

import android.content.Context;
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

import com.example.bolo.chat.R;

import org.caiqizhao.adapter.FriendListAdapter;

public class Contacks extends Fragment {
    private View view;
    public static Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Chats.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView friendlist = view.findViewById(R.id.friend_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        friendlist.setLayoutManager(linearLayoutManager);
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
