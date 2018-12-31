package org.caiqizhao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bolo.chat.R;
import com.google.gson.Gson;

import org.caiqizhao.chatview.MsgAdapter;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.MessageListEntity;

import java.util.ArrayList;
import java.util.List;

public class ChatView extends AppCompatActivity {


    private List<Message> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private String friend_id;

    /**
     * 初始化消息数据
     */
//    public void initMsgs(){
//        Msg msg1 = new Msg("hello guy.", Msg.TYPE_RECEIVED);
//        msgList.add(msg1);
//        Msg msg2 = new Msg("hello guy.", Msg.TYPE_SENT);
//        msgList.add(msg2);
//        Msg msg3 = new Msg("yo.", Msg.TYPE_RECEIVED);
//        msgList.add(msg3);
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatview);
        Intent data = getIntent();
        MessageListEntity messageListEntity = new Gson().fromJson(data.getStringExtra("chat"),MessageListEntity.class);
        msgList = messageListEntity.getMessageList();
        setChatView();
    }

    /**
     * 初始化聊天界面
     */
    public void setChatView(){
        inputText = findViewById(R.id.input_text);
        send = findViewById(R.id.send);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if(!"".equals(content)){
                    Message msg = new Message();
                    msg.setMessage(content);
                    msg.setPut_id(1);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });
    }

}
