package org.caiqizhao.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bolo.chat.R;

import org.caiqizhao.adapter.FriendListAdapter;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.service.DeleteFriendIntentService;
import org.caiqizhao.util.ToastUtil;
import org.caiqizhao.util.VariableUtil;

import q.rorbin.badgeview.QBadgeView;

public class Contacks extends Fragment {
    private View view;
    public  Context context;
    private LinearLayout newfriendlayout;
    private QBadgeView badgeView;
    public static Handler handler ;
    RecyclerView friendlist;
    FriendListAdapter friendListAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        badgeView = new QBadgeView(view.getContext());
        initAdapter();
        if(VariableUtil.frien_add_user!=0){
            badgeView.bindTarget(view.findViewById(R.id.friendlist_photo));
            badgeView.setBadgeNumber(VariableUtil.frien_add_user);
            badgeView.setBadgeTextColor(Color.RED);
            badgeView.setBadgeTextColor(Color.WHITE);
            badgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
        }
        handler = new MessageUtil();

        registerForContextMenu(friendlist);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.context_menu_0:
                    if (friendListAdapter.getmPostion() != -1) {
                        System.out.println("kaishi");
                        Intent deleteFriend = new Intent(getActivity(), DeleteFriendIntentService.class);
                        deleteFriend.putExtra("user_id", User.user.getUser_id());
                        deleteFriend.putExtra("friend_id", UserFriend.userFriendList.get(friendListAdapter.getmPostion()).getFriend_id());
                        context.startService(deleteFriend);
                        if (org.caiqizhao.entity.Message.messageHasMap.get(UserFriend.userFriendList.get(friendListAdapter.getmPostion()).getFriend_id()) != null) {
                            org.caiqizhao.entity.Message.messageHasMap.remove(UserFriend.userFriendList.get(friendListAdapter.getmPostion()).getFriend_id());
                        }
                        int i = -1;
                        for (int j = 0; j < UserFriend.userFriendList.size(); j++) {
                            if (UserFriend.userFriendList.get(j).getFriend_id()
                                    .equals(UserFriend.userFriendList.get(friendListAdapter.getmPostion()).getFriend_id())) {
                                i = j;
                                break;
                            }
                        }
                        if (i != -1)
                            UserFriend.userFriendList.remove(i);

                        android.os.Message message = new android.os.Message();
                        Chats.handler.sendMessage(message);
                        android.os.Message message1 = new android.os.Message();
                        Contacks.handler.sendMessage(message1);
                    }
                    break;
                default:
                    //do nothing
            }
        return super.onContextItemSelected(item);
    }


    private void initAdapter() {

        /*初始化通讯录页面的list内容*/
        friendlist = view.findViewById(R.id.friend_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        friendlist.setLayoutManager(linearLayoutManager);
        friendlist.findViewById(R.id.friendlist_photo);
        friendListAdapter = new FriendListAdapter();
        friendlist.setAdapter(friendListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacks, container, false);
        Log.v("fragment", "friendlist");
        return view;
    }


    class MessageUtil extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            badgeView.bindTarget(view.findViewById(R.id.friendlist_photo));
            badgeView.setBadgeNumber(VariableUtil.frien_add_user);
            badgeView.setBadgeTextColor(Color.RED);
            badgeView.setBadgeTextColor(Color.WHITE);
            badgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
            initAdapter();
        }
    }



}
