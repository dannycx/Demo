package com.danny.socket;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Socket套接字
 *      流式套接字
 *      用户数据报套接字
 *   TCP:面向连接,提供稳定双向通信功能,三次握手完成,为完成稳定数据传输功能,提供超时重传机制
 *   UDP:无连接,提供不稳定单向通信功能,也可实现双向通信
 */
public class TcpClientActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "TcpClientActivity";
    private static final int MESSAGE_RECEIVER_NEW_MSG = 0;
    private static final int MESSAGE_SOCKET_CONNECTED = 1;

    private Socket mClientSocket;
    private PrintWriter mPrintWriter;

    private Button mSendButton;
    private TextView mMessageTv;
    private EditText mMessageEt;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_RECEIVER_NEW_MSG:
                    mMessageTv.setText(mMessageTv.getText()+(String)msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mSendButton.setEnabled(true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);

        mMessageTv=findViewById(R.id.tcp_client_show_result);
        mMessageEt=findViewById(R.id.tcp_client_msg);
        mSendButton=findViewById(R.id.tcp_client_send);
        mSendButton.setOnClickListener(this);

        Intent service = new Intent(this,TCPServerService.class);
        startService(service);

        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    private void connectTCPServer() {
        Socket socket=null;
        while(socket==null){//采用超时重连
            try {
                socket=new Socket("localhost",8688);
                mClientSocket=socket;
                mPrintWriter=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.d(TAG, "server connect success");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                Log.d(TAG, "connect fail, retry...");
                e.printStackTrace();
            }
        }
        try {//读取服务器端消息
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TcpClientActivity.this.isFinishing()){//当前activity没有被销毁
                String msg = reader.readLine();
                Log.d(TAG, "receive:"+msg);
                if (msg!=null){
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showMsg = "server "+time+":"+msg+"\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVER_NEW_MSG,showMsg).sendToTarget();
                }
            }
            Log.d(TAG, "quit...");
            IOUtils.close(mPrintWriter);
            IOUtils.close(reader);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tcp_client_send:
                final String msg = mMessageEt.getText().toString();
                if (!TextUtils.isEmpty(msg) && mPrintWriter != null){
                    mPrintWriter.println(msg);
                    mMessageEt.setText("");
                    String time = formatDateTime(System.currentTimeMillis());
                    final String showMsg = "self "+time+":"+msg+"\n";
                    mMessageTv.setText(mMessageTv.getText()+showMsg);
                }
                break;
        }
    }

    private String formatDateTime(long l) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(l));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClientSocket!=null){//结束关闭
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
