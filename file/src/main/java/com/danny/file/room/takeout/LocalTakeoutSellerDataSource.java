package com.danny.file.room.takeout;


import com.danny.file.utils.TestExecutors;

import java.util.List;

/**
 * Created by danny on 4/21/18.
 */

public class LocalTakeoutSellerDataSource implements TakeoutDataSource {
    private static volatile LocalTakeoutSellerDataSource sInstance = null;
    private TestExecutors mExecutors;
    private TakeoutSellerDao mSellerDao;
    private TakeoutSeller mTakeoutSeller;
    private List<TakeoutSeller> mSellers;

    private LocalTakeoutSellerDataSource(TestExecutors executors, TakeoutSellerDao takeoutSellerDao) {
        this.mExecutors = executors;
        this.mSellerDao = takeoutSellerDao;
    }


    public static LocalTakeoutSellerDataSource getInstance(TestExecutors executors, TakeoutSellerDao takeoutSellerDao) {
        if (sInstance == null) {
            synchronized (LocalTakeoutSellerDataSource.class) {
                if (sInstance == null) {
                    sInstance = new LocalTakeoutSellerDataSource(executors, takeoutSellerDao);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void insertTakeoutSeller(final TakeoutSeller seller) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mSellerDao.insertTakeoutSeller(seller);}
        });
    }

    @Override
    public void queryAllFood(final LoadAllSellerCallback callback) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mSellers = mSellerDao.queryAllFood();
                mExecutors.getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!mSellers.isEmpty()) {
                            if (callback != null) {callback.onSuccess(mSellers);}
                        } else {
                            if (callback != null) {callback.onError();}
                        }
                    }
                });
            }
        });
    }

    @Override
    public void findFood(final String foodName, final LoadSellerCallback callback) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mTakeoutSeller=mSellerDao.findFood(foodName);
                mExecutors.getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mTakeoutSeller!=null){
                            if (callback!=null){callback.onSuccess(mTakeoutSeller);}
                        }else {
                            if (callback!=null){callback.onError();}
                        }
                    }
                });
            }
        });
    }

    @Override
    public void updateFoodCount(final int i, final String foodName) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mSellerDao.updateFoodCount(i,foodName);}
        });
    }

    @Override
    public void modifyFoodCount(final int i, final String foodName) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mSellerDao.modifyFoodCount(i,foodName);}
        });
    }

    @Override
    public void deleteTakeoutSeller(final String sellerName) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mSellerDao.deleteTakeoutSeller(sellerName);}
        });
    }

    @Override
    public void deleteTakeoutFood(final String foodName) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mSellerDao.deleteTakeoutFood(foodName);}
        });
    }

    @Override
    public void deleteTakeoutSeller() {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mSellerDao.deleteTakeoutSeller();}
        });
    }
}
