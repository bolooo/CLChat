package org.caiqizhao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bolo.chat.R;
import com.google.gson.Gson;

import org.caiqizhao.adapter.MessageListAdepter;
import org.caiqizhao.adapter.MsgAdapter;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.User;
import org.caiqizhao.entity.UserFriend;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.service.UpdateMessageState;
import org.caiqizhao.service.addMessageService;
import org.caiqizhao.util.Base64Code;
import org.caiqizhao.util.VariableUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
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
    private   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Toolbar toolbar;
    public static Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatview);
        handler = new MessageUtil();
        msgList = Message.messageHasMap.get(friend.getFriend_id());
        if(msgList == null)
            msgList = new ArrayList<Message>();
        getFriendIP();
        setChatView();
        initMessageState();
        initToolbar();
    }

    /**
     * 更新消息的状态（已读或未读的状态）
     */
    private void initMessageState() {
        for(Message message:msgList){
            if (message.getMessage_state()==0) {
                message.setMessage_state(1);
                MessageListAdepter.count--;
            }
        }
        Intent updateMessageState = new Intent(ChatView.this, UpdateMessageState.class);
        updateMessageState.putExtra("friend_id",friend.getFriend_id());
        startService(updateMessageState);
        if(MessageListAdepter.count==0){
            android.os.Message m = new android.os.Message();
            m.what = 0x001;
            Main.handler.sendMessage(m);
        }
    }

    /**
     * 获取当前好友的状态（离线或在线）
     */
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
                    final String str = response.body().string();

                    if (!str.equals("0")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                friend_ip = str;
                                send.setOnClickListener(new sendIPOnClick());
                            }
                        });

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

    /**
     * 初始化toolbar
     */
    public void initToolbar(){
        toolbar = findViewById(R.id.toolbar_chatview);

        /*返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!msgList.isEmpty()&&Message.messageHasMap.get(friend.getFriend_id())==null){
                    Message.messageHasMap.put(friend.getFriend_id(),msgList);
                }
                friend = null;
                msgList = null;
                friend_ip = "";
                android.os.Message message = new android.os.Message();
                Chats.handler.sendMessage(message);
                Intent intent = new Intent(ChatView.this,Main.class);
                startActivity(intent);
                ChatView.this.finish();
            }
        });

        toolbar.setTitle(friend.getFriend_name());
    }

    //好友不在线，离线消息发送
    private class sendNotIPOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String content = inputText.getText().toString();
            if(!"".equals(content)){
                Message msg = new Message();
                msg.setMessage_state(1);
                msg.setUser_id(User.user.getUser_id());
                msg.setFriend_id(friend.getFriend_id());
                /*时间格式：xxxx年xx月xx日 HHMM*/
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


    //好友在线，在线消息发送
    private class sendIPOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String content = inputText.getText().toString();
            if (!"".equals(content)) {
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
                Intent intent = new Intent(ChatView.this, addMessageService.class);
                final Message mag = new Message();
                mag.setMessage(Base64Code.encode(msg.getMessage()));
                mag.setMessage_state(msg.getMessage_state());
                mag.setUser_id(msg.getUser_id());
                mag.setPut_id(msg.getPut_id());
                mag.setTime(msg.getTime());
                mag.setFriend_id(msg.getFriend_id());
                mag.setMessage_no(msg.getMessage_no());
                String json = new Gson().toJson(msg);
                intent.putExtra("json", json);
                startService(intent);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //建立一个套接字向地址为friend_ip，端口为9000的服务器（就是刚刚你那个监听9000端口的服务器）发请求
                            Socket socket = new Socket(friend_ip,9000);
                            //得到套接字的输出流，这个输出流可以向服务器写东西，对应的服务器的输入流可以读你在这边写的东西
                            OutputStream out = socket.getOutputStream();
                            out.write(new Gson().toJson(mag).getBytes());
                            out.flush();//这个是防止套接字连接缓冲区中有数据没传过去
                            out.close();//记得用完之后要关闭套接字的输出流
                            socket.close();//关闭套接字
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }



    private class MessageUtil extends Handler{
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String str = data.getString("message");
            Message masssage = new Gson().fromJson(str,Message.class);
            if (msg.what == 0x001) {
                msgList.add(masssage);
                adapter.notifyItemInserted(msgList.size() - 1);
                msgRecyclerView.scrollToPosition(msgList.size() - 1);
                Intent updateMessageState = new Intent(ChatView.this, UpdateMessageState.class);
                updateMessageState.putExtra("friend_id", friend.getFriend_id());
                startService(updateMessageState);
            }
        }
    }



    //销毁活动解除绑定
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
