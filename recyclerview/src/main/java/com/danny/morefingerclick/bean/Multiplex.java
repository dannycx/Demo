package com.danny.morefingerclick.bean;

import java.util.Map;

/**
 * 复用条目封装bean
 * Created by danny on 5/15/18.
 */

public class Multiplex {
    public String name;
    public int price;
    public Map<Integer,Integer> flag;//标志该位置减按钮和数量textview是否显示

    @Override
    public String toString() {
        return "Multiplex{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", flag=" + flag +
                '}';
    }
}
