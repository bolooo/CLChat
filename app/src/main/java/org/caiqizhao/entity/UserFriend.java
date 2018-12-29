package org.caiqizhao.entity;

import java.util.ArrayList;
import java.util.List;

public class UserFriend {
    private String friend_id;
    private String friend_name;

    public static List<UserFriend> userFriendList = new ArrayList<UserFriend>();

    public static List<UserFriend> uer_add_friend = new ArrayList<UserFriend>();

    public static List<UserFriend> friend_add_user = new ArrayList<UserFriend>();

    @Override
    public String toString() {
        return "UserFriend{" +
                "friend_id='" + friend_id + '\'' +
                ", friend_name='" + friend_name + '\'' +
                '}';
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }
}
