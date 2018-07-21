package com.danny.imageview.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.danny.imageview.R;

/**
 * 图片三级缓存工具类
 * Created by danny on 6/9/18.
 */

public class DcxBitmapUtils {
    private NetBitmapUtils mNetBitmapUtils;
    private LocalCacheBitmap mLocalCacheBitmap;
    private MemoryCacheBitmap mMemoryCacheBitmap;

    public DcxBitmapUtils() {
        mLocalCacheBitmap = new LocalCacheBitmap();
        mMemoryCacheBitmap = new MemoryCacheBitmap();
        mNetBitmapUtils = new NetBitmapUtils(mLocalCacheBitmap, mMemoryCacheBitmap);
    }

    /**
     * 图片三级缓存
     * 先从内存取--sd卡取--网络获取
     * @param iv 设置图片的控件
     * @param url 网络地址
     */
    public void display(ImageView iv, String url){
        iv.setImageResource(R.drawable.girl_1);

        //三级缓存:内存-sd-网络

        Bitmap bitmap = mMemoryCacheBitmap.getCache(url);
        if (bitmap!=null){
            iv.setImageBitmap(bitmap);
            Log.d("DcxBitmapUtils", "display: 内存缓存");
            return;
        }

        bitmap = mLocalCacheBitmap.getCache(url);
        if (bitmap!=null){
            iv.setImageBitmap(bitmap);
            Log.d("DcxBitmapUtils", "display: 本地缓存");
            mMemoryCacheBitmap.setCache(url,bitmap);
            return;
        }

        mNetBitmapUtils.obtainBitmap(iv,url);
    }
}
