package com.danny.databinding.attr;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.danny.databinding.R;

/**
 * 非静态自定义属性
 *      自定义类中添加非static方法，用于添加自定义属性
 *
 * 步骤:
 *      1.在自定义类中对添加自定义属性的非static方法，添加@BindingAdapter注解
 *      2.创建自定义组件类，实现DataBindingComponent。这时会自动报错，提示需要实现获取1中类的方法（静态的就不需要了）
 *      3.那就实现此方法把组件对象设置给DataBinding
 *
 * Created by danny on 18-7-21.
 */

public class NoStaticAttrUtil {

    @BindingAdapter("noStaticImage")
    public void setImage(ImageView view, String url){
        if (url == null){
            view.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(view.getContext()).load(url).into(view);
        }
    }
}
