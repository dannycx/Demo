package com.danny.anim.property;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.danny.anim.R;

/**
 * Math中根据度数得到弧度值的函数:double toRadians(double angdeg)
 */
public class MultipleActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MultipleActivity";

    private ImageButton mMenu;
    private ImageButton mItem1;
    private ImageButton mItem2;
    private ImageButton mItem3;
    private ImageButton mItem4;
    private ImageButton mItem5;

    private boolean mIsMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple);

        initView();
        setListener();
    }

    private void setListener() {
        mMenu.setOnClickListener(this);
        mItem1.setOnClickListener(this);
        mItem2.setOnClickListener(this);
        mItem3.setOnClickListener(this);
        mItem4.setOnClickListener(this);
        mItem5.setOnClickListener(this);
    }

    private void initView() {
        mMenu=findViewById(R.id.multiple_btn);
        mItem1=findViewById(R.id.multiple_msg);
        mItem2=findViewById(R.id.multiple_sina);
        mItem3=findViewById(R.id.multiple_ali_pay);
        mItem4=findViewById(R.id.multiple_github);
        mItem5=findViewById(R.id.multiple_wechat);
    }

    @Override
    public void onClick(View v) {
        if (v == mMenu) {
            if (!mIsMenuOpen) {
                open();
            } else {
                close();
            }
        } else {
            close();
            switch (v.getId()){
                case R.id.multiple_msg:
                    msg();
                    break;
                case R.id.multiple_sina:
                    sina();
                    break;
                case R.id.multiple_ali_pay:
                    alipay();
                    break;
                case R.id.multiple_github:
                    github();
                    break;
                case R.id.multiple_wechat:
                    wechat();
                    break;
            }
        }
    }

    /**
     * 开启
     */
    private void open() {
        mIsMenuOpen = true;
        doAnimateOpen(mItem1, 0, 5, 300);
        doAnimateOpen(mItem2, 1, 5, 300);
        doAnimateOpen(mItem3, 2, 5, 300);
        doAnimateOpen(mItem4, 3, 5, 300);
        doAnimateOpen(mItem5, 4, 5, 300);
    }

    /**
     * 关闭
     */
    private void close() {
        mIsMenuOpen = false;
        doAnimateClose(mItem1, 0, 5, 300);
        doAnimateClose(mItem2, 1, 5, 300);
        doAnimateClose(mItem3, 2, 5, 300);
        doAnimateClose(mItem4, 3, 5, 300);
        doAnimateClose(mItem5, 4, 5, 300);
    }

    /**
     * 开启菜单
     *
     * @param view 控件
     * @param index 当前控件索引,从0开始
     * @param total 控件总数
     * @param radius 摆放半径
     */
    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }

        // 计算弧度
        double degree = Math.toRadians(90)/(total - 1) * index;//在90度角中按圆弧均匀摆放五个控件
        int translationX = -(int) (radius * Math.sin(degree));//x正弦函数
        int translationY = -(int) (radius * Math.cos(degree));//y余弦函数

        //包含平移、缩放和透明度动画
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));

        //动画周期为500ms
        set.setDuration(1 * 300).start();
    }

    /**
     * 关闭菜单
     *
     * @param view 控件
     * @param index 当前控件索引,从0开始
     * @param total 控件总数
     * @param radius 摆放半径
     */
    private void doAnimateClose(final View view, int index, int total,
                                int radius) {
        //Math.toRadians(90) == Math.PI/2
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();

        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        set.setDuration(1 * 300).start();

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                }
            }
        }, 300);
    }

    private void msg() {
        Toast.makeText(this, "你点击了" + "msg", Toast.LENGTH_SHORT).show();
    }

    private void sina() {
        Toast.makeText(this, "你点击了" + "sina", Toast.LENGTH_SHORT).show();
    }

    private void alipay() {
        Toast.makeText(this, "你点击了" + "alipay", Toast.LENGTH_SHORT).show();
    }

    private void github() {
        Toast.makeText(this, "你点击了" + "github", Toast.LENGTH_SHORT).show();
    }

    private void wechat() {
        Toast.makeText(this, "你点击了" + "wechat", Toast.LENGTH_SHORT).show();
    }
}
