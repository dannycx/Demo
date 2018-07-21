package com.danny.imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.danny.imageview.cache.DcxBitmapUtils;
import com.danny.imageview.cache.LocalCacheBitmap;

public class CacheActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        mListView=findViewById(R.id.cache_list);

        mListView.setAdapter(new ListAdapter());
    }

    class ListAdapter extends BaseAdapter {
        private DcxBitmapUtils mBitmapUtils;

        public ListAdapter() {mBitmapUtils = new DcxBitmapUtils();}

        @Override
        public int getCount() {return Constant.mImage.length;}

        @Override
        public String getItem(int position) {return Constant.mImage[position];}

        @Override
        public long getItemId(int position) {return position;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder=null;
            if (convertView==null){
                holder=new Holder();
                convertView=View.inflate(parent.getContext(),R.layout.cache_item,null);
                holder.mImage=convertView.findViewById(R.id.cache_item_image);
                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }

            mBitmapUtils.display(holder.mImage,getItem(position));
            return convertView;
        }
    }

    class Holder {ImageView mImage;}
}
