package com.danny.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class DownloadService extends Service {
    private DownloadAsyncTask mAsyncTask;
    private String mDownloadUrl;

    private DownloadListener mListener=new DownloadListener() {
        @Override
        public void onSuccess() {
            mAsyncTask=null;
            //下载成功将前台服务通知关闭，并创建一个成功通知
            stopForeground(true);
            getNotificationManager().notify(0000,getNotification("Download Success",-1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            mAsyncTask=null;
            //下载失败将前台服务通知关闭，并创建一个失败通知
            stopForeground(true);
            getNotificationManager().notify(0000,getNotification("Download Failed",-1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(0000,getNotification("Downloading...",progress));
            Log.d("onReceive", "onProgress: ");
            // 发送进度广播给Activity
            Intent mIntent = new Intent();//发广播，显示进度
            mIntent.setAction("com.danny.receiver");
            mIntent.putExtra("progress", progress);
            sendBroadcast(mIntent);
        }

        @Override
        public void onPaused() {
            mAsyncTask=null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            mAsyncTask=null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload(String url){
            if (mAsyncTask==null){
                mDownloadUrl=url;
                mAsyncTask=new DownloadAsyncTask(mListener);
                mAsyncTask.execute(mDownloadUrl);
                startForeground(1,getNotification("Downloading...",0));
                Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        }

        public void pauseDownload(){
            if (mAsyncTask!=null){
                mAsyncTask.pauseDownload();
            }
        }

        public void cancelDownload(){
            if (mAsyncTask!=null){
                mAsyncTask.cancelDownload();
            }else {
                if (mDownloadUrl!=null){
                    //取消下载时需将文件删除，并关闭通知
                    String fileName=mDownloadUrl.substring(mDownloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory,fileName);
                    if (file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "Cancel", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * 发通知
     * @param msg
     */
    private Notification getNotification(String msg,int progress) {
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        builder.setContentIntent(pi);
        builder.setContentTitle(msg);
        if (progress>0){//显示下载进度
            builder.setContentText(progress+"%");
            builder.setProgress(100,progress,false);//是否使用模糊进度条
        }
        return builder.build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 获取通知管理者对象
     */
    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
