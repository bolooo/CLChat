package org.caiqizhao.adapter;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bolo.chat.R;
import com.google.gson.Gson;

import org.caiqizhao.activity.ChatView;
import org.caiqizhao.chatview.Msg;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.MessageListEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdepter extends RecyclerView.Adapter<MessageListAdepter.ViewHolder> {
    private List<MessageListEntity> messageListEntityList;

    public MessageListAdepter(List<MessageListEntity> list){
        this.messageListEntityList = list;
    }

    @NonNull
    @Override
    public MessageListAdepter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.chatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageListEntity messageListEntity = messageListEntityList.get(holder.getAdapterPosition());
                Intent intent = new Intent(parent.getContext(),ChatView.class);
//                intent.putExtra("chat",new Gson().toJson(messageListEntity));
                ChatView.friend = messageListEntity.getFriend();
                parent.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdepter.ViewHolder holder, int position) {
        final MessageListEntity messageListEntity = messageListEntityList.get(position);
//        holder.friend_tupian.setImageResource(fileCloud.getFile_tupian());
        holder.friend_name.setText(messageListEntity.getFriend().getFriend_name());
        int index = messageListEntity.getMessageList().size()-1;
        System.out.println(messageListEntity.getMessageList());
        holder.friend_message.setText(messageListEntity.getMessageList()
                .get(index).getMessage());
        String time = messageListEntity.getMessageList()
                .get(index).getTime();
        holder.message_time.setText(time.split(" ")[time.split(" ").length-1]);
    }

    @Override
    public int getItemCount() {
        return messageListEntityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        View chatView;
        public ImageView friend_tupian;
        public TextView friend_name;
        public TextView message_time;
        public TextView friend_message;


        public ViewHolder(View itemView) {
            super(itemView);
            chatView = itemView;
            friend_message = itemView.findViewById(R.id.friend_message);
            message_time = itemView.findViewById(R.id.message_time);
            friend_name = itemView.findViewById(R.id.firend_name);
            friend_tupian = itemView.findViewById(R.id.friend_tupiao);

        }
    }
}
