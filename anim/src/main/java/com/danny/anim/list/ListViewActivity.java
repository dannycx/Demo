package com.danny.anim.list;

import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.danny.anim.R;

import java.util.ArrayList;
import java.util.List;

/**
 * LayoutAnimation的xml实现——layoutAnimation标签
 *      定义一个layoutAnimation的animation文件:anim/layout_animation
 *      在viewGroup类型的控件中，添加android:layoutAnimation=”@anim/layout_animation”
 *
 * GridLayoutAnimation的XML实现——gridLayoutAnimation
 *
 * 总结：
 *      1、LayoutAnimationt和GridLayoutAnimation是在api1时就已经引入进来了，所以不用担心API不支持的问题
 *      2、gridLayoutAnimation与layoutAnimation一样，都只是在viewGroup创建的时候，会对其中的item添加进入动画，在创建完成后，再添加数据将不会再有动画！
 *      3、LayoutAnimationt和GridLayoutAnimation仅支持Animation动画，不支持Animator动画；正是因为它们在api 1就引入进来了，而Animator是在API 11才引入的，所以它们是不可能支持Animator动画的。
 *
 * API11
 *      android:animateLayoutChanges:添加和移除其中控件时自动添加动画,只有默认效果
 *
 *      LayoutTransition
 *          创建实例-创建动画并设置-将LayoutTransaction设置进ViewGroup
 */
public class ListViewActivity extends AppCompatActivity {
    private Context mContext;
//    private ListView mListView;
//    private GridView mGridView;
    private List<String> mList;
    private LinearLayout mLayout;
    private Button mAdd;
    private Button mRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        mContext = this;
//        initListView();
//        initGridView();
        initAnimateLayoutChanges();
    }

    private void initAnimateLayoutChanges() {
        mAdd=findViewById(R.id.list_view_add);
        mRemove=findViewById(R.id.list_view_remove);
        mLayout=findViewById(R.id.list_view_layout);

        initLayoutTransition();

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = new Button(mContext);
                button.setText("添加按钮");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                button.setLayoutParams(params);
                mLayout.addView(button,0);
            }
        });

        mRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLayout.getChildCount()>0){
//                    mLayout.removeViewAt(mLayout.getChildCount()-1);
                    mLayout.removeViewAt(0);
                }
            }
        });
    }

    private void initLayoutTransition() {
        LayoutTransition transition = new LayoutTransition();
        ObjectAnimator animatorOut = ObjectAnimator.ofFloat(null,"rotation",0f,90f,0f);
        ObjectAnimator animatorIn = ObjectAnimator.ofFloat(null,"alpha",0f,1f);
        /**
         * 第一个参数int transitionType：表示当前应用动画的对象范围，取值有：
         *        APPEARING —— 元素在容器中出现时所定义的动画。
         *        DISAPPEARING —— 元素在容器中消失时所定义的动画。
         *        CHANGE_APPEARING —— 由于容器中要显现一个新的元素，其它需要变化的元素所应用的动画
         *        CHANGE_DISAPPEARING —— 当容器中某个元素消失，其它需要变化的元素所应用的动画
         * 第二个参数Animator animator：表示当前所选范围的控件所使用的动画。
         */
        transition.setAnimator(LayoutTransition.DISAPPEARING, animatorOut);
        transition.setAnimator(LayoutTransition.APPEARING, animatorIn);

        /**
         * CHANGE_APPEARING
         * 这里有几点注意事项：
         *       1、LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！也就是说使用ObjectAnimator构造的动画，在这里是不会有效果的！
         *       2、在构造PropertyValuesHolder动画时，”left”、”top”属性的变动是必写的。
         */
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left",0,100,0);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top",1,1);
        PropertyValuesHolder pvhBottom = PropertyValuesHolder.ofInt("bottom",0,0);
        PropertyValuesHolder pvhRight = PropertyValuesHolder.ofInt("right",0,0);
        //必须第一个值与最后一值相同才会有效果,不然没有效果
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("ScaleX",1f,9f,1f);
        ObjectAnimator changeAnimator = ObjectAnimator.ofPropertyValuesHolder(mLayout,pvhLeft,pvhBottom,pvhTop,pvhRight,pvhScaleX);
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,changeAnimator);

        /**
         * LayoutTransition.CHANGE_DISAPPEARING动画
         */
        PropertyValuesHolder outLeft = PropertyValuesHolder.ofInt("left",0,0);
        PropertyValuesHolder outTop = PropertyValuesHolder.ofInt("top",1,1);

        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder mPropertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10);

        ObjectAnimator changeDisAppearing = ObjectAnimator.ofPropertyValuesHolder(this, outLeft,outTop,mPropertyValuesHolder);
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeDisAppearing);


        mLayout.setLayoutTransition(transition);
    }

//    private void initGridView() {
//        initData();
//        mGridView = findViewById(R.id.grid_view);
////        mGridView.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1,mList));
//        mGridView.setAdapter(new GridAdapter());
//
//        Animation animation = AnimationUtils.loadAnimation(mContext,R.anim.slide_in_left);
//        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation);
//        controller.setColumnDelay(0.75f);//设置列动画开始延迟
//        controller.setRowDelay(0.5f);//设置行动画开始延迟
//        controller.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP|GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT);//设置gridview动画的入场方向
//        controller.setDirectionPriority(GridLayoutAnimationController.PRIORITY_NONE);//动画开始优先级
//        mGridView.setLayoutAnimation(controller);
//        mGridView.startLayoutAnimation();
//    }

//    private void initListView() {
//        initData();
//        mListView =findViewById(R.id.list_view);
//        mListView.setAdapter(new ArrayAdapter<>(mContext, android.R.layout.simple_expandable_list_item_1,mList));
//
//        //代码设置通过加载XML动画设置文件来创建一个Animation对象；
//        Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
//        LayoutAnimationController controller = new LayoutAnimationController(animation); //得到一个LayoutAnimationController对象；
//        controller.setOrder(LayoutAnimationController.ORDER_RANDOM);   //设置控件显示的顺序；
//        controller.setDelay(0.3f);   //设置控件显示间隔时间；
//        mListView.setLayoutAnimation(controller);//为ListView设置LayoutAnimationController属性；
//        mListView.startLayoutAnimation();
//    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mList.add("数据"+i);
        }
    }

    class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public String getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setText(getItem(position));
            textView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
            return textView;
        }
    }
}
