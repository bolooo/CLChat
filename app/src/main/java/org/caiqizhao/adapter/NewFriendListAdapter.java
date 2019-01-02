package org.caiqizhao.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.UserFriend;

import java.util.List;

public class NewFriendListAdapter extends RecyclerView.Adapter<NewFriendListAdapter.ViewHolder> {

    private List<UserFriend> friend_add_user;

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View view){
            super(view);

        }
    }

    public NewFriendListAdapter(){this.friend_add_user = UserFriend.friend_add_user;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_friend_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewFriendListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return friend_add_user.size();
    }
}
