package com.danny.imageview.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络获取图片
 * Created by danny on 6/9/18.
 */

public class NetBitmapUtils {
    private LocalCacheBitmap mLocalCacheBitmap;
    private MemoryCacheBitmap mMemoryCacheBitmap;

    public NetBitmapUtils(LocalCacheBitmap localCacheBitmap,MemoryCacheBitmap memoryCacheBitmap) {
        mLocalCacheBitmap = localCacheBitmap;
        mMemoryCacheBitmap = memoryCacheBitmap;
    }

    public void obtainBitmap(ImageView iv, String url) {
        new NetAsyncTask().execute(iv,url);
    }

    class NetAsyncTask extends AsyncTask<Object, Integer, Bitmap> {
        private ImageView mImageView;
        private String mUrl;

        /**
         * 运行在主线程,可进行预备工作
         */
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        /**
         * 主要方法
         * 主要方法运行在子线程中,可直接进行网络请求
         * 给ImageView添加标志,和url一一对应,防止listView中重用时,数据错乱问题
         * @param objects 参数来自execute中的参数,参数类型对应第一个泛型,返回值类型对应最后一个泛型,返回值直接传递给onPostExecute
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object... objects) {
            mImageView = (ImageView) objects[0];
            mUrl = (String) objects[1];

            mImageView.setTag(mUrl);

            Bitmap bitmap = download(mUrl);
//            publishProgress(1);//调用该方法会直接回调onProgressUpdate,可以在其中更新进度条的进度
            return bitmap;
        }

        /**
         * 更新进度,运行在主线程中
         * @param values 进度值
         */
        @Override
        protected void onProgressUpdate(Integer... values) {super.onProgressUpdate(values);}

        /**
         * 主要方法
         * 运行在主行程中,参数来源于doInBackground,可以进行ui更新,在doInBackground中进行耗时操作,得到的结果传递过来
         * 先取出ImageView中存的标志,和当前传过来的是一个的话则设置,否则不处理
         * @param bitmap doInBackground返回值
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap!=null){
                String url = (String) mImageView.getTag();
                if (url.equals(mUrl)){
                    Log.d("NetBitmapUtils", "onPostExecute: 网络获取数据");
                    mImageView.setImageBitmap(bitmap);

                    //本地缓存
                    mLocalCacheBitmap.setCache(url,bitmap);

                    //内存缓存
                    mMemoryCacheBitmap.setCache(url,bitmap);
                }
            }
        }
    }

    /**
     * 下载网络图片
     * @param url 网络链接
     * @return bitmap对象
     */
    private Bitmap download(String url) {
        HttpURLConnection conn=null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200){
                InputStream is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (conn!=null){
                conn.disconnect();
            }
        }
        return null;
    }
}
