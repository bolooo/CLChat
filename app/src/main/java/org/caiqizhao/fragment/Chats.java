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

import org.caiqizhao.adapter.MessageListAdepter;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.MessageListEntity;
import org.caiqizhao.entity.UserFriend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Chats extends Fragment {

    private View view;
    public static Context context;
    public List<MessageListEntity> messageListEntities = new ArrayList<MessageListEntity>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Chats.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Set<String> strings = Message.messageHasMap.keySet();  //获取消息列表所有的key 即用户朋友的id


        for(String friend_id:strings) {
            int i = Collections.binarySearch(UserFriend.userFriendList,friend_id); //查询朋友id在用户通讯录中的索引号
            if (i >= 0) {
                MessageListEntity messageListEntity = new MessageListEntity(UserFriend.userFriendList.get(i),
                        Message.messageHasMap.get(friend_id));
                messageListEntities.add(messageListEntity);
            }
        }

        RecyclerView message_list = view.findViewById(R.id.message_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        message_list.setLayoutManager(linearLayoutManager);
        MessageListAdepter messageListAdepter = new MessageListAdepter(messageListEntities);
        message_list.setAdapter(messageListAdepter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chats, container, false);
        Log.v("fragment", "chatview");
        return view;
    }
}
