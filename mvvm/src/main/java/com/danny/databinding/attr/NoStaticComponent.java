package com.danny.databinding.attr;

import android.databinding.DataBindingComponent;

/**
 * 创建自定义组件类，并实现方法
 *
 * Created by danny on 18-7-21.
 */

public class NoStaticComponent implements DataBindingComponent {
    private NoStaticAttrUtil mAttrUtil;

    @Override
    public NoStaticAttrUtil getNoStaticAttrUtil() {
        if (mAttrUtil == null){
            mAttrUtil = new NoStaticAttrUtil();
        }
        return mAttrUtil;
    }
}
