package com.danny.databinding.attr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityNoStaticAttrBinding;

/**
 * 使用非静态的自定义的属性
 *      方法一:设置默认组件  DataBindingUtil.setDefaultComponent(new NoStaticComponent());
 *      方法二:直接在构造中传递组件  DataBindingUtil.setDefaultComponent(new NoStaticComponent());
 */
public class NoStaticAttrActivity extends AppCompatActivity {
    private ActivityNoStaticAttrBinding mNoStaticAttrBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setDefaultComponent(new NoStaticComponent());
        mNoStaticAttrBinding = DataBindingUtil.setContentView(this, R.layout.activity_no_static_attr, new NoStaticComponent());

        mNoStaticAttrBinding.setImageNet("http://avatar.csdn.net/4/9/8/1_a10615.jpg");
        mNoStaticAttrBinding.setImageNull(null);
    }
}
