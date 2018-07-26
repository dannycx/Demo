package com.danny.file.sql;

/**
 * 数据库封装的bean
 * Created by danny on 5/29/18.
 * id Integer primary key autoincrement,imageId,title,_desc,comment,type,time
 */

public class Subscribe {
    public int _id;
    public int image_id;
    public String title;

    @Override
    public String toString() {
        return "Subscribe{" +
                "_id=" + _id +
                ", image_id=" + image_id +
                ", title='" + title + '\'' +
                '}';
    }
}
