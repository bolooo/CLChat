package org.caiqizhao.entity;

import java.util.ArrayList;
import java.util.List;

public class MessageListEntity {
    private UserFriend friend;
    private List<Message> messageList;

    public static List<MessageListEntity> messageListEntities = new ArrayList<MessageListEntity>();


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
}
