package com.danny.file.room.takeout;

/**
 * Created by danny on 4/21/18.
 */

public class TakeoutRepository implements TakeoutDataSource {
    private static TakeoutRepository sInstance=null;
    private LocalTakeoutSellerDataSource mDataSource;

    private TakeoutRepository(LocalTakeoutSellerDataSource dataSource){this.mDataSource=dataSource;}

    public static TakeoutRepository getInstance(LocalTakeoutSellerDataSource dataSource){
        if (sInstance==null){
            synchronized (TakeoutRepository.class){
                if (sInstance==null){
                    sInstance=new TakeoutRepository(dataSource);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void insertTakeoutSeller(TakeoutSeller seller) {mDataSource.insertTakeoutSeller(seller);}

    @Override
    public void queryAllFood(LoadAllSellerCallback callback) {mDataSource.queryAllFood(callback);}

    @Override
    public void findFood(String foodName, LoadSellerCallback callback) {mDataSource.findFood(foodName,callback);}

    @Override
    public void updateFoodCount(int i, String foodName) {mDataSource.updateFoodCount(i,foodName);}

    @Override
    public void modifyFoodCount(int i, String foodName) {mDataSource.modifyFoodCount(i,foodName);}

    @Override
    public void deleteTakeoutSeller(String sellerName) {mDataSource.deleteTakeoutSeller(sellerName);}

    @Override
    public void deleteTakeoutFood(String foodName) {mDataSource.deleteTakeoutFood(foodName);}

    @Override
    public void deleteTakeoutSeller() {mDataSource.deleteTakeoutSeller();}
}
