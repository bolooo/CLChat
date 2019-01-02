package org.caiqizhao.adapter;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bolo.chat.R;

import org.caiqizhao.activity.ChatView;
import org.caiqizhao.activity.Main;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.MessageListEntity;

import java.util.List;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MessageListAdepter extends RecyclerView.Adapter<MessageListAdepter.ViewHolder> {
    private List<MessageListEntity> messageListEntityList;
    public static int count = 0;

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
        List<Message> messageList = messageListEntity.getMessageList();
        int sum = 0;
        for (Message message :messageList){
            if(message.getMessage_state() == 0){
                sum++;
                count = count + sum;
            }
        }
        if (sum!=0){
            holder.badgeView.bindTarget(holder.friend_tupian);
            holder.badgeView.setBadgeNumber(sum);
            holder.badgeView.setBadgeTextColor(Color.RED);
            holder.badgeView.setBadgeTextColor(Color.WHITE);
            holder.badgeView.setBadgeGravity(Gravity.END|Gravity.TOP);
            android.os.Message m = new android.os.Message();
            m.what = 0x001;
            Main.handler.sendMessage(m);
        }
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
        private View chatView;
        private ImageView friend_tupian;
        private TextView friend_name;
        private TextView message_time;
        private TextView friend_message;
        private QBadgeView badgeView;



        public ViewHolder(View itemView) {
            super(itemView);
            chatView = itemView;
            friend_message = itemView.findViewById(R.id.friend_message);
            message_time = itemView.findViewById(R.id.message_time);
            friend_name = itemView.findViewById(R.id.firend_name);
            friend_tupian = itemView.findViewById(R.id.friend_tupiao);
            badgeView = new QBadgeView(chatView.getContext());

        }
    }
}
