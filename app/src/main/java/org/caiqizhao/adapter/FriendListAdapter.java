package org.caiqizhao.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.UserFriend;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<UserFriend> FriendList;


    public FriendListAdapter() {
        FriendList = UserFriend.userFriendList;
    }

    @NonNull
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item,parent,false);
        return new ViewHolder(view);
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

        public CircleImageView friend_photo;
        public TextView friend_name;

        public ViewHolder(View itemView) {
            super(itemView);
            friend_photo = itemView.findViewById(R.id.friendlist_photo);
            friend_name = itemView.findViewById(R.id.friendlist_name);
        }
    }
}
