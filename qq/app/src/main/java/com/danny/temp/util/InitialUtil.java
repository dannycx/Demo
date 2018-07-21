package com.danny.temp.util;

import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 首字母获取工具类
 *
 * Created by danny on 18-7-7.
 */

public class InitialUtil {

    /**
     * 获取汉字的拼音，会销毁一定的资源，所以不应该被频繁调用
     *
     * @param chinese 汉字
     * @return 首字母
     */
    public static String getInitial(String chinese){
        if(TextUtils.isEmpty(chinese)){
            return null;
        }
        //用来设置转化的拼音的大小写，或者声调
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//转化的拼音不带声调
//        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);//字符编码类型

        //由于只能对单个汉字转化，所以需要将字符串转化为字符数组，然后对每个字符转化，最后拼接起来
        char[] chars = chinese.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isWhitespace(chars[i])) {//判断字符是否为空格
                continue;
            }

            if (chars[i]>127){//判断是否为汉字，一个汉字占两个字节，一个字节范围为-128-127，汉字肯定大于127，大于127不一定都是汉字
                //汉字多音字，所以用数组接收
                try {
                    String[] initial = PinyinHelper.toHanyuPinyinStringArray(chars[i],format);
                    if (initial!=null){
                        sb.append(initial[0]);//有多音字，取第一个拼音
                    }else {//没有找到对应拼音，汉字有问题，或者可能不是汉字,忽略

                    }
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    //转化失败，不是汉字，比如O(∩_∩)O~，忽略
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
            }else {
                //肯定不是汉字，应该是键盘上能够直接输入的字符，这些字符能够排序，但不能获取拼音
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
