package org.caiqizhao.entity;

public class User {

    private Integer no;
    private String user_id;
    private String user_name;
    private String user_password;
    private Integer user_state;
    private String user_sex;
    private String user_introduce;

    public static User user = null;

    public String getUser_id() {
        return user_id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_introduce() {
        return user_introduce;
    }

    public void setUser_introduce(String user_introduce) {
        this.user_introduce = user_introduce;
    }




    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Integer getUser_state() {
        return user_state;
    }

    public void setUser_state(Integer user_state) {
        this.user_state = user_state;
    }

    @Override
    public String toString() {
        return "User{" +
                "no=" + no +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_state=" + user_state +
                ", user_sex='" + user_sex + '\'' +
                ", user_introduce='" + user_introduce + '\'' +
                '}';
    }
}
