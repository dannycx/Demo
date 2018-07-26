package com.danny.service;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by danny on 18-7-6.
 */

public class DownloadAsyncTask extends AsyncTask<String, Integer, Integer> {
    private static final int TYPE_SUCCESS = 0;
    private static final int TYPE_FAILED = 1;
    private static final int TYPE_PAUSED = 2;
    private static final int TYPE_CANCEL = 3;

    private DownloadListener mDownloadListener;//下载监听器
    private boolean mIsCancel = false;
    private boolean mIsPause = false;
    private int mLastProgress;//上次进度

    /**
     * 构造器设置监听器
     *
     * @param downloadListener 监听器对象
     */
    public DownloadAsyncTask(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    /**
     * 用于后台下载
     *
     * @param strings
     * @return 传递给onPostExecute()
     */
    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;//输入流
        RandomAccessFile saveFile = null;//随机访问
        File file = null;
        try {
            long downloadedFileLength = 0;//记录已下载文件长度
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            //sd卡download目录
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory, fileName);
            if (file.exists()) {//存在，读取已下载字节数
                downloadedFileLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);//获取下载内容长度
            if (contentLength == 0) {
                return TYPE_FAILED;
            } else if (contentLength == downloadedFileLength) {
                return TYPE_SUCCESS;//已下载字节和文件总字节数相等，下载成功
            }
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //断点下载,指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadedFileLength + "-").url(downloadUrl).build();
            Response response = client.newCall(request).execute();
            if (response!=null){
                is=response.body().byteStream();
                saveFile=new RandomAccessFile(file,"rw");
                saveFile.seek(downloadedFileLength);//跳过已下载字节
                byte[] bys = new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(bys))!=-1){
                    if (mIsCancel){
                        return TYPE_CANCEL;
                    }else if (mIsPause){
                        return TYPE_PAUSED;
                    }else {
                        total+=len;
                        saveFile.write(bys,0,len);
                        //计算下载百分比
                        int progress= (int) ((total+downloadedFileLength)*100/contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null){
                    is.close();
                }
                if (saveFile!=null){
                    saveFile.close();
                }
                if (mIsCancel && file!=null){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    /**
     * 获取下载内容长度
     *
     * @param downloadUrl 下载地址
     * @return 下载内容长度
     */
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if (response!=null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    /**
     * 用于后台下载结束后，回调
     *
     * @param integer doInBackground()的返回值
     */
    @Override
    protected void onPostExecute(Integer integer) {
        if(mDownloadListener!=null) {
            switch (integer) {
                case TYPE_SUCCESS:
                    mDownloadListener.onSuccess();
                    break;
                case TYPE_FAILED:
                    mDownloadListener.onFailed();
                    break;
                case TYPE_CANCEL:
                    mDownloadListener.onCancel();
                    break;
                case TYPE_PAUSED:
                    mDownloadListener.onPaused();
                    break;
                default:
                    break;
            }
        }
        super.onPostExecute(integer);
    }

    /**
     * 暂停下载
     */
    public void pauseDownload(){
        mIsPause=true;
    }

    /**
     * 取消下载
     */
    public void cancelDownload(){
        mIsCancel=true;
    }

    /**
     * 更新进度
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress>mLastProgress){
            if (mDownloadListener!=null) {
                mDownloadListener.onProgress(progress);
            }
            mLastProgress=progress;
        }
    }
}
