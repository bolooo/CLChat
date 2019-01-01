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
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.service.addMessageService;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatView extends AppCompatActivity {


    private List<Message> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    public static UserFriend friend = null;
    private String friend_ip;
    public static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
        msgList = Message.messageHasMap.get(friend.getFriend_id());
        getFriendIP();
        setChatView();
    }

    private void getFriendIP() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("friend_id",friend.getFriend_id())
                        .build();
                Request request = new Request.Builder()
                        .url(VariableUtil.Service_IP +"getip")
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    String str = response.body().string();

                    if (!str.equals("0")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                        friend_ip = str;
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                send.setOnClickListener(new sendNotIPOnClick());
                            }
                        });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    }

    private class sendNotIPOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String content = inputText.getText().toString();
            if(!"".equals(content)){
                Message msg = new Message();
                msg.setMessage_state(1);
                msg.setUser_id(User.user.getUser_id());
                msg.setFriend_id(friend.getFriend_id());
                msg.setTime(simpleDateFormat.format(new Date()));
                msg.setMessage(content);
                msg.setPut_id(1);
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                inputText.setText("");
                Intent intent = new Intent(ChatView.this,addMessageService.class);
                intent.putExtra("json",new Gson().toJson(msg));
                startService(intent);
            }
        }
    }
}
