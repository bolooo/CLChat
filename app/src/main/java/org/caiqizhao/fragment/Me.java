package org.caiqizhao.fragment;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bolo.chat.MainActivity;
import com.example.bolo.chat.R;

import org.caiqizhao.activity.CLChatVersionActivity;
import org.caiqizhao.activity.UpdateUserDataActivity;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.service.LogoutService;

import static android.app.Activity.RESULT_OK;

public class Me extends Fragment {

    private TextView user_name; //用户名称
    private TextView user_id;   //账户
    private LinearLayout update_information; //用户信息更新
    private LinearLayout help;   //版本信息
    private LinearLayout user_return_login;  //退出账户
    private View view;

    Intent intent; //用于跳转获得

    private UpdataUserNameFilter updataUserNameFilter; //广播监听器

    public  Context context;  //记录上下文环境

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {

        //设置用户昵称
        user_name = view.findViewById(R.id.user_name);
        user_name.setText(User.user.getUser_name());


        //设置用户账号
        user_id = view.findViewById(R.id.user_id);
        user_id.setText(User.user.getUser_id());

        //更新用户信息的响应事件
        update_information = view.findViewById(R.id.update_information);
        update_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),UpdateUserDataActivity.class);
                startActivityForResult(intent,1);
            }
        });

        //退出登陆响应事件
        user_return_login = view.findViewById(R.id.user_return_login);
        user_return_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除现存的用户信息
                User.user = null;              //用户个人资料设置为空
                Message.messageHasMap.clear();  //清楚存储的用户消息

                //清空所有好友列表以及好友请求
                UserFriend.userFriendList.clear();
                UserFriend.friend_add_user.clear();
                UserFriend.uer_add_friend.clear();

                //通知服务器退出账户
                intent = new Intent(getActivity(),LogoutService.class);
                getActivity().startService(intent);

                //返回登陆界面
                intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //查看程序版本相关信息信息
        help = view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(),CLChatVersionActivity.class);
                startActivityForResult(intent,2);
            }
        });


        //注册广播接收
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.mycloud.UPDATA_USER");
        updataUserNameFilter = new UpdataUserNameFilter();
        localBroadcastManager.registerReceiver(updataUserNameFilter,intentFilter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me, container, false);
        return view;
    }


    /**
     * 监听广播用户名称的变化，并将其显示在相应的TextView中
     */
    public class UpdataUserNameFilter extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            user_name.setText(intent.getStringExtra("user_name"));
        }
    }

}
