package org.caiqizhao.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bolo.chat.R;

import org.caiqizhao.activity.ChatView;
import org.caiqizhao.activity.Main;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.fragment.Contacks;
import org.caiqizhao.service.DeleteFriendIntentService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<UserFriend> FriendList;
    Context context;

    public FriendListAdapter(Context context) {
        this.context = context;
        FriendList = UserFriend.userFriendList;
    }

    @NonNull
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.friendView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFriend userFriend = FriendList.get(holder.getAdapterPosition());
                Intent intent = new Intent(parent.getContext(),ChatView.class);
                ChatView.friend = userFriend;
                parent.getContext().startActivity(intent);
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.ViewHolder holder, int position) {
        UserFriend friend = FriendList.get(position);
        holder.friend_name.setText(friend.getFriend_name());
    }

    @Override
    public int getItemCount() {
        return FriendList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View friendView;
        private CircleImageView friend_photo;
        private TextView friend_name;

        public ViewHolder(View itemView) {
            super(itemView);
            friendView = itemView;
            friend_photo = itemView.findViewById(R.id.friendlist_photo);
            friend_name = itemView.findViewById(R.id.friendlist_name);

        }
    }
}
