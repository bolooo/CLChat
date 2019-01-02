package org.caiqizhao.entity;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserFriend implements Comparable<String>{
    private String friend_id = "0";     //好友账户
    private String friend_name;   //好友昵称
    private int friend_photo_src;


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

    public int getFriend_photo_src() {
        return friend_photo_src;
    }

    public void setFriend_photo_src(int friend_photo_src) {
        this.friend_photo_src = friend_photo_src;
    }

    @Override
    public int compareTo(@NonNull String o) {
        if(this.getFriend_id().equals(o))
            return 0;
        else
            return -1;
    }


}
