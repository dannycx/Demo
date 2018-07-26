package com.danny.file.room;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danny.file.R;
import com.danny.file.room.friend.CallDataSource;
import com.danny.file.room.friend.CallInjection;
import com.danny.file.room.friend.CallRepository;
import com.danny.file.room.friend.MissedCall;
import com.danny.file.room.takeout.TakeoutDataSource;
import com.danny.file.room.takeout.TakeoutInjection;
import com.danny.file.room.takeout.TakeoutRepository;
import com.danny.file.room.takeout.TakeoutSeller;

import java.util.List;
import java.util.regex.Pattern;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = RoomActivity.class.getSimpleName();
    private TakeoutRepository mRepository;
    private Button mInsert;
    private Button mDeleteFood;
    private Button mDeleteSeller;
    private Button mDeleteAll;
    private Button mUpdate;
    private Button mUpdateDirector;
    private Button mQuery;
    private Button mQueryAll;
    private Button mExpandInsert;
    private EditText mSellerName;
    private EditText mFoodName;
    private EditText mPrice;
    private EditText mCount;
    private EditText mExpand1;
    private Pattern mIntPattern;
    private Pattern mDoublePattern;

    private EditText mCallAccount;
    private EditText mCallName;
    private EditText mCallCount;
    private Button mCallAdd;
    private Button mCallQuery;
    private CallRepository mCallRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mRepository= TakeoutInjection.getInstance(this);
        mCallRepository = CallInjection.getInstance(this);
        initView();
        mIntPattern=Pattern.compile("(\\d)+");
        mDoublePattern=Pattern.compile("\\d+(\\.\\d+)?");
    }

    private void initView() {
        mSellerName=findViewById(R.id.room_seller_name);
        mFoodName=findViewById(R.id.room_food_name);
        mPrice=findViewById(R.id.room_price);
        mCount=findViewById(R.id.room_count);
        mExpand1=findViewById(R.id.room_expand_1);
        mCallAccount=findViewById(R.id.room_call_account);
        mCallName=findViewById(R.id.room_call_name);
        mCallCount=findViewById(R.id.room_call_count);

        mInsert=findViewById(R.id.room_add);
        mDeleteFood=findViewById(R.id.room_delete_food);
        mDeleteSeller=findViewById(R.id.room_delete_seller);
        mDeleteAll=findViewById(R.id.room_delete);
        mUpdate=findViewById(R.id.room_update);
        mUpdateDirector=findViewById(R.id.room_update_direct);
        mQuery=findViewById(R.id.room_query);
        mQueryAll=findViewById(R.id.room_query_all);
        mExpandInsert=findViewById(R.id.room_expand_add);
        mCallAdd=findViewById(R.id.room_call_add);
        mCallQuery=findViewById(R.id.room_call_query);
        mInsert.setOnClickListener(this);
        mDeleteFood.setOnClickListener(this);
        mDeleteSeller.setOnClickListener(this);
        mDeleteAll.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mUpdateDirector.setOnClickListener(this);
        mQuery.setOnClickListener(this);
        mQueryAll.setOnClickListener(this);
        mExpandInsert.setOnClickListener(this);
        mCallAdd.setOnClickListener(this);
        mCallQuery.setOnClickListener(this);
    }

    /**
     * 添加商品
     */
    public void add(){
        final String seller_name = mSellerName.getText().toString().trim();
        final String food_name = mFoodName.getText().toString().trim();
        final String price = mPrice.getText().toString().trim();
        final String count = mCount.getText().toString().trim();
        if (mSellerName.length()!=0 || mFoodName.length()!=0 || mPrice.length()!=0 || mCount.length()!=0) {
            mRepository.findFood(food_name, new TakeoutDataSource.LoadSellerCallback() {

                @Override
                public void onSuccess(TakeoutSeller seller) {
                    Log.d(TAG, "onSuccess: " + seller.toString());
                }

                @Override
                public void onError() {
                    TakeoutSeller seller = new TakeoutSeller();
                    seller.food_name = food_name;
                    seller.count = Integer.parseInt(count);
                    seller.price = Double.parseDouble(price);
                    seller.seller_name = seller_name;
                    mRepository.insertTakeoutSeller(seller);
                }
            });
        }
    }

    /**
     * 一次减少n个,修改商品数量,减得数量大于当前数量,则删除该商品
     */
    public void deleteCount(){
        final String food_name=mFoodName.getText().toString().trim();
        final String count=mCount.getText().toString().trim();
        if (mFoodName.length()!=0 && mCount.length()!=0) {
            mRepository.findFood(food_name, new TakeoutDataSource.LoadSellerCallback() {
                @Override
                public void onSuccess(TakeoutSeller seller) {
                    int reduce_count = Integer.parseInt(count);
                    if (seller.count>reduce_count) {
                        mRepository.updateFoodCount(-reduce_count, food_name);
                    }else {
                        mRepository.deleteTakeoutFood(food_name);
                    }
                }

                @Override
                public void onError() {
                    Toast.makeText(RoomActivity.this, "你还没添加该商品", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.room_add:
                add();
                break;
            case R.id.room_delete_food:
                deleteCount();
                break;
            case R.id.room_delete_seller:
                deleteSeller();
                break;
            case R.id.room_delete:
                delete();
                break;
            case R.id.room_update:
                update();
                break;
            case R.id.room_update_direct:
                updateDirector();
                break;
            case R.id.room_query:
                query();
                break;
            case R.id.room_query_all:
                queryAll();
                break;
            case R.id.room_expand_add:
                expandAdd();
                break;
            case R.id.room_call_query:
                callQuery();
                break;
            case R.id.room_call_add:
                callAdd();
                break;
        }
    }

    /**
     * 版本升级添加打电话表,添加数据
     */
    private void callAdd() {
        final String call_account = mCallAccount.getText().toString().trim();
        final String call_name = mCallName.getText().toString().trim();
        final String call_count = mCallCount.getText().toString().trim();
        if (call_account.length()!=0 && call_name.length()!=0 && call_count.length()!=0){
            mCallRepository.queryPointCall(call_account, new CallDataSource.LoadPointCallCallback() {
                @Override
                public void onSuccess(MissedCall call) {
                    Log.d(TAG, "onSuccess: "+call.toString());
                }

                @Override
                public void onError() {
                    MissedCall call=new MissedCall();
                    call.call_account=call_account;
                    call.call_name=call_name;
                    call.call_count=Integer.parseInt(call_count);
                    mCallRepository.insertCall(call,null);
                }
            });
        }
    }

    /**
     * 版本升级添加打电话表,查询数据
     */
    private void callQuery() {
        mCallRepository.queryAllCall(new CallDataSource.LoadCallCallback() {
            @Override
            public void onSuccess(List<MissedCall> calls) {
                for (MissedCall call:calls){
                    Log.d(TAG, "onSuccess: "+call.toString());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     *
     */
    private void expandAdd() {
        final String seller_name = mSellerName.getText().toString().trim();
        final String food_name = mFoodName.getText().toString().trim();
        final String price = mPrice.getText().toString().trim();
        final String count = mCount.getText().toString().trim();
        final String expand1 = mExpand1.getText().toString().trim();
        if (mSellerName.length()!=0 || mFoodName.length()!=0 || mPrice.length()!=0 || mCount.length()!=0 || mExpand1.length()!=0) {
            mRepository.findFood(food_name, new TakeoutDataSource.LoadSellerCallback() {

                @Override
                public void onSuccess(TakeoutSeller seller) {
                    Log.d(TAG, "onSuccess: " + seller.toString());
                }

                @Override
                public void onError() {
                    TakeoutSeller seller = new TakeoutSeller();
                    seller.food_name = food_name;
                    seller.count = Integer.parseInt(count);
                    seller.price = Double.parseDouble(price);
                    seller.seller_name = seller_name;
                    seller.time_date = expand1;
                    mRepository.insertTakeoutSeller(seller);
                }
            });
        }
    }

    /**
     * 删除指定家的商品
     */
    private void deleteSeller() {
        String seller_name = mSellerName.getText().toString().trim();
        if (seller_name.length()!=0) {
            mRepository.deleteTakeoutSeller(seller_name);
        }
    }

    /**
     * 删除表中所有记录
     */
    private void delete() {mRepository.deleteTakeoutSeller();}

    /**
     * 查所有
     */
    private void queryAll() {
        mRepository.queryAllFood(new TakeoutDataSource.LoadAllSellerCallback() {
            @Override
            public void onSuccess(List<TakeoutSeller> sellers) {
                Log.d(TAG, "onSuccess: "+sellers.size());
                for (TakeoutSeller seller:sellers) {
                    Log.d(TAG, "onSuccess: " + seller.food_name);
                    Log.d(TAG, "onSuccess: " + seller.count);
                    Log.d(TAG, "onSuccess: " + seller.seller_name);
                }
            }

            @Override
            public void onError() {}
        });
    }

    /**
     * 名字查询指定商品
     */
    private void query() {
        String food_name = mFoodName.getText().toString().trim();
        if (mFoodName.length()!=0) {
            mRepository.findFood(food_name, new TakeoutDataSource.LoadSellerCallback() {
                @Override
                public void onSuccess(TakeoutSeller seller) {
                    Log.d(TAG, "onSuccess: "+seller.food_name);
                    Log.d(TAG, "onSuccess: "+seller.count);
                }

                @Override
                public void onError() {}
            });
        }
    }

    /**
     * 一次增加n个,修改商品数量
     */
    private void update() {
        String food_name=mFoodName.getText().toString().trim();
        String count= mCount.getText().toString().trim();
        if (mFoodName.length()!=0 && mCount.length()!=0) {
            mRepository.updateFoodCount(Integer.parseInt(count),food_name);
        }
    }

    /**
     * 直接改商品数量
     * EditView不输入内容不可通过TextUtils判断,可通过控件的length()!=0进行判断 或 拿到的字符串length()!=0进行判断
     */
    private void updateDirector() {
        String food_name=mFoodName.getText().toString().trim();
        String count=mCount.getText().toString().trim();
        Log.e(TAG, "updateDirector: "+count);
//        if (!TextUtils.isEmpty(count) || !TextUtils.isEmpty(food_name)){//不可用
//            Log.e(TAG, "updateDirector: "+count);
//            mRepository.modifyFoodCount(Integer.parseInt(count),food_name);
//        }
        if (mFoodName.length()!=0 && mCount.length()!=0){//可用
            mRepository.modifyFoodCount(Integer.parseInt(count),food_name);
        }

//        if (food_name.length()!=0 && count.length()!=0){//可用
//            mRepository.modifyFoodCount(Integer.parseInt(count),food_name);
//        }
    }
}
