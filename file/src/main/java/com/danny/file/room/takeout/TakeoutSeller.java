package com.danny.file.room.takeout;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * 外卖表
 * Created by danny on 4/21/18.
 */
@Entity(tableName = "seller")
public class TakeoutSeller {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String seller_name;
    public String food_name;
    public int count;
    public double price;
    public String time_date;//版本升级添加新字段

    @Override
    public String toString() {
        return "TakeoutSeller{" +
                "id='" + id + '\'' +
                ", seller_name='" + seller_name + '\'' +
                ", food_name='" + food_name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", time_date=" + time_date +
                '}';
    }
}
