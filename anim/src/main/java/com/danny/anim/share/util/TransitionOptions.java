package com.danny.anim.share.util;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

/**
 * 转换选项，使用{@link TransitionOptions＃makeTransitionOptions（Activity，View ...）}建立你的选择
 *
 * Created by danny on 2017/5/8.
 */
public class TransitionOptions {

    private Activity activity;
    private View[] views;
    private ArrayList<ViewAttrs> attrs;

    public TransitionOptions(Activity activity, View[] views) {
        this.activity = activity;
        this.views = views;
    }

    /**
     * 制定过渡动画选项
     *
     * @param activity 包含共享视图的活动
     * @param views    共享视图，必须在两个活动之间包含相同的ID
     * @return 一个新的过渡选项，用于构建我们的过渡动画
     */
    public static TransitionOptions makeTransitionOptions(Activity activity, View... views) {
        return new TransitionOptions(activity, views);
    }

    public void update() {
        if (null == views)
            return;

        attrs = new ArrayList<>();
        for (View v : views) {
            int[] location = new int[2];
//            getLocationOnScreen(int[] outLocation);以out开头的参数为执行方法后将填充该参数
            v.getLocationOnScreen(location);//计算屏幕上此视图的坐标,参数必须是两个整数的数组,方法返回后，数组按顺序包含x和y位置。
            attrs.add(new ViewAttrs(
                    v.getId(),
                    location[0],
                    location[1],
                    v.getWidth(),
                    v.getHeight()
            ));
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public ArrayList<ViewAttrs> getAttrs() {
        return attrs;
    }

    /**
     * 序列化对象
     */
    public static class ViewAttrs implements Parcelable {
        public int id;
        public float startX;
        public float startY;
        public float width;
        public float height;

        public ViewAttrs(int id, float startX, float startY, float width, float height) {
            this.id = id;//view id 用于获取B中的对应的View
            this.startX = startX;// view 在AActivity x坐标
            this.startY = startY;// view 在AActivity y坐标
            this.width = width;// view 在AActivity宽
            this.height = height;// view 在AActivity高
        }

        // Parcelable
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeFloat(this.startX);
            dest.writeFloat(this.startY);
            dest.writeFloat(this.width);
            dest.writeFloat(this.height);
        }

        protected ViewAttrs(Parcel in) {
            this.id = in.readInt();
            this.startX = in.readFloat();
            this.startY = in.readFloat();
            this.width = in.readFloat();
            this.height = in.readFloat();
        }

        public static final Creator<ViewAttrs> CREATOR = new Creator<ViewAttrs>() {
            @Override
            public ViewAttrs createFromParcel(Parcel source) {
                return new ViewAttrs(source);
            }

            @Override
            public ViewAttrs[] newArray(int size) {
                return new ViewAttrs[size];
            }
        };
    }
}
