package com.danny.anim;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.danny.anim.frame.FrameActivity;
import com.danny.anim.list.List2Activity;
import com.danny.anim.list.ListViewActivity;
import com.danny.anim.nineold.NineOldActivity;
import com.danny.anim.property.MultipleActivity;
import com.danny.anim.property.object.ObjectActivity;
import com.danny.anim.property.set.SetActivity;
import com.danny.anim.property.value.PropertyActivity;
import com.danny.anim.property.xml.XMLActivity;
import com.danny.anim.share.AActivity;
import com.danny.anim.share.ShareElementActivity;
import com.danny.anim.value.AlphaActivity;
import com.danny.anim.value.CustomActivity;
import com.danny.anim.value.RotateActivity;
import com.danny.anim.value.ScaleActivity;
import com.danny.anim.value.TranslateActivity;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button mFrame;
    private Button mScale;
    private Button mTranslate;
    private Button mAlpha;
    private Button mRotate;
    private Button mCustom;
    private Button mShareElement;
    private Button mTransition;
    private Button mNineOld;
    private Button mProperty;
    private Button mObject;
    private Button mSet;
    private Button mXml;
    private Button mMultiple;
    private Button mListView;
    private Button mListView2;
    private Button m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext = this;
        initView();
    }

    private void initView() {
        mFrame = findViewById(R.id.test_anim_frame);
        mFrame.setOnClickListener(this);
        mTranslate = findViewById(R.id.test_anim_translate);
        mTranslate.setOnClickListener(this);
        mScale = findViewById(R.id.test_anim_scale);
        mScale.setOnClickListener(this);
        mAlpha = findViewById(R.id.test_anim_alpha);
        mAlpha.setOnClickListener(this);
        mRotate = findViewById(R.id.test_anim_rotate);
        mRotate.setOnClickListener(this);
        mCustom = findViewById(R.id.test_anim_custom);
        mCustom.setOnClickListener(this);
        mShareElement = findViewById(R.id.test_anim_share_element);
        mShareElement.setOnClickListener(this);
        mTransition = findViewById(R.id.test_anim_transition);
        mTransition.setOnClickListener(this);
        mNineOld = findViewById(R.id.test_anim_nine_old);
        mNineOld.setOnClickListener(this);
        mProperty = findViewById(R.id.test_anim_property);
        mProperty.setOnClickListener(this);
        mObject = findViewById(R.id.test_anim_object);
        mObject.setOnClickListener(this);
        mSet = findViewById(R.id.test_anim_set);
        mSet.setOnClickListener(this);
        mXml = findViewById(R.id.test_anim_xml);
        mXml.setOnClickListener(this);
        mMultiple = findViewById(R.id.test_anim_multiple);
        mMultiple.setOnClickListener(this);
        mListView = findViewById(R.id.test_anim_list_view);
        mListView.setOnClickListener(this);
        mListView2 = findViewById(R.id.test_anim_list_view_2);
        mListView2.setOnClickListener(this);
        m = findViewById(R.id.test_anim_);
        m.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_anim_frame:
                startActivity(new Intent(mContext, FrameActivity.class));
                break;
            case R.id.test_anim_translate:
                startActivity(new Intent(mContext, TranslateActivity.class));
                break;
            case R.id.test_anim_scale:
                startActivity(new Intent(mContext, ScaleActivity.class));
                break;
            case R.id.test_anim_alpha:
                startActivity(new Intent(mContext, AlphaActivity.class));
                break;
            case R.id.test_anim_rotate:
                startActivity(new Intent(mContext, RotateActivity.class));
                break;
            case R.id.test_anim_custom:
                startActivity(new Intent(mContext, CustomActivity.class));
                break;
            case R.id.test_anim_share_element:
                startActivity(new Intent(mContext, ShareElementActivity.class));
                break;
            case R.id.test_anim_transition:
                startActivity(new Intent(mContext, AActivity.class));
                break;
            case R.id.test_anim_nine_old:
                startActivity(new Intent(mContext, NineOldActivity.class));
                break;
            case R.id.test_anim_property:
                startActivity(new Intent(mContext, PropertyActivity.class));
                break;
            case R.id.test_anim_object:
                startActivity(new Intent(mContext, ObjectActivity.class));
                break;
            case R.id.test_anim_set:
                startActivity(new Intent(mContext, SetActivity.class));
                break;
            case R.id.test_anim_xml:
                startActivity(new Intent(mContext, XMLActivity.class));
                break;
            case R.id.test_anim_multiple:
                startActivity(new Intent(mContext, MultipleActivity.class));
                break;
            case R.id.test_anim_list_view:
                startActivity(new Intent(mContext, ListViewActivity.class));
                break;
            case R.id.test_anim_list_view_2:
                startActivity(new Intent(mContext, List2Activity.class));
                break;
            case R.id.test_anim_:
                startActivity(new Intent(mContext, SkewActivity.class));
                break;
            default:
                break;
        }
    }
}
