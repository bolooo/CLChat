package org.caiqizhao.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;

import org.caiqizhao.activity.ChatView;
import org.caiqizhao.entity.Message;
import org.caiqizhao.entity.User;
import org.caiqizhao.fragment.Chats;
import org.caiqizhao.util.Base64Code;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class getFriendMessgaeIntentService extends IntentService {
    private static ServerSocket serverSocket = null;

    public getFriendMessgaeIntentService() {
        super("getFriendMessgaeIntentService");
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            serverSocket = new ServerSocket(9000);  //新建套接字-服务器端。监听本地IP的9000端口

            //进入死循环，不停的监听，套接字-客户端的请求
            while (true) {
                Socket socket = serverSocket.accept();  //堵塞请求，当有请求到来时返回一个可以和客户端通信的套接字

                new Thread(new getFriendMessageRun(socket)).start();
            }
        } catch (Exception e) { }
    }
    private class getFriendMessageRun implements Runnable{
        private Socket socket;

        public getFriendMessageRun(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                int size;
                byte[] bytes = new byte[1024*4];
                InputStream inputStream = socket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((size = inputStream.read(bytes)) != -1) {
                    baos.write(bytes, 0, size);
                }
                inputStream.close();
                socket.close();
                String str = new String(baos.toByteArray());
                Message mag = new Gson().fromJson(str,Message.class);
                mag.setMessage(Base64Code.decode(mag.getMessage()));
                mag.setPut_id(0);
                String user_id = mag.getFriend_id();
                mag.setFriend_id(mag.getUser_id());
                mag.setUser_id(user_id);
                android.os.Message message = new android.os.Message();
                Bundle data = new Bundle();
                data.putString("message", new Gson().toJson(mag));
                if(ChatView.friend==null){
                    mag.setMessage_state(0);
                    if(Message.messageHasMap.get(mag.getFriend_id()) == null){
                        Message.messageHasMap.put(mag.getFriend_id(),new ArrayList<Message>());
                    }
                    Message.messageHasMap.get(mag.getFriend_id()).add(mag);
                    Chats.handler.sendMessage(message);
                }else {
                    if(ChatView.friend.getFriend_id().equals(mag.getFriend_id())) {
                        mag.setMessage_state(1);
                        message.setData(data);
                        message.what = 0x001;
                        ChatView.handler.sendMessage(message);
                    }else {
                        mag.setMessage_state(0);
                        if(Message.messageHasMap.get(mag.getFriend_id()) == null){
                            Message.messageHasMap.put(mag.getFriend_id(),new ArrayList<Message>());
                        }
                        Message.messageHasMap.get(mag.getFriend_id()).add(mag);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
