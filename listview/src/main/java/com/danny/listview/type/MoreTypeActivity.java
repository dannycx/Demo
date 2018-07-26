package com.danny.listview.type;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.listview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 多类型listview
 * Created by danny on 5/16/18.
 */

public class MoreTypeActivity extends AppCompatActivity {
    private Context mContext;
    private TextView mMemory;
    private TextView mSd;
    private TextView mTitle;
    private ListView mListView;
    private MyAdapter mAdapter;
    private List<AppInfo> mInfos;
    private List<AppInfo> mSystems;
    private List<AppInfo> mUsers;
    private AppInfo mAppInfo;
    private ProgressDialog mDialog;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            mDialog.dismiss();
            mAdapter=new MyAdapter();
            mListView.setAdapter(mAdapter);
            if (mTitle!=null && mUsers!=null){
                mTitle.setText("用户应用("+mUsers.size()+")");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_type);
        mContext=this;
        initView();
        mDialog=ProgressDialog.show(this, "标题", "加载中，请稍后……");
    }

    private void initView() {
        mMemory=findViewById(R.id.more_type_memory);
        mSd=findViewById(R.id.more_type_sd);
        mListView=findViewById(R.id.more_type_list);
        mTitle=findViewById(R.id.more_type_title);
        initData();
        String memoryPath= Environment.getDataDirectory().getAbsolutePath();
        String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath();
        mMemory.setText("磁盘可用:"+Formatter.formatFileSize(mContext,getSize(memoryPath)));
        mSd.setText("sd卡可用:");Formatter.formatFileSize(mContext,getSize(sdPath));

    }

    private long getSize(String path) {
        StatFs statFs=new StatFs(path);
        long count=statFs.getAvailableBlocks();//可用区块个数
        long size=statFs.getBlockSize();//区块大小
        long result=count*size;
        return result;
    }

    private void initData() {
        mSystems=new ArrayList<>();
        mUsers=new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mInfos= AppProviderEngine.getAppInfos(mContext);
                if (mInfos!=null) {
                    for (AppInfo info : mInfos) {
                        if (info.isSystem == true) {
                            mSystems.add(info);
                        }else {
                            mUsers.add(info);
                        }
                    }
                }
                mHandler.sendEmptyMessage(0);
            }
        }).start();

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem<mUsers.size()+1){
                    mTitle.setText("用户应用("+mUsers.size()+")");
                }else {
                    mTitle.setText("系统应用("+mSystems.size()+")");
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0 || position==mUsers.size()+1){
                    return;
                }else {
                    if (position<mUsers.size()+1){
                        mAppInfo=mUsers.get(position-1);
                    }else {
                        mAppInfo=mSystems.get(position-mUsers.size()-2);
                    }
                    showPopupWindow(view);
                }
            }
        });
    }

    //popupwindow 淡入淡出(缩放,透明动画),显示view右侧,与view等高
    private void showPopupWindow(View v) {
        View view=View.inflate(mContext,R.layout.more_type_popup_window,null);
        TextView uninstall=view.findViewById(R.id.more_type_popup_window_uninstall);
        TextView open=view.findViewById(R.id.more_type_popup_window_open);
        TextView share=view.findViewById(R.id.more_type_popup_window_share);

        AlphaAnimation alpha=new AlphaAnimation(0,1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);

        ScaleAnimation scale=new ScaleAnimation(0,1,
                0,1, Animation.RELATIVE_TO_SELF,0.5f
                ,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(1000);
        scale.setFillAfter(true);

        AnimationSet set=new AnimationSet(true);
        set.addAnimation(alpha);
        set.addAnimation(scale);

        view.startAnimation(set);

        final PopupWindow popupWindow=new PopupWindow(view
                , LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());//设置背景，透明，响应回退
        popupWindow.showAsDropDown(v,50,-v.getHeight());


        uninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uninstall();
                popupWindow.dismiss();
            }
        });
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
                popupWindow.dismiss();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                popupWindow.dismiss();
            }
        });
    }

    private void share() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"分享一个应用,应用名称:"+mAppInfo.name);
        startActivity(intent);
    }

    private void open() {
        PackageManager pm=getPackageManager();
        Intent intent=pm.getLaunchIntentForPackage(mAppInfo.packageName);
        if (intent==null){
            Toast.makeText(mContext,"此应用不能开启!",Toast.LENGTH_SHORT).show();
        }else {
            startActivity(intent);
        }
    }

    private void uninstall() {
        if (mAppInfo.isSystem){
            Toast.makeText(mContext,"系统应用,不可卸载",Toast.LENGTH_SHORT).show();
        }else {
            if (!mAppInfo.packageName.equals(getPackageName())) {
                Intent intent = new Intent("android.intent.action.DELETE");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package:" + mAppInfo.packageName));
                startActivity(intent);
            }else {
                Toast.makeText(mContext,"本应用不可卸载!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MyAdapter extends BaseAdapter{

        //返回两种类型数据
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount()+1;
        }

        //条目类型状态码:0-纯文本  1-文本+图片
        @Override
        public int getItemViewType(int position) {
            if (position==0 || mUsers.size()+1==position){
                return 0;//标题
            }else {
                return 1;//内容
            }
        }

        @Override
        public int getCount() {//两种类型数据
            return mUsers.size()+mSystems.size()+2;
        }

        @Override
        public AppInfo getItem(int position) {
            if (position==0 || position==mUsers.size()+1){
                return mUsers.get(position-1);
            }
            if (position<mUsers.size()+1){
                return mUsers.get(position-1);
            }else {
                return mSystems.get(position-mUsers.size()-2);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type=getItemViewType(position);
            if (type==0){
                TitleHolder holder=null;
                if (convertView==null){
                    holder=new TitleHolder();
                    convertView= View.inflate(parent.getContext(),R.layout.more_type_item_title,null);
                    holder.mTitle=convertView.findViewById(R.id.more_type_item_title);
                    convertView.setTag(holder);
                }else {
                    holder= (TitleHolder) convertView.getTag();
                }
                if (position==0){
                    holder.mTitle.setText("用户应用("+mUsers.size()+")");
                }else {
                    holder.mTitle.setText("系统应用("+mSystems.size()+")");
                }
                return convertView;
            }else {
                ContentHolder holder=null;
                if (convertView==null){
                    holder=new ContentHolder();
                    convertView=View.inflate(parent.getContext(),R.layout.more_type_item,null);
                    holder.mIcon=convertView.findViewById(R.id.more_type_item_icon);
                    holder.mName=convertView.findViewById(R.id.more_type_item_name);
                    holder.mPath=convertView.findViewById(R.id.more_type_item_path);
                    convertView.setTag(holder);
                }else {
                    holder= (ContentHolder) convertView.getTag();
                }
                holder.mIcon.setBackgroundDrawable((getItem(position)).icon);
                holder.mName.setText((getItem(position)).name);
                if (position==0 || position==mUsers.size()+1){

                }else {
                    if (position<mUsers.size()+1){
                        holder.mPath.setText("用户应用");
                    }else {
                        holder.mPath.setText("系统应用");
                    }
                }
                return convertView;
            }
        }
    }

    static class TitleHolder {
        TextView mTitle;
    }

    static class ContentHolder {
        ImageView mIcon;
        TextView mName;
        TextView mPath;
    }
}
