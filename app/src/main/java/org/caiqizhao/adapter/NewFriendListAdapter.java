package org.caiqizhao.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bolo.chat.R;

import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.service.AddContacksService;

import java.util.List;

public class NewFriendListAdapter extends RecyclerView.Adapter<NewFriendListAdapter.ViewHolder> {

    private List<UserFriend> friend_add_user;

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView new_friend_name;
        private Button agree;
        private Button disagree;


        public ViewHolder(View view){
            super(view);
            new_friend_name = view.findViewById(R.id.new_friend_nameT);
            agree = view.findViewById(R.id.new_friend_agreeB);
            disagree = view.findViewById(R.id.new_friend_disagreeB);
        }
    }

    public NewFriendListAdapter(){this.friend_add_user = UserFriend.friend_add_user;}

    @NonNull
    @Override
    public NewFriendListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_friend_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewFriendListAdapter.ViewHolder holder, final int position) {
        final UserFriend friend = friend_add_user.get(position);
        holder.new_friend_name.setText(friend.getFriend_name());

        holder.agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agreeService = new Intent(view.getContext(), AddContacksService.class);
                agreeService.putExtra("friend_id", friend.getFriend_id());
                agreeService.putExtra("user_id", User.user.getUser_id());
                agreeService.putExtra("code", "2");
                view.getContext().startService(agreeService);
                holder.agree.setText("已添加");
                holder.agree.setEnabled(false);
                holder.disagree.setEnabled(false);
                UserFriend.friend_add_user.remove(position);
            }
        });

        holder.disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent disagreeService = new Intent(view.getContext(), AddContacksService.class);
                disagreeService.putExtra("friend_id", friend.getFriend_id());
                disagreeService.putExtra("user_id", User.user.getUser_id());
                disagreeService.putExtra("code", "0");
                view.getContext().startService(disagreeService);
                UserFriend.friend_add_user.remove(position);
                NewFriendListAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friend_add_user.size();
    }
}
