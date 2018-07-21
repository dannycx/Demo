package com.danny.imageview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class GalleryActivity extends AppCompatActivity {
    private Context mContext;
    private ImageSwitcher mSwitcher;
    private Gallery mGallery;
    private int[] mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        mSwitcher=findViewById(R.id.gallery_switcher);
        mGallery=findViewById(R.id.gallery_gallery);
        mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(mContext);
//                imageView.setBackgroundColor(0xff000000);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT));
                return imageView;
            }
        });

        BaseAdapter adapter=new BaseAdapter() {
            @Override
            public int getCount() {return mImages.length;}

            @Override
            public Object getItem(int position) {return position;}

            @Override
            public long getItemId(int position) {return position;}

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView imageView;
                if (convertView==null){
                    imageView=new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setPadding(5,0,5,0);
                }else {
                    imageView= (ImageView) convertView;
                }
                imageView.setImageResource(mImages[position]);
                return imageView;
            }
        };
        mGallery.setAdapter(adapter);
        mGallery.setSelection(mImages.length/2);
        mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSwitcher.setImageResource(mImages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void initData() {
        mImages = new int[]{
                R.drawable.girl_1, R.drawable.girl_2, R.drawable.girl_3
                , R.drawable.girl_4, R.drawable.girl_5, R.drawable.girl_6
        };
    }
}
