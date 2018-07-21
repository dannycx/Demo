package com.danny.imageview.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 本地缓存图片
 * Created by danny on 6/9/18.
 */

public class LocalCacheBitmap {
    private static final String LOCAL_Path = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"danny";

    /**
     * 设置缓存
     * @param url 文件名
     * @param bitmap bitmap对象
     */
    public void setCache(String url, Bitmap bitmap){
        File dir = new File(LOCAL_Path);
        if (!dir.exists() || !dir.isDirectory()){
            dir.mkdirs();
        }
        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(dir,fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取本地缓存数据
     * @param url 文件名
     * @return bitmap对象
     */
    public Bitmap getCache(String url){
        try {
            File file = new File(LOCAL_Path,MD5Encoder.encode(url));
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
