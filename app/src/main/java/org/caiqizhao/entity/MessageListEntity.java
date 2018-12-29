package org.caiqizhao.entity;

import java.util.ArrayList;
import java.util.List;

public class MessageListEntity {
    private UserFriend friend;
    private List<Message> messageList;

    public MessageListEntity(UserFriend friend, List<Message> messageList) {  //存放用户朋友id及与其的聊天消息
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
}
