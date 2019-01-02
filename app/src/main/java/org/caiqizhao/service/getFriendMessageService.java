package org.caiqizhao.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.Gson;

import org.caiqizhao.activity.ChatView;
import org.caiqizhao.entity.Message;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class getFriendMessageService extends Service {
    private  LocalBinder mBinder;

    public class LocalBinder extends Binder {
        public getFriendMessageService getService() {
            return getFriendMessageService.this;
        }
    }


    public getFriendMessageService() {
        mBinder = new LocalBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    public void PortListener(){
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true){
                Socket socket = serverSocket.accept();
                new Thread(new getFriendMessageRun(socket)).start();
            }
        }catch (Exception e){
        }
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
                String str = baos.toByteArray().toString();
                Message mag = new Gson().fromJson(str,Message.class);
                if(ChatView.friend.getFriend_id() == mag.getFriend_id()){

                }else {
                    android.os.Message message = new android.os.Message();
                    Bundle data = new Bundle();
                    data.putString("message", str);
                    message.setData(data);
                    ChatView.handler.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
