package com.danny.service;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 断点下载
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mStart;
    private Button mPause;
    private Button mCancel;
    private SeekBar mSeekBar;
    private TextView mShowProgress;
    private int mCurrentProgress;

    private DownloadService.DownloadBinder mBinder;//下载
    private ServiceReceiver mServiceReceiver;//广播

    private ServiceConnection mConn =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder= (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        Intent intent = new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,mConn,BIND_AUTO_CREATE);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        // 1. 实例化ServiceReceiver & IntentFilter
        mServiceReceiver = new ServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        // 2. 设置接收广播的类型
        intentFilter.addAction("com.danny.receiver");
        // 3. 动态注册：调用Context的registerReceiver（）方法
        registerReceiver(mServiceReceiver, intentFilter);
    }

    private void initView() {
        mStart=findViewById(R.id.start);
        mStart.setOnClickListener(this);

        mPause=findViewById(R.id.pause);
        mPause.setOnClickListener(this);

        mCancel=findViewById(R.id.cancel);
        mCancel.setOnClickListener(this);

        mSeekBar=findViewById(R.id.seek);
        mSeekBar.setMax(100);

        mCurrentProgress = (int) SpUtil.get(this,"progress",0);
        mShowProgress=findViewById(R.id.seek_progress);
        mShowProgress.setText(mCurrentProgress + "%");

        mSeekBar.setProgress(mCurrentProgress);
    }

    @Override
    public void onClick(View v) {
        if (mBinder==null){
            return;
        }
        switch (v.getId()){
            case R.id.start:
//                https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe
//                https://github.com/dannycx/knowledge/blob/master/README.md
                mBinder.startDownload("https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe");
                break;
            case R.id.pause:
                mBinder.pauseDownload();
                break;
            case R.id.cancel:
                mBinder.cancelDownload();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
        unregisterReceiver(mServiceReceiver);
        SpUtil.put(this,"progress",mCurrentProgress);
    }

    public class ServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: ");
            if (intent!=null){
                int progress = intent.getIntExtra("progress",0);
                mSeekBar.setProgress(progress);
                mShowProgress.setText(progress+"%");
                mCurrentProgress=progress;
            }
        }
    }
}
