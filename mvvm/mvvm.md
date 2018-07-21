# MVVM及Databinding使用

## MVVM



## Databinding
###### 双向绑定实现数据和UI绑定的框架，基于MVVM模式实现,去除了冗余代码的基础上对数据和UI层进行解耦
- 通过android:text="@={...}"将数据双向绑定到UI中
- 通过android:enabled="@{...}"控制按钮状态
- 通过android:onClick="@{...}"直接处理用户操作事件
- 编译期同过APT生成辅助工具类，实现数据和UI的动态绑定

#### 使用
###### Java模块 build.gradle 中添加
```java
android {
    ....
    dataBinding {
        enabled = true
    }
}
```
###### 注意：如果app依赖了一个使用 Data Binding 的库，那么app module 的 build.gradle 也必须配置 Data Binding。

#### xml中的使用语法
```markdown
1. 每一个变量variable都是由名称name和类型type组成。name可以在布局文件中使用，也可以通过Setter和Getter在代码中使用。type可以是基本数据类型、集合、适配器、自定义类等，除了基本类型，其他都必须把包名写全
2. 双引号中可以套（Tab键上面，非单引号），单引号内可以套双引号；
3. 不能直接用boolean和int类型的值。而且int值将被当做资源文件。因此要使用其数值，必须转换成字符串，如（boolean同理）：  "@{user.age + （Tab键上面，非单引号）（Tab键上面，非单引号）;}"，或"@{String.valueOf(user.age)}"
4. 颜色必须使用ARGB制，指定其所有的透明度和单色值，以前的#F00和#FF0000无效，且前面不能加“#”
5. "@{user.nickname ?? user.name}"，代表user.nickname为null时显示user.name，否则显示自己。等同于"@{user.nickname == null ? user.name : user.nickname}"
6. 比较运算符，必须使用转义字符：  大于号“>”——&gt;  小于号“<”——&lt;
7. 不能直接使用中文（MD），如： android:text='@{user.male ? "男" : "女"}' 将报错，得用string引用，改成： android:text='@{user.male ? @string/male : @string/female}'
```

#### 简单实现,实现基本数据绑定,按钮事件触发,及线程任务执行
###### 提供一个javabean
###### 注意:set,get方法一定注意规范,自动生成,否则会出现找不到该变量问题
```java
public class Student {
    private String mNo;
    private String mName;

    public String getNo() {
        return mNo;
    }

    public String getName() {
        return mName;
    }

    public Student(String no, String name) {
        mNo = no;
        mName = name;
    }
}
```

###### 布局
```java
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <!-- 变量user， 描述了一个布局中会用到的属性 -->
        <variable name="student" type="com.danny.databinding.model.Student"/>
        <variable name="event" type="com.danny.databinding.model.Event"/>
        <variable name="task" type="com.danny.databinding.model.Task"/>
    </data>

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView android:layout_width="wrap_content"
                  android:layout_height="wrap_content"
                  android:text="@{student.no}"/>

        <!-- 布局文件中的表达式使用 “@{}” 的语法 -->
        <TextView android:layout_width="wrap_content"
                  android:layout_height="wrap_content"
                  android:text="@{student.name}"/>

        <!-- 注意：函数名和监听器对象必须对应 -->
        <!-- 函数调用也可以使用 `.` , 如handler.onClickFriend , 不过已弃用 -->
        <Button
            android:text="点下试试"
            android:onClick="@{event::click}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>

        <Button
            android:text="执行任务"
            android:onClick="@{() -> event.onTaskClick(task)}"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
    </LinearLayout>
</layout>
```
###### Activity代码实现(此处setStudent/setEvent/setTask方法会显示红色,不用管编译运行没有问题)(重启Android Studio可避免该问题)
```java
/**
 * 所有的 set 方法也是根据布局中 variable 名称生成的
 * variableId不能为0, 否则会出现数据无法显示现象
 * binding.setVariable(0, new Student("1111100000","张三"));无数据显示
 * binding.setVariable();// 有多个对象数据时,不要使用该方法,会抛出类型转换异常
 * <variable name="student" type="com.danny.databinding.model.Student"/>
 * 方法名setStudent来源于 name="student"
 * binding.setStudent(new Student("1111100000","张三"));
 */
ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
binding.setEvent(new Event(this));
binding.setTask(new Task(this));
```

[视图跟随数据刷新(参考mvvm)](https://github.com/dannycx/Demo/tree/master/mvvm)

[DataBinding使用详解参考](https://blog.csdn.net/stamsuper/article/details/78574996)
