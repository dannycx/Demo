package com.danny.imageview.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 内存缓存
 * 缓存在集合中
 *  栈:  存变量,方法声明,引用
 *  堆:  对象
 *
 *  强引用:垃圾回收器不会回收
 *  软引用:垃圾回收期,在内存充足时不回收-SoftReference
 *  弱引用:检测到就回收-WeakReference
 *  虚引用:形同虚设-PhantomReference
 *
 * 优化添加软引用
 *
 * 进一步优化,使用LruCache
 * Created by danny on 6/9/18.
 */

public class MemoryCacheBitmap {
//    private HashMap<String,Bitmap> mCache = new HashMap<>();
    private HashMap<String,SoftReference<Bitmap>> mCache = new HashMap<>();//不建议使用,极不可靠

    private LruCache<String,Bitmap> mLruCache;//google建议用LruCache,内部实现LinkedHashMap

    public MemoryCacheBitmap(){
        //获取分配最大内存数:Runtime.getRuntime().maxMemory()
        int maxSize = (int) (Runtime.getRuntime().maxMemory());
        mLruCache = new LruCache<>(maxSize / 8);//分配八分之一空间用来缓存图片
    }

    /**
     * 设置内存缓存
     * @param url key
     * @param bitmap value
     */
    public void setCache(String url,Bitmap bitmap){
//        SoftReference<Bitmap> soft = new SoftReference<>(bitmap);
//        mCache.put(url,soft);

        mLruCache.put(url,bitmap);
    }

    /**
     * 获取内存缓存
     * @param url key
     * @return bitmap
     */
    public Bitmap getCache(String url){
//        SoftReference<Bitmap> soft = mCache.get(url);
//        if (soft != null) {
//            return soft.get();
//        }
//        return null;
        return mLruCache.get(url);
    }
}
