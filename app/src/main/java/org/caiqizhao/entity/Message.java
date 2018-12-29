package org.caiqizhao.entity;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;

public class Message{
    private Integer message_no;
    private String user_id;
    private String friend_id;
    private Integer message_state;
    private Integer put_id;
    private String message;
    private String time;

    //key值为朋友id value为与朋友的所有Message信息且朋友为
    public static HashMap<String,List<Message>> messageHasMap = new HashMap<String,List<Message>>();

    @Override
    public String toString() {
        return "Message{" +
                "message_no=" + message_no +
                ", user_id='" + user_id + '\'' +
                ", friend_id='" + friend_id + '\'' +
                ", message_state=" + message_state +
                ", put_id=" + put_id +
                ", message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }



    public Integer getMessage_no() {
        return message_no;
    }

    public void setMessage_no(Integer message_no) {
        this.message_no = message_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public Integer getMessage_state() {
        return message_state;
    }

    public void setMessage_state(Integer message_state) {
        this.message_state = message_state;
    }

    public Integer getPut_id() {
        return put_id;
    }

    public void setPut_id(Integer put_id) {
        this.put_id = put_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
