package com.danny.anim.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.danny.anim.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class List2Activity extends AppCompatActivity {
    private ListView mListView;
    private MyAdapter mAdapter;
    private List<Drawable> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        initData();
        initList();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(getResources().getDrawable(R.drawable.girl_1));
        mList.add(getResources().getDrawable(R.drawable.girl_2));
        mList.add(getResources().getDrawable(R.drawable.girl_3));
        mList.add(getResources().getDrawable(R.drawable.girl_4));
        mList.add(getResources().getDrawable(R.drawable.girl_5));
        mList.add(getResources().getDrawable(R.drawable.girl_6));
        mList.add(getResources().getDrawable(R.drawable.girl_3));
        mList.add(getResources().getDrawable(R.drawable.girl_1));
        mList.add(getResources().getDrawable(R.drawable.girl_5));
    }

    private void initList() {
        mListView = findViewById(R.id.list2_view);
        mAdapter = new MyAdapter(this, mListView, 300);
        mListView.setAdapter(mAdapter);
    }

    class MyAdapter extends BaseAdapter {
        private int mLength = 0;
        private LayoutInflater mInflater;
        private Context mContext;
        private ListView mListView;

        private boolean mIsScrollDown;
        private int mFirstPosition;
        private int mFirstTop;

        private Animation mAnimation;

        AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            /**
             * 滑动
             *
             * @param view 是当前listView的对象
             * @param firstVisibleItem 表示当前第一个可见的item在listView所有item中的索引
             * @param visibleItemCount 表示当前屏幕中可见的有几条item
             * @param totalItemCount 表示当前listView总共有多少条item
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstChild = view.getChildAt(0);
                if (firstChild == null) return;
                int top = firstChild.getTop();
                /**
                 * firstVisibleItem > mFirstPosition表示向下滑动一整个Item
                 * mFirstTop > top表示在当前这个item中滑动
                 */
                mIsScrollDown = firstVisibleItem > mFirstPosition || mFirstTop > top;
                mFirstTop = top;
                mFirstPosition = firstVisibleItem;
            }
        };

        public MyAdapter(Context context, ListView listView, int length) {
            mLength = length;
            mInflater = LayoutInflater.from(context);
            mContext = context;
            mListView = listView;
            mAnimation = AnimationUtils.loadAnimation(context, R.anim.bottom_in_anim);
            mListView.setOnScrollListener(mOnScrollListener);
        }

        @Override
        public int getCount() {
            return mLength;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position % mList.size());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list2_item, null);
                holder.mImage = convertView.findViewById(R.id.list2_img);
                holder.mText = convertView.findViewById(R.id.list2_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //清除当前显示区域中所有item的动画
            for (int i = 0; i < mListView.getChildCount(); i++) {// getChildCount表示当前屏幕显示区域中，总共有多少个item
                View view = mListView.getChildAt(i);
                view.clearAnimation();
            }

            //然后给当前item添加上动画
            if (mIsScrollDown) {
                convertView.startAnimation(mAnimation);
            }

            holder.mImage.setImageDrawable(mList.get(position % mList.size()));
            holder.mText.setText(position + "");

            return convertView;
        }

        public class ViewHolder {
            ImageView mImage;
            TextView mText;
        }
    }
}
