# Android 动画
- 帧动画（ＡｎｉｍａｔｉｏｎＤｒａｗａｂｌｅ）：一张张图片组成，类似放电影效果，需要大量图片资源
- 补间动画（不改变控件实际位置）：旋转动画(RotateAnimation)、透明动画(AlphaAnimation)、缩放动画(ScaleAnimation)、平移动画(TranslateAnimation)
- 属性动画（改变控件实际位置）：ValueAnimator、ObjectAnimator
- 布局动画（）：
- 共享元素（场景动画T）：

## 帧动画
###### 容易引起OOM，避免使用过多大尺寸图片
```java
帧布局文件(drawable下)-frame.xml
<animation-list android:oneshot="true">
    <item android:drawable="@drawable/theme_translation_turn_day_01" android:duration="110"/>
    <item android:drawable="@drawable/theme_translation_turn_day_02" android:duration="110"/>
    <item android:drawable="@drawable/theme_translation_turn_day_03" android:duration="110"/>
    <item android:drawable="@drawable/theme_translation_turn_day_04" android:duration="110"/>
    <item android:drawable="@drawable/theme_translation_turn_day_05" android:duration="110"/>
    <item android:drawable="@drawable/theme_translation_turn_day_06" android:duration="110"/>
</animation-list>
view.setBackgroundResource(R.drawable.frame);
AnimationDrawable ad = (AnimationDrawable) view.getBackground();
ad.start();
```

## 补间动画
###### 补间动画虽能对控件做动画，但并没有改变控件内部的属性值 

![](https://github.com/dannycx/knowledge/blob/master/image/view_animation.png)  

#### 透明动画
```java
java代码
AlphaAnimation alpha = new AlphaAnimation(0,1);
alpha.setDuration(1000);//时间
view.startAnimation(alpha);

xml文件 alpha.xml
<?xml version="1.0" encoding="utf-8"?>
<alpha xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromAlpha="0"
    android:toAlpha="1"
    android:duration="3000"/>
view.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.alpha));
```

#### 旋转动画
```java
java代码
// new RotateAnimation(0,360,100,50);//从0-360度旋转,以（100，50）为中心点
// new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//从0-360度旋转,以自身中心点为中心点
RotateAnimation rotate = new RotateAnimation(0,360);//从0-360度旋转
rotate.setDuration(1000);
rotate.setFillAfter(true);//停在结束位置
view.startAnimation(rotate);

xml文件 rotate.xml
<?xml version="1.0" encoding="utf-8"?>
<rotate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromDegrees="0"
    android:toDegrees="360"
    android:pivotX="50%"
    android:pivotY="50%"
    android:duration="3000"/>
view.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.rotate));
```

#### 缩放动画
```java
java代码
// new ScaleAnimation(0, 1, 0, 1, 100, 50);//相对(100，50)点缩放
// new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);//相对自身中心点缩放一倍
ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1);//放大一倍
scale.setDuration(1000);
scale.setFillAfter(true);//停在结束位置
view.startAnimation(scale);

xml文件 scale.xml
<?xml version="1.0" encoding="utf-8"?>
<scale
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXScale="0"
    android:toXScale="1"
    android:fromYScale="0"
    android:toYScale="1"
    android:pivotX="50%"
    android:pivotY="50%"
    android:duration="3000"/>
view.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.scale));
```

#### 平移动画
```java
java代码
TranslateAnimation translate = new TranslateAnimation(0, 200, 0, 200);//相对自身位置右移200，下移200
translate.setDuration(1000);
translate.setFillAfter(true);//停在结束位置
view.startAnimation(translate);

xml文件 translate.xml
<?xml version="1.0" encoding="utf-8"?>
<translate
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXDelta="0"
    android:toXDelta="200"
    android:fromYDelta="0"
    android:toYDelta="200"
    android:duration="3000"/>
view.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.translate));
```

#### 组合动画
```java
java代码
AnimationSet set = new AnimationSet(true);//是否使用插补器
set.setDuration(2000);

AlphaAnimation alpha = new AlphaAnimation(0,1);
alpha.setDuration(1000);//时间
set.addAnimation(alpha);

TranslateAnimation translate = new TranslateAnimation(200, 0, 200, 0);//相对自身位置右移200，下移200
translate.setDuration(1000);
set.addAnimation(translate);
        
view.startAnimation(set);

xml文件 set.xml
<?xml version="1.0" encoding="utf-8"?>
<set
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="3000"
    android:shareInterpolator="true">

    <alpha
        android:fromAlpha="0"
        android:toAlpha="1"/>

    <translate
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:fromXDelta="200"
        android:fromYDelta="200"
        android:toXDelta="0"
        android:toYDelta="0"/>
</set>
view.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.set));
```

#### 动画监听
```java
TranslateAnimation translate = new TranslateAnimation(200, 0, 200, 0);//相对自身位置右移200，下移200
translate.setDuration(1000);
translate.setAnimationListener(new Animation.AnimationListener() {
    @Override
    public void onAnimationStart(Animation animation) {
       //开始
    }

    @Override
    public void onAnimationEnd(Animation animation) {
       //结束
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
       //重复
    }
});
```

####　自定义补间动画
- 继承自Animation，重写ｉｎｉｔialize()和applyTransformation（）
- ｉｎｉｔialize()初始化工作
- applyTransformation（）矩阵变换工作（Camera简化矩阵变换过程）



## 属性动画
###### API11出现，ValueAnimator, ObjectAnimator, AnimatorSet,效果是在一个时间间隔内完成对象从一个属性值到另一个属性值的改变。可使用nineoldandroids兼容之前版本
###### ofObject来做动画的时候，都必须调用setEvaluator显示设置Evaluator，因为系统根本是无法知道，你动画的中间值Object真正是什么类型的。


#### ValueAnimator
###### ValueAnimator对指定值区间做动画运算，我们通过对运算过程做监听来自己操作控件。
- ValueAnimator只负责对指定的数字区间进行动画运算
- 我们需要对运算过程进行监听，然后自己对控件做动画操作

##### 使用
- 创建实例
- 添加监听

#### ObjectAnimator
- 要使用ObjectAnimator来构造对画，要操作的控件中，必须存在对应的属性的set方法
- setter 方法的命名必须以骆驼拼写法命名，即set后每个单词首字母大写，其余字母小写，即类似于setPropertyName所对应的属性为propertyName 
- 当且仅当动画的只有一个过渡值时，系统才会调用对应属性的get函数来得到动画的初始值。

#### PropertyValuesHolder
```markdown
其中保存了动画过程中所需要操作的属性和对应的值。我们通过ofFloat(Object target, String propertyName, float… values)构造的动画，ofFloat()的内部实现其实就是将传进来的参数封装成PropertyValuesHolder实例来保存动画状态。在封装成PropertyValuesHolder实例以后，后期的各种操作也是以PropertyValuesHolder为主的。 
```

#### 插值器
###### 可以通过重写加速器改变数值进度来改变数值位置

#### 计数器
######　转换器，他能把小数进度转换成对应的数值位置.可以通过改变Evaluator中进度所对应的数值来改变数值位置。
- ofInt(0,400)表示指定动画的数字区间，是从0运动到400；
- 加速器：上面我们讲了，在动画开始后，通过加速器会返回当前动画进度所对应的数字进度，但这个数字进度是百分制的，以小数表示，如0.2
- Evaluator:我们知道我们通过监听器拿到的是当前动画所对应的具体数值，而不是百分制的进度。那么就必须有一个地方会根据当前的数字进度，将其转化为对应的数值，这个地方就是Evaluator；Evaluator就是将从加速器返回的数字进度转成对应的数字值。所以上部分中，我们讲到的公式：
- 监听器：我们通过在AnimatorUpdateListener监听器使用animation.getAnimatedValue()函数拿到Evaluator中返回的数字值。
讲了这么多，Evaluator其实就是一个转换器，他能把小数进度转换成对应的数值位置



## 布局动画

#### 该布局下的子view，上个显示后下个显示
```java
LinearLayout root = View.inflate(this, R.layout.root, null);
ScaleAnimation scale = new ScaleAnimation(0,1,0,1);
scale.setDuration(3000);
LayoutAnimationController lac = new LayoutAnimationController(scale,0.5f);
lac.setOrder(LayoutAnimationController.ORDER_REVERSE);//子view顺序，从下往上
root.setLayoutAnimation(lac);
```

## 布局内容动画
###### 添加并移除控件
```java
private void addBtn(){
    Button btn = new Button(this);
    btn.setText("add");
    root.addView(btn);
    root.setLayoutTransition(Transition);
    btn.setOnClickListener(new OnClistener(){
       @Override
       public void onClick(View view){
            root.removeView(view);
       }
    });
}
```

## 列表添加布局动画
```java
private ListView mListView;
private ArrayAdapter<String> mAdapter;

mAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new String[]{"123","456"});
mListView.setAdapter(mAdapter);

ScaleAnimation scale = new ScaleAnimation(0,1,0,1);
scale.setDuration(3000);
LayoutAnimationController lac = new LayoutAnimationController(scale,0.5f);
mListView.setLayoutAnimation(lac);
```

## 资源文件配置布局动画
```java
动画scale.xml-anim
<?xml version="1.0" encoding="utf-8"?>
<scale
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:fromXScale="0"
    android:toXScale="1"
    android:fromYScale="0"
    android:toYScale="1"
    android:pivotX="50%"
    android:pivotY="50%"
    android:duration="3000"/>
    
控制LayoutAnimationController
list_anim.xml-anim
<?xml version="1.0" encoding="utf-8"?>
<layoutAnimation
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:animation="@anim/scale"
    android:delay="0.5"/>
    
<ListView 
    android:id="@+id/list_view"
    android:width="match_parent"
    android:height="match_parent"
    android:layoutAnimation="@anim/list_anim"/>
```
