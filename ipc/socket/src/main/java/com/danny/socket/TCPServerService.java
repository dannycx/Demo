package com.danny.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {
    private static final String TAG = "TCPServerService";
    private boolean mIsServerDestory=false;
    private String[] mDefaultReply = new String[]{
            "你好啊!",
            "今天天气不错!",
            "我可以和多人进行聊天!",
            "给你讲个笑话吧!",
            "好吧,就这样吧!"
    };

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServerDestory=true;
    }

    class TcpServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                //监听本地8688端口
                serverSocket = new ServerSocket(8688);
            } catch (IOException e) {
                Log.d(TAG, "建立连接失败,port:8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServerDestory){
                try {
                    //接收客户端请求
                    final Socket client = serverSocket.accept();
                    Log.d(TAG, "接收客户端请求.");
                    new Thread(){
                        @Override
                        public void run() {
                            try {
                                responseClient(client);//响应客户端
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void responseClient(Socket client) throws IOException{
        //用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
        out.println("欢迎来到聊天室!");
        while (!mIsServerDestory){
            String str = in.readLine();
            Log.d(TAG, "msg from client:"+str);
            if (str==null){
                //客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefaultReply.length);
            String msg = mDefaultReply[i];
            out.println(msg);
            Log.d(TAG, "send:"+msg);
        }
        Log.d(TAG, "client quit.");
        //关闭流
        IOUtils.close(out);
        IOUtils.close(in);
        client.close();
    }
}
