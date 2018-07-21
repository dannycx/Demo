package com.danny.temp.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.temp.R;
import com.danny.temp.adapter.AlphabetAdapter;
import com.danny.temp.domain.Person;
import com.danny.temp.util.Constant;
import com.danny.temp.widget.AlphabetView;
import com.danny.temp.widget.CircleImageView;
import com.danny.temp.widget.ForbidOperateLinearLayout;
import com.danny.temp.widget.SideSlipLayout;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SideSlipActivity";

    private AlphabetView mAlphabetView;
    private ListView mListView;
    private TextView mCurrentAlphabet;
    private ArrayList<Person> mPersonList;
    private Handler mHandler =new Handler();
    private boolean mIsScale=false;

    private ListView mMenuListView;
    private SideSlipLayout mSideSlipLayout;
    private ImageView mIcon;
    private ForbidOperateLinearLayout mForbidOperateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_slip);

        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mAlphabetView=findViewById(R.id.alphabet);
        mListView=findViewById(R.id.alphabet_list_view);
        mCurrentAlphabet=findViewById(R.id.alphabet_current);

        mMenuListView=findViewById(R.id.menu_list_view);
        mSideSlipLayout=findViewById(R.id.side_slip_root);
        mIcon=findViewById(R.id.main_head_icon);
        mForbidOperateLayout = findViewById(R.id.main_forbid_operate);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mMenuListView.setAdapter(new MenuListAdapter());
        mMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, Constant.sTitle[position], Toast.LENGTH_SHORT).show();
            }
        });


        mAlphabetView.setOnTouchAlphabetListener(new AlphabetView.OnTouchAlphabetListener() {
            @Override
            public void onTouchAlphabet(String alphabet) {
                Log.d(TAG, "onTouchAlphabet: "+alphabet);
                //根据当前触摸的字母，去集合中找看哪个item的首字母和alphabet一样，然后将对应的item设置为第一个可见的item
                for (int i = 0; i < mPersonList.size(); i++) {
                    String initial = mPersonList.get(i).getInitial().charAt(0)+"";
                    if (initial.equals(alphabet)){
                        mListView.setSelection(i);
                        break;//找到item就结束循环
                    }
                }
                showCurrentAlphabet(alphabet);
            }
        });

        //隐藏
//        mCurrentAlphabet.setVisibility(View.INVISIBLE);
        ViewHelper.setScaleX(mCurrentAlphabet,0);
        ViewHelper.setScaleY(mCurrentAlphabet,0);

        initListData();
        Collections.sort(mPersonList);
        mListView.setAdapter(new AlphabetAdapter(mPersonList,this));

//        mMainListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Constant.sNAMES){
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                TextView view = (TextView) (convertView!=null ? convertView :super.getView(position,convertView,parent));
//                //缩小
//                ViewHelper.setScaleX(view,0.5f);
//                ViewHelper.setScaleY(view,0.5f);
//                //放大
//                ViewPropertyAnimator.animate(view).scaleX(1.0f).setDuration(300).start();
//                ViewPropertyAnimator.animate(view).scaleY(1.0f).setDuration(300).start();
//                return view;
//            }
//        });

        mSideSlipLayout.setOnSideSlipLayoutStateChangedListener(new SideSlipLayout.OnSideSlipLayoutStateChangedListener() {
            @Override
            public void open() {
//                Log.d(TAG, "open: ");
//                mMenuListView.smoothScrollToPosition(new Random().nextInt(mMenuListView.getCount()));
            }

            @Override
            public void close() {
                ViewPropertyAnimator.animate(mIcon).translationXBy(20).setInterpolator(new CycleInterpolator(4)).setDuration(500).start();
            }

            @Override
            public void onDragging(float fraction) {
                ViewHelper.setAlpha(mIcon,1-fraction);
            }
        });

        mForbidOperateLayout.setSlipLayout(mSideSlipLayout);
    }

    class MenuListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return Constant.sTitle.length;
        }

        @Override
        public String getItem(int position) {
            return Constant.sTitle[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(parent.getContext(),R.layout.layout_menu_item,null);
            CircleImageView icon = view.findViewById(R.id.layout_menu_item_icon);
            TextView title = view.findViewById(R.id.layout_menu_item_title);
            icon.setImageResource(Constant.sImages[position]);
            title.setText(Constant.sTitle[position]);
            return view;
        }
    }

    /**
     * 显示选中的字母
     */
    private void showCurrentAlphabet(String alphabet) {
        mCurrentAlphabet.setText(alphabet);
        if (!mIsScale){
            mIsScale=true;
            //OvershootInterpolator:向前甩一定值后再回到原来位置
//            mCurrentAlphabet.setVisibility(View.VISIBLE);
            ViewPropertyAnimator.animate(mCurrentAlphabet).scaleX(1.0f).setInterpolator(new OvershootInterpolator()).setDuration(300).start();
            ViewPropertyAnimator.animate(mCurrentAlphabet).scaleY(1.0f).setInterpolator(new OvershootInterpolator()).setDuration(300).start();
        }

        //移除之前消息
        mHandler.removeCallbacksAndMessages(null);

        //延时隐藏选中字母
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mCurrentAlphabet.setVisibility(View.INVISIBLE);
                //动画实现
                ViewPropertyAnimator.animate(mCurrentAlphabet).scaleX(0).setDuration(300).start();
                ViewPropertyAnimator.animate(mCurrentAlphabet).scaleY(0).setDuration(300).start();
                mIsScale=false;
            }
        },1000);
    }

    private void initListData() {
        mPersonList = new ArrayList<>();
        // 虚拟数据
        mPersonList.add(new Person("俞灶迟"));
        mPersonList.add(new Person("李彩早"));
        mPersonList.add(new Person("陈启红"));
        mPersonList.add(new Person("何颖升"));
        mPersonList.add(new Person("张三"));
        mPersonList.add(new Person("丘约靖"));
        mPersonList.add(new Person("陈原庚"));
        mPersonList.add(new Person("阿三"));
        mPersonList.add(new Person("阿四"));
        mPersonList.add(new Person("段誉"));
        mPersonList.add(new Person("段正淳"));
        mPersonList.add(new Person("张三丰"));
        mPersonList.add(new Person("陈坤"));
        mPersonList.add(new Person("张淮森"));
        mPersonList.add(new Person("林俊杰"));
        mPersonList.add(new Person("岑弥勳"));
        mPersonList.add(new Person("王生安"));
        mPersonList.add(new Person("周逸依"));
        mPersonList.add(new Person("张四"));
        mPersonList.add(new Person("林俊杰"));
        mPersonList.add(new Person("王二"));
        mPersonList.add(new Person("朱付流"));
        mPersonList.add(new Person("赵四"));
        mPersonList.add(new Person("杨坤"));
        mPersonList.add(new Person("赵子龙"));
        mPersonList.add(new Person("杨坤"));
        mPersonList.add(new Person("宁古薄"));
        mPersonList.add(new Person("李伟"));
        mPersonList.add(new Person("张祥德"));
        mPersonList.add(new Person("宋江"));
        mPersonList.add(new Person("张顺谷"));
        mPersonList.add(new Person("李伟"));
        mPersonList.add(new Person("吴湘"));
        mPersonList.add(new Person("周卓浩"));
        mPersonList.add(new Person("张昧谡"));
        mPersonList.add(new Person("池慕营"));
        mPersonList.add(new Person("梁澄静"));
    }
}
