package com.danny.databinding.attr;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.danny.databinding.R;

/**
 * 静态自定义属性
 *
 * Created by danny on 18-7-20.
 */

public class DataBindingAttrUtils {

    /**
     * 使用DataBinding来加载图片
     * 使用@BindingAdapter注解，注解值（这里的imageUrl）可任取，注解值将成为自定义属性
     * 此自定义属性可在xml布局文件中使用，自定义属性的值就是这里定义String类型url
     * 《说明》：
     * 1. 方法名可与注解名一样，也可不一样
     * 2. 第一个参数必须是View，就是自定义属性所在的View
     * 3. 第二个参数就是自定义属性的值，与注解值对应。这是数组，可多个,这里需要INTERNET权限，别忘了
     *
     * @param imageView     ImageView控件
     * @param url           图片网络地址
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (url == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(imageView.getContext()).load(url).into(imageView);
        }
    }
}
