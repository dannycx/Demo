package com.danny.file.room.friend;


import com.danny.file.utils.TestExecutors;

import java.util.List;

/**
 * 数据库操作类实现
 * Created by danny on 3/30/18.
 */

public class LocalCallDataSource implements CallDataSource {
    private static volatile LocalCallDataSource sInstance;
    private TestExecutors mExecutors;
    private MissedCallDao mMissedCallDao;
    private List<MissedCall> mMissedCalls;
    private MissedCall mCall;

    private LocalCallDataSource(TestExecutors executors, MissedCallDao callDao){
        mExecutors=executors;
        mMissedCallDao=callDao;
    }

    public static LocalCallDataSource getInstance(TestExecutors executors, MissedCallDao callDao){
        if (sInstance==null){
            synchronized (LocalCallDataSource.class){
                if (sInstance==null){
                    sInstance=new LocalCallDataSource(executors,callDao);
                }
            }
        }
        return sInstance;
    }

    @Override
    public void insertCall(final MissedCall call, final SuccessCallback callback) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMissedCallDao.insertCall(call);
                mExecutors.getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {if (callback!=null){callback.onSuccess();}}
                });
            }
        });
    }

    @Override
    public void queryAllCall(final LoadCallCallback callback) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMissedCalls=mMissedCallDao.queryAllCall();
                mExecutors.getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!mMissedCalls.isEmpty()){
                             if (callback!=null){callback.onSuccess(mMissedCalls);}
                        }else {
                            if (callback!=null){callback.onError();}
                        }
                    }
                });
            }
        });
    }

    @Override
    public void queryPointCall(final String account, final LoadPointCallCallback callback) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mCall=mMissedCallDao.queryPointCall(account);
                mExecutors.getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mCall!=null){
                            if (callback!=null){callback.onSuccess(mCall);}
                        }else {
                            if (callback!=null){callback.onError();}
                        }
                    }
                });
            }
        });
    }

    @Override
    public void updateCallCount(final int i, final String account, final SuccessCallback callback) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mMissedCallDao.updateCallCount(i,account);
                mExecutors.getMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {if (callback!=null){callback.onSuccess();}}
                });
            }
        });
    }

    @Override
    public void deleteCall(final String account) {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mMissedCallDao.deleteCall(account);}
        });
    }

    @Override
    public void deleteCall() {
        mExecutors.getIoExecutor().execute(new Runnable() {
            @Override
            public void run() {mMissedCallDao.deleteCall();}
        });
    }
}
