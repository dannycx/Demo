package com.danny.listview.recycler;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.danny.listview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * listview条目复用
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private List<Eat> mList;
    private ListView mListView;
    private MyAdapter mAdapter;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initData();
        initView();
        initAdapter();
    }

    private void initData() {
        mList=new ArrayList<>();
        for (int i=0;i<50;i++){
            Eat eat=new Eat();
            Map<Integer,String> map=new HashMap<>();
            map.put(0,"吃饭");
            eat.mMap=map;
            eat.title="吃"+i;
            eat.content="吃啥子幺"+i;
            mList.add(eat);
        }
    }

    private void initView() {
        mListView=findViewById(R.id.list_view);
    }

    private void initAdapter() {
        mAdapter=new MyAdapter(mList);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnButtonClick(new MyAdapter.OnButtonClick() {
            @Override
            public void onButton(int flag, int position) {
                if (flag==0){
                    show(position);
                }else if (flag==1){
                    //扩展
                }

            }
        });
    }

    private void show(final int position) {
        mDialog=new AlertDialog.Builder(mContext)
                .setTitle("吃饭吗?")
                .setMessage("你要吃饭吗?")
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<Integer,String> map=mList.get(position).mMap;
                        map.put(1,"好的");
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).setNegativeButton("等会", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .create();
        mDialog.show();
    }
}
