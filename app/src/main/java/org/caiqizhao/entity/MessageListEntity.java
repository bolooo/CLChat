package org.caiqizhao.entity;

import android.support.annotation.NonNull;

import org.caiqizhao.util.CompareTime;


import java.util.List;


public class MessageListEntity implements Comparable<MessageListEntity> {
    private UserFriend friend;
    private List<Message> messageList;




    public MessageListEntity(UserFriend friend, List<Message> messageList) {
        this.friend = friend;
        this.messageList = messageList;
    }

    public UserFriend getFriend() {
        return friend;
    }

    public void setFriend(UserFriend friend) {
        this.friend = friend;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int compareTo(@NonNull MessageListEntity o) {

        return CompareTime.compare_time(this.messageList.get(this.messageList.size()-1).getTime(),
                o.messageList.get(o.messageList.size()-1).getTime());
    }
}
