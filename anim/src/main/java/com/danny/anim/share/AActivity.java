package com.danny.anim.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.danny.anim.R;
import com.danny.anim.share.util.Transition;
import com.danny.anim.share.util.TransitionOptions;

import java.util.ArrayList;
import java.util.List;

public class AActivity extends AppCompatActivity {
    private ListView mListView;
    private AAdapter mAdapter;
    private List<String> mLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        initData();
        initListView();
    }

    private void initData() {
        mLists = new ArrayList<>();
        for (int i=0;i<50;i++){
            mLists.add("张"+i);
        }
    }

    private void initListView() {
        mListView = findViewById(R.id.a_list_view);
        mAdapter = new AAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AActivity.this, BActivity.class);
                intent.putExtra("name", mAdapter.getItem(position));

                // 准备转场动画选项
                TransitionOptions options = TransitionOptions.makeTransitionOptions(
                        AActivity.this
                        , view.findViewById(R.id.a_item_name)
                        , view.findViewById(R.id.a_item_image)
                        , findViewById(R.id.a_top_card));

                // 开启
                Transition.startActivity(intent,options);
            }
        });
    }

    class AAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mLists == null ? 0 : mLists.size();
        }

        @Override
        public String getItem(int position) {
            return mLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
//                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_list, null, false);
//                convertView = View.inflate(parent.getContext(),R.layout.a_item_list,null);
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_item_list,null,false);
            }

            Holder holder = Holder.create(convertView);
            holder.mName.setText(getItem(position));

            return convertView;
        }
    }

    static class Holder {
        ImageView mImage;
        TextView mName;

        private Holder() {}

        private Holder(View view) {
            mImage=view.findViewById(R.id.a_item_image);
            mName=view.findViewById(R.id.a_item_name);
        }

        /**
         * 获取Holder对象
         *
         * @param view 父view
         * @return Holder对象
         */
        static Holder create(View view){
            Holder holder = (Holder) view.getTag();
            if (holder == null){
                holder = new Holder(view);
                view.setTag(holder);
            }
            return holder;
        }

        /**
         * 用来缓存控件，优化加载
         *
         * @param view  itemView的布局
         * @param id    itemView布局中需要缓存控件的id
         * @return  缓存后的控件（textView、imageView...等控件）
         */
//        @SuppressWarnings("unchecked")
        static View get(View view, int id) {
            // 获取itemView的ViewHolder对象，并将其转型为SparseArray<View>
            SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
            if (viewHolder == null) {
                viewHolder = new SparseArray<>();
                view.setTag(viewHolder);
            }

            // 根据控件的id获取itemView布局的控件
            View childView = viewHolder.get(id);
            if (childView == null) {
                childView = view.findViewById(id);
                viewHolder.put(id, childView);
            }

            return childView;
        }
    }
}
