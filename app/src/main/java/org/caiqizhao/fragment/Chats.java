package org.caiqizhao.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolo.chat.R;

import org.caiqizhao.adapter.MessageListAdepter;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.MessageListEntity;
import org.caiqizhao.entity.UserFriend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Chats extends Fragment {

    public List<MessageListEntity> messageListEntities = new ArrayList<MessageListEntity>(); //聊天列表（适配器实体类）
    private View view;    //布局
    public  Context context;    //活动上下文
    MessageListAdepter messageListAdepter;
    public static Handler handler;  //刷新消息

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //消息接收
        handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                initAdepter();
            }
        };


        initAdepter();

    }

    /**
     * 初始化适配器（加载聊天列表）
     */
    private void initAdepter() {
        messageListEntities.clear();
        Set<String> strings = Message.messageHasMap.keySet();
        for(String friend_id:strings) {
            int i = Collections.binarySearch(UserFriend.userFriendList,friend_id); //查询朋友id在用户通讯录中的索引号
            if (i >= 0) {
                MessageListEntity messageListEntity = new MessageListEntity(UserFriend.userFriendList.get(i),
                        Message.messageHasMap.get(friend_id));
                messageListEntities.add(messageListEntity);
            }
        }
        Collections.sort(messageListEntities);
        RecyclerView message_list = view.findViewById(R.id.message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        message_list.setLayoutManager(linearLayoutManager);
        messageListAdepter = new MessageListAdepter(messageListEntities);
        message_list.setAdapter(messageListAdepter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats, container, false);
        return view;
    }


}
