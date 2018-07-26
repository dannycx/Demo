package com.danny.file.ergodic;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.file.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 遍历文件夹
 * Created by danny on 6/12/18.
 */

public class ErgodicActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ErgodicActivity.class.getSimpleName();
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private ListView mListView;
    private FileAdapter mAdapter;
    private ImageView mBack;

    private List<Directory> mRootDirectory;
    private File mLastPath;
    private File mCurrentPath;
    private List<Directory> mNextDirectory;
    private String mParentPath = PATH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ergodic);
        mListView=findViewById(R.id.ergodic_list);
        mBack=findViewById(R.id.ergodic_back);
        mBack.setOnClickListener(this);

        getFiles();
    }

    private void getFiles() {
        Log.d(TAG, "getFiles: "+PATH);
        File file = new File(PATH);
        File[] files = file.listFiles();
        mRootDirectory=new ArrayList<>();
        for (int i=0;i<files.length;i++){
            Directory directory=new Directory();
            directory.setFileName(files[i].getName());
            if (files[i].isDirectory()) {
                directory.setDirectory(true);
            }else {
                directory.setDirectory(false);
            }
            mRootDirectory.add(directory);
        }

        if (mRootDirectory!=null){
            mAdapter=new FileAdapter();
            mListView.setAdapter(mAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mRootDirectory.get(position).isDirectory()){
                        Directory directory = mRootDirectory.get(position);
                        Log.d(TAG, "onItemClick: " + directory.getFileName());
                        File fileSecond = new File(mParentPath, directory.getFileName());
                        Log.d(TAG, "onItemClick: " + fileSecond.getAbsolutePath());
                        File[] fileSeconds = fileSecond.listFiles();
                        Log.d(TAG, "onItemClick: "+fileSeconds.length);
                        if (fileSeconds.length==0){
                            Toast.makeText(ErgodicActivity.this, "空文件夹!", Toast.LENGTH_SHORT).show();
                        }else {
                            mRootDirectory.clear();
                            mParentPath = fileSecond.getAbsolutePath();
                            for (int i = 0; i < fileSeconds.length; i++) {
                                Directory d = new Directory();
                                d.setFileName(fileSeconds[i].getName());
                                if (fileSeconds[i].isDirectory()){
                                    d.setDirectory(true);
                                }else {
                                    d.setDirectory(false);
                                }
                                mRootDirectory.add(d);
                            }
                            if (mParentPath==PATH){
                                mBack.setVisibility(View.GONE);
                            }else {
                                mBack.setVisibility(View.VISIBLE);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }else {
                        Toast.makeText(parent.getContext(), "点击了"+(mRootDirectory.get(position).isDirectory()?"文件夹":"文件"),Toast.LENGTH_SHORT).show();
                    }
//                    back();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ergodic_back:
                back();
                break;
        }
    }

    private void back() {
        Log.d(TAG, "back: "+mParentPath+"-"+PATH);
        if (mParentPath==PATH){
            Log.d(TAG, "back: root");
            mBack.setVisibility(View.GONE);
        }else {
            Log.d(TAG, "back: sun");
            mBack.setVisibility(View.VISIBLE);
            File sun = new File(mParentPath);
            File parent = sun.getParentFile();
            if (parent.isDirectory()){
                File[] files = parent.listFiles();
                mRootDirectory.clear();
                mParentPath=parent.getAbsolutePath();
                for (int i = 0; i < files.length; i++) {
                    Directory directory = new Directory();
                    directory.setFileName(files[i].getName());
                    if (files[i].isDirectory()) {
                        directory.setDirectory(true);
                    }else {
                        directory.setDirectory(false);
                    }
                    mRootDirectory.add(directory);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    class FileAdapter extends BaseAdapter {
        @Override
        public int getCount() {return mRootDirectory.size();}

        @Override
        public Directory getItem(int position) {return mRootDirectory.get(position);}

        @Override
        public long getItemId(int position) {return position;}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder=null;
            if (convertView==null){
                holder=new Holder();
                convertView=View.inflate(parent.getContext(),R.layout.ergodic_item,null);
                holder.mImage=convertView.findViewById(R.id.ergodic_item_image);
                holder.mFileName=convertView.findViewById(R.id.ergodic_item_file_name);
                holder.mIncrement=convertView.findViewById(R.id.ergodic_item_add);
                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }
            holder.mIncrement.setTag(position);
            Directory directory = getItem(position);
            if (directory.isDirectory()){
                holder.mFileName.setText(directory.getFileName());
                holder.mIncrement.setVisibility(View.GONE);
                holder.mImage.setImageResource(R.drawable.ergodic_files);
            }else {
                holder.mFileName.setText(directory.getFileName());
                holder.mIncrement.setVisibility(View.VISIBLE);
                holder.mImage.setImageResource(R.drawable.ergodic_file);
            }
            return convertView;
        }
    }

    class Holder {
        ImageView mImage;
        TextView mFileName;
        Button mIncrement;
    }
}
