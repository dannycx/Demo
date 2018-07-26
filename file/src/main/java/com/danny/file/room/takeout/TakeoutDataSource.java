package com.danny.file.room.takeout;

import java.util.List;

/**
 * Created by danny on 4/21/18.
 */

public interface TakeoutDataSource {
    //加载单个用户信息
    interface LoadSellerCallback{
        void onSuccess(TakeoutSeller seller);
        void onError();
    }

    //加载所有用户信息
    interface LoadAllSellerCallback{
        void onSuccess(List<TakeoutSeller> sellers);
        void onError();
    }

    void insertTakeoutSeller(TakeoutSeller seller);

    void queryAllFood(LoadAllSellerCallback callback);

    void findFood(String foodName, LoadSellerCallback callback);

    void updateFoodCount(int i, String foodName);

    void modifyFoodCount(int i, String foodName);

    void deleteTakeoutSeller(String sellerName);

    void deleteTakeoutFood(String foodName);

    void deleteTakeoutSeller();
}
