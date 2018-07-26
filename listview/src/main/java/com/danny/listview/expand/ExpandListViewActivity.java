package com.danny.listview.expand;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.listview.R;

public class ExpandListViewActivity extends AppCompatActivity {
    private Context mContext;
    private ExpandableListView mExpandableListView;
    private MyExpandAdapter mAdapter;
    private String[] mParent;
    private String[][] mChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_list_view);
        mContext=this;
        initView();
        initData();
    }

    private void initData() {
        mParent=new String[]{
                "服务电话",
                "报警电话",
                "通讯电话"
        };
        mChild=new String[][]{
                {"中国联通10010","中国移动10086","医院120"},
                {"火警119","匪警110"},
                {"张三15342442244","李四15423234553","王五15625256374","黑八15263647855"}
        };
        mAdapter=new MyExpandAdapter();
        mExpandableListView.setAdapter(mAdapter);
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(mContext,mChild[groupPosition][childPosition],Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initView() {
        mExpandableListView=findViewById(R.id.expand_list);
    }

    class MyExpandAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return mParent.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mChild[groupPosition].length;
        }

        @Override
        public String getGroup(int groupPosition) {
            return mParent[groupPosition];
        }

        @Override
        public String getChild(int groupPosition, int childPosition) {
            return mChild[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            TextView textView=new TextView(mContext);
            textView.setText(getGroup(groupPosition));
            return textView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            TextView textView=new TextView(mContext);
            textView.setText(getChild(groupPosition,childPosition));
            return textView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
