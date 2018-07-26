package com.danny.file.file;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.file.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by danny on 17-9-14.
 */

public class MachineFileActivity extends AppCompatActivity {
    private static final String TAG = MachineFileActivity.class.getSimpleName();
    private Context mContext;

    private TextView mCurrentPath;
    private ListView mListView;

    private File mCurrentParent;//parentFile
    private File[] mCurrentChildFiles;//subFiles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_file);
        mContext=this;

        //obtain Component
        mCurrentPath= findViewById(R.id.tv_path);
        mListView= findViewById(R.id.lv);

//        File root=new File("/mnt/sdcard/");
        File root=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        if(root.exists()){
            Log.d(TAG, "onCreate: ");
            mCurrentParent=root;
            mCurrentChildFiles=root.listFiles();
            for (File file:mCurrentChildFiles){
                Log.d(TAG, "onCreate: "+file.getName());
            }
            Log.d(TAG, "onCreate: "+mCurrentChildFiles.length);
            inflateListView(mCurrentChildFiles);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                if(mCurrentChildFiles[i].isFile()){
                    OpenFileUtils.getInstance(mContext).openFile(mCurrentChildFiles[i].getName());
                    return;
                }
                File[] temp=mCurrentChildFiles[i].listFiles();
                if (temp==null || temp.length==0){
                    Toast.makeText(getApplicationContext(),
                            "当前文件卡不可访问或该目录下无文件",Toast.LENGTH_SHORT).show();
                }else {
                    mCurrentParent=mCurrentChildFiles[i];
                    mCurrentChildFiles=temp;
                    inflateListView(mCurrentChildFiles);
                }
            }
        });
        Button btn= findViewById(R.id.btn_parent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mCurrentParent.getCanonicalPath().equals(/*"/mnt/sdcard/"*/Environment.getExternalStorageDirectory().getAbsolutePath())){
                        mCurrentParent=mCurrentParent.getParentFile();
                        mCurrentChildFiles=mCurrentParent.listFiles();
                        inflateListView(mCurrentChildFiles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //fill ListView
    private void inflateListView(File[] files){
        List<Map<String,Object>> listItems=new ArrayList<>();
        for(File file:files){
            Map<String,Object> item=new HashMap<>();
            if(file.isDirectory()){
                item.put("icon",R.drawable.director);
            }else {
                item.put("icon",R.drawable.file);
            }
//            item.put("fileName",files[i].getName());
            item.put("fileName",file.getName());
            listItems.add(item);
        }
        SimpleAdapter simpleAdapter=
                new SimpleAdapter(getApplicationContext(),
                        listItems,R.layout.machine_file_item,new String[]{"icon","fileName"},
                        new int[]{R.id.machine_file_icon, R.id.machine_file_filename});
        mListView.setAdapter(simpleAdapter);
        try {
            mCurrentPath.setText("当前路径为："+mCurrentParent.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}