package com.danny.anim.property.object;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.danny.anim.R;
import com.danny.anim.property.value.CharEvaluator;

/**
 * ofFloat():
 * 第一个参数用于指定这个动画要操作的是哪个控件
 * 第二个参数用于指定这个动画要操作这个控件的哪个属性
 * 第三个参数是可变长参数，这个就跟ValueAnimator中的可变长参数的意义一样了，就是指这个属性值是从哪变到哪
 */
public class ObjectActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ObjectActivity";

    private TextView mObjectAnimator;
    private ImageView mPhone;
    private Button mStop;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private PointView mPointView;
    private CharacterTextView mCharacter;

    private ObjectAnimator mAnimator;

    private PropertyValuesHolder mLeftAndRightFrameHolder;
    private PropertyValuesHolder mScaleXFrameHolder;
    private PropertyValuesHolder mScaleYFrameHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        initView();
        setListener();

    }

    private void initView() {
        mObjectAnimator = findViewById(R.id.object_tv);
        mStop = findViewById(R.id.object_stop);
        mButton1 = findViewById(R.id.object_1);
        mButton2 = findViewById(R.id.object_2);
        mButton3 = findViewById(R.id.object_3);
        mPointView = findViewById(R.id.object_point_view);
        mCharacter = findViewById(R.id.object_character);
        mPhone = findViewById(R.id.object_phone);
    }

    private void setListener() {
        mStop.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.object_3:
                initAnimator3();
                break;
            case R.id.object_2:
                initAnimator2();
                break;
            case R.id.object_1:
                initAnimator1();
                break;
            case R.id.object_stop:
                stop();
                break;
        }
    }

    /**
     * 电话震动
     */
    private void initAnimator3() {
        leftAndRight();// 左右抖动动画
        scaleX();// scaleX放大1.1倍
        scaleY();//scaleY放大1.1倍

        mAnimator = ObjectAnimator.ofPropertyValuesHolder(mPhone, mLeftAndRightFrameHolder,mScaleXFrameHolder,mScaleYFrameHolder);
        mAnimator.setDuration(5000);
        mAnimator.start();
    }

    /**
     * y轴放大
     */
    private void scaleY() {
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        mScaleYFrameHolder = PropertyValuesHolder.ofKeyframe("ScaleY",scaleYframe0,scaleYframe1,scaleYframe2,scaleYframe3,scaleYframe4,scaleYframe5,scaleYframe6,scaleYframe7,scaleYframe8,scaleYframe9,scaleYframe10);
    }

    /**
     * x轴放大
     */
    private void scaleX() {
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        mScaleXFrameHolder = PropertyValuesHolder.ofKeyframe("ScaleX",scaleXframe0,scaleXframe1,scaleXframe2,scaleXframe3,scaleXframe4,scaleXframe5,scaleXframe6,scaleXframe7,scaleXframe8,scaleXframe9,scaleXframe10);
    }

    /**
     * 左右抖动动画
     */
    private void leftAndRight() {
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
        mLeftAndRightFrameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);
    }

    /**
     * PropertyValuesHolder
     *      ofFloat(String propertyName, float... values)
     *      ofInt(String propertyName, int... values)
     *          propertyName：表示ObjectAnimator需要操作的属性名。即ObjectAnimator需要通过反射查找对应属性的setProperty()函数的那个property.
     *          values：属性所对应的参数，同样是可变长参数，可以指定多个，还记得我们在ObjectAnimator中讲过，如果只指定了一个，那么ObjectAnimator会通过查找getProperty()方法来获得初始值。
     *      ofObject(String propertyName, TypeEvaluator evaluator,Object... values)
     *          propertyName:ObjectAnimator动画操作的属性名;
     *          evaluator:Evaluator实例，Evaluator是将当前动画进度计算出当前值的类，可以使用系统自带的IntEvaluator、FloatEvaluator也可以自定义
     *          values：可变长参数，表示操作动画属性的值
     *      ofKeyframe(String propertyName, Keyframe... values)
     *          propertyName：动画所要操作的属性名
     *          values：Keyframe的列表，PropertyValuesHolder会根据每个Keyframe的设定，定时将指定的值输出给动画。
     *          KeyFrame:关键帧(至少要有两个帧才行)
     *              ofFloat(float fraction, float value)
     *                  fraction：表示当前的显示进度，即从加速器中getInterpolation()函数的返回值；
     *                  value：表示当前应该在的位置
     *                  Keyframe.ofFloat(0, 0):表示动画进度为0时，动画所在的数值位置为0；
     *                  Keyframe.ofFloat(0.25f, -20f):表示动画进度为25%时，动画所在的数值位置为-20；
     *                  Keyframe.ofFloat(1f,0):表示动画结束时，动画所在的数值位置为0；
     *              如果去掉第0帧，将以第一个关键帧为起始位置
     *              如果去掉结束帧，将以最后一个关键帧为结束位置
     *              使用Keyframe来构建动画，至少要有两个或两个以上帧
     *
     * ObjectAnimator
     *      ofPropertyValuesHolder(Object target,PropertyValuesHolder... values)
     *          target：指需要执行动画的控件
     *          values：是一个可变长参数，可以传进去多个PropertyValuesHolder实例，由于每个PropertyValuesHolder实例都会针对一个属性做动画，所以如果传进去多个PropertyValuesHolder实例，将会对控件的多个属性同时做动画操作。
     */
    private void initAnimator2() {
        //ofFloat,ofInt
//        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
//        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
//        mAnimator = ObjectAnimator.ofPropertyValuesHolder(mObjectAnimator, rotationHolder, colorHolder);

        //ofObject
        PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("CharText", new CharEvaluator(), new Character('A'), new Character('Z'));
        mAnimator = ObjectAnimator.ofPropertyValuesHolder(mCharacter, charHolder);

        //ofKeyframe
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2);
        mAnimator = ObjectAnimator.ofPropertyValuesHolder(mObjectAnimator, frameHolder);

        mAnimator.setDuration(3000);
//        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.start();
    }

    /**
     * 初识Object属性动画
     * rotation、rotationX、rotationY:旋转
     * alpha:透明
     * translationX、translationY：平移
     * scaleX、scaleY：缩放
     * <p>
     * 多个值会从开始值变化
     * 单个值提供get()会从get()返回值开始变化,未提供get()会从0开始变化,出现警告
     * Method getPointRadius() with type null not found on target class class com.danny.anim.property.object.PointView
     * <p>
     * 颜色计数器
     */
    private void initAnimator1() {
//        mAnimator = ObjectAnimator.ofFloat(mObjectAnimator,"rotation", 0,180,0);
//        mAnimator = ObjectAnimator.ofInt(mPointView, "pointRadius", 300);// 自定义属性
        mAnimator = ObjectAnimator.ofInt(mObjectAnimator, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        mAnimator.setDuration(3000);
        mAnimator.start();
    }

    /**
     * 停止动画
     */
    private void stop() {
        mAnimator.cancel();
    }
}
