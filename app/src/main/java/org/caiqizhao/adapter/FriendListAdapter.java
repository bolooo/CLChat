package org.caiqizhao.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bolo.chat.R;

import org.caiqizhao.activity.ChatView;
import org.caiqizhao.entity.UserFriend;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<UserFriend> FriendList;
    private int mPostion = -1;

    public FriendListAdapter() {
        FriendList = UserFriend.userFriendList;
    }

    public int getmPostion() {
        return mPostion;
    }

    public void setmPostion(int mPostion) {
        this.mPostion = mPostion;
    }

    public void removeItem(int position) {
        FriendList.remove(position);
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

        holder.friendView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setmPostion(holder.getAdapterPosition());
               // mPostion = getmPostion();
                return false;
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

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private View friendView;
        private CircleImageView friend_photo;
        private TextView friend_name;

        public ViewHolder(View itemView) {
            super(itemView);
            friendView = itemView;
            friend_photo = itemView.findViewById(R.id.friendlist_photo);
            friend_name = itemView.findViewById(R.id.friendlist_name);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        }
    }
}
