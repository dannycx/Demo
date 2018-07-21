package com.danny.imageview.picasso;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.danny.imageview.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Picasso使用简单易用的接口，并有一个实现类Picasso，一个完整的图片加载请求至少需要三个参数：
 *      with(Context context)：上下文；
 *      load(String path)：加载图片的地址；
 *      into(ImageView target)：图片展示的ImageView。
 *
 *      build.gradle配置：compile 'com.squareup.picasso:picasso:2.5.2'
 *      混淆配置：-dontwarn com.squareup.okhttp.**
 *      网络配置：<uses-permission android:name="android.permission.INTERNET" />
 *
 * 在图片请求加载时添加标记：
         Picasso.with(mActivity)
         .load(Constant.picUrl)
         .tag("mark")
         .into(mIvNetworkPictures);

 * ListView实现滑动监听OnScrollListener：
             @Override
             public void onScrollStateChanged(AbsListView view, int scrollState) {
             Picasso picasso = Picasso.with(mActivity);
             if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
             picasso.resumeTag("mark");
             } else {
             picasso.pauseTag("mark");
             }
             }

 * 在页面进行跳转或者退出时取消请求：
             @Override
             protected void onDestroy() {
             super.onDestroy();
             Picasso.with(mActivity)
             .cancelTag("mark");
             }
 * 注意：如果Tag状态为pause或者resume的时候，Picasso会对Tag持有一个引用，如果此时用户退出了当前Activity，垃圾回收机制进行回收的时候就可能会出现内存泄露，所以需要在onDestroy()方法中进行相应处理。
 *
 * 缓存机制：
             Picasso默认的缓存分配大小特点:
                LRU缓存占应用程序可用内存的15%；
                本地缓存占硬盘空间的2%，但不超过50M且不小于5M(前提是这种情况只在4.0以上有效果或者你能像OKHttp那样提供一个本地缓存库来支持全平台)；
                Picasso默认开启3个线程来进行本地与网络之间的访问；
                Picasso加载图片顺序为内存–>本地–>网络。
 *
 * 内存策略：
             MemoryPolicy负责管理内存缓存，可能有的时候你不想让Picasso去内存中进行读取而跳过此步骤，
             此时你可以在进行网络请求的时候调用memoryPolicy(MemoryPolicy policy, MemoryPolicy... additional)方法。
             MemoryPolicy是一个枚举类型，只有两个值：NO_CACHE和NO_STORE。
                  NO_CACHE：让Picasso跳过从内存中读取图片这一操作；
                  NO_STORE：如果你的图片只加载一次就不在使用了就可以设置该值，这样的话Picasso就不会在内存及本地进行缓存了。

             注意：调用memoryPolicy(MemoryPolicy.NO_CACHE)虽然能避免Picasso从内存中读取资源，但是并不能避免从本地读取资源。
             代码示例：
             Picasso.with(mActivity).load(Constant.picUrl).memoryPolicy(MemoryPolicy.NO_CACHE).into(mIvNetworkPictures);
             Picasso.with(mActivity).load(Constant.picUrl).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(mIvNetworkPictures);
 *
 *网络策略：
             NetworkPolicy负责管理本地缓存，它也是一个枚举类型。
                     NO_CACHE：跳过从本地读取资源这一过程；
                     NO_STORE：不进行本地图片缓存；
                     OFFLINE：加载图片的时候只从本地读取，除非网络正常且本地找不到资源的情况下。

             示例代码：
             Picasso.with(mActivity).load(Constant.picUrl).networkPolicy(NetworkPolicy.NO_CACHE).into(mIvNetworkPictures);
 *
 * 缓存指示器，查看图片来源于何处：
             当我们想知道所加载的图片来源于何处，是内存、本地还是从网络加载的时候，只需要在请求的时候调用.setIndicatorsEnabled(true)方法就好了。
             Picasso.with(mActivity).setIndicatorsEnabled(true);
             这样每张图片在显示的时候，左上角都会有一个小标记，分别是蓝色、绿色、红色三种颜色。
                 蓝色：从内存中获取，性能最佳；
                 绿色：从本地获取，性能一般；
                 红色：从网络加载，性能最差。
 *
 * 创建一个Picasso：
                  Picasso picasso = new Picasso.Builder(mActivity).build();
                  标准创建方式：Picasso picasso = Picasso.with(mActivity);

 * 自定义创建Picasso实例：
                         Picasso.Builder pb = new Picasso.Builder(mActivity);
                         Picasso picasso = pb.build();
                         picasso.load(Constant.picUrl).into(mIvNetworkPictures);
 *
 * 将自定义Picasso变成全局使用：
 *      在应用启动的时候调用Picasso.setSingletonInstance(picasso)方法，这样的话后面所有调用Picasso.with(context)返回的都是我们自定义的Picasso。
             Picasso.Builder pb = new Picasso.Builder(mActivity);
             Picasso picasso = pb.build();
             Picasso.setSingletonInstance(picasso);
 *
 * Picasso默认的缓存路径位于data/data/packageName/cache/picasso-cache/下。开发过程中我们难免会遇到一些需求，需要我们去修改图片的缓存路径。
 *      我们注意到Picasso底层其实是使用OkHttp去下载图片，同时在设置Picasso的时候有一个.downloader(Downloader downloader)方法，我们可以传递进去一个OkHttpDownloader(...)。
 *
 * 自定义缓存
        方式一
             OkHttp依赖
                    compile 'com.squareup.okhttp:okhttp:2.4.0'
 *           自定义缓存目录
                    FileUtil.getCachePath(mContext)

             自定义创建Picasso实例并应用到全局

             Picasso.Builder pb = new Picasso.Builder(this);
             Picasso picasso = pb
             .downloader(new OkHttpDownloader(new File(FileUtil.getCachePath(this))))
             .build();
             Picasso.setSingletonInstance(picasso);
 *      方式二
 *          okHttp3不支持OkHttpDownloader
 *          依赖:compile 'com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0'

 *          自定义缓存目录和缓存大小
             // 设置缓存目录
             File directory = new File(FileUtil.getCachePath(this));
             if (!directory.exists()) {
             directory.mkdirs();
             }
             // 设置缓存大小为运行时缓存的八分之一
             long maxSize = Runtime.getRuntime().maxMemory() / 8;

             自定义创建Picasso实例并应用到全局
             OkHttpClient client = new OkHttpClient.Builder().cache(new Cache(directory, maxSize)).build();
             Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(client)).build();
             Picasso.setSingletonInstance(picasso);
 *
 *
 *
 */
public class PicassoActivity extends AppCompatActivity {
    private static final String TAG = "PicassoActivity";
    private Context mContext;

    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso);
        mContext=this;
        mRecyclerView=findViewById(R.id.picasso_recycler);

        mAdapter=new MyAdapter();
        RecyclerView.LayoutManager manager = new GridLayoutManager(mContext,2, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picasso_recycler_item,parent,false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            /**
             * .noFade()：Picasso的默认图片加载方式有一个淡入的效果，如果调用了noFade()方法后加载的图片将直接显示在ImageView上。
             * .resize(int targetWidth, int targetHeight)：如果图片很大或者想自定义图片的显示样式时，可以使用此方法来裁剪图片尺寸。
             * .noPlaceholder()：
             * .onlyScaleDown()：原图是比重新resize的新图规格大的时候，我们就可以调用onlyScaleDown()来直接进行展示而不再重新计算，缩短图片的加载计算时间。
             * .centerInside()：图片会被完整的展示，可能图片不会填充满ImageView，也有可能会被拉伸或者挤压，一般是等比例缩小。
             * .centerCrop()：图片会被裁剪，但是图片质量没有什么区别，等比例放大。
             * .fit()：Picasso会对图片的大小及ImageView进行测量，计算出最佳的大小及最佳的图片质量来进行图片展示，减少内存的消耗并对视图没有影响。
             * .priority()：如果一个视图中顶部图片较大而底部图片较小，因为Picasso是异步加载，所以小图会先加载出来。但是对于用户来说或许更希望看到的是上面的图片先被加载而底部的图片后被加载，此时可使用此方法来设置图片加载的优先级。
             *              注意：设置优先级并不能保证图片就一定会被优先加载，只是会偏向倾斜于先加载。
             * .tag()：为请求添加标记提升用户体验。比如在列表ListView的Item中加载了图片，当用户在快速滑动的时候可以设置停止请求，在滑动停止时再去加载请求，退出当前页面时取消请求。
             *          Picasso提供了三种设置Tag的方式：
             *              暂停标记：pauseTag()
             *              可见标记：resumeTag()
             *              取消标记：cancelTag()
             * .fetch()：该方法会在后台异步加载一张图片，但是不会展示在ImageView上，也不会返回Bitmap，这个方法只是为了将获取到的资源加载到本地和内存当中，为后期的加载缩短时间。
             * .get()：该方法是一个异步线程，加载完成后会返回一个Bitmap，但是需要注意的是该方法不能在主线程中调用，因为会造成线程阻塞。
             * .rotate(float degrees)：旋转处理时，只需要传入大于0小于360的旋转角度即可。
             * .rotate(float degrees, float pivotX, float pivotY)：默认的图片旋转都是相对(0,0)进行旋转的，所以如果我们想自定义相对于某个点的旋转时可以调用以上方法。
             * .error：加载失败时的图片显示，如果重试3次（下载源代码可以根据需要修改）还是无法成功加载图片，则用错误占位符图片显示。
             * .placeholder()：加载过程中的图片显示。
             *
             */
            //方式一
            Picasso.with(mContext).setIndicatorsEnabled(true);//查看从哪加载图片
            Picasso.with(mContext).setLoggingEnabled(true);//通过输出日志的方式查看每张图片从网络请求加载时用的时间。
            Picasso.with(mContext).load(Constant.mImage[position]).error(R.drawable.girl_1).noFade().into(holder.mImage);

            //查看图片的加载在内存中占用了多大可以调用StatsSnapshot即可。
            //结果:statsSnapshot:StatsSnapshot{maxSize=19173961, size=0, cacheHits=0
//                    , cacheMisses=1, downloadCount=0, totalDownloadSize=0
//                    , averageDownloadSize=0, totalOriginalBitmapSize=0, totalTransformedBitmapSize=0
//                    , averageOriginalBitmapSize=0, averageTransformedBitmapSize=0, originalBitmapCount=0
//                    , transformedBitmapCount=0, timeStamp=1494484266351}
//            StatsSnapshot statsSnapshot = Picasso.with(mContext).getSnapshot();
//            Log.e(TAG, "statsSnapshot:" + statsSnapshot.toString());

            //方式二效果不好
            //注意：可以使用.get()或者Target获取图片的Bitmap，但是当你使用Target时不能使用匿名内部类的方式，因为垃圾回收机制在你获取不到Bitmap的时候会把对象回收。
//            MyTarget target = new MyTarget(holder.mImage);
//            Picasso.with(mContext).load(Constant.mImage[position]).into(target);

            //方式三:模糊图片
//            Picasso.with(mContext)
//                    .load(Constant.mImage[position])
//                    .transform(new BlurTransformation(mContext))
//                    .into(holder.mImage);

            //方式四:模糊+圆形
//            List<Transformation> transformations = new ArrayList<>();
//            transformations.add(new CircleTransformation());
//            transformations.add(new BlurTransformation(mContext));
//            Picasso.with(mContext)
//                    .load(Constant.mImage[position])
//                    .transform(transformations)
//                    .into(holder.mImage);





            //当图片地址不存在或为空时，可以先调用cancelRequest()方法取消网络请求，然后调用imageView.setImageDrawable(null)方法进行设置。
//            if (TextUtils.isEmpty(Constant.mImage[position])){
//                Picasso.with(mContext)
//                        .cancelRequest(holder.mImage);
//                holder.mImage.setImageDrawable(null);
//            }



            //Picasso在自定义Notification中的使用：
//            Picasso有一个功能是可以加载图片到RemoteViews上，而RemoteViews是用在Widget及自定义Notification布局中的，以下是其使用方式。
//            Picasso.with(mContext)
//                    .load(Constant.mImage[position])
//                    .into(remoteViews, R.id.iv_remoteView, notificationId, notification);
        }

        @Override
        public int getItemCount() {
            return Constant.mImage.length;
        }
    }



    //方式四:模糊+圆形
    private class CircleTransformation implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());

            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
            if (squaredBitmap != source) {
                source.recycle();
            }

            Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP,
                    BitmapShader.TileMode.CLAMP);
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            squaredBitmap.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "circle";
        }
    }

    //方式三加载图片:模糊效果
    private class BlurTransformation implements Transformation {

        RenderScript rs;

        public BlurTransformation(Context context) {rs = RenderScript.create(context);}

        @Override
        public Bitmap transform(Bitmap bitmap) {
            // 创建一个Bitmap作为最后处理的效果Bitmap
            Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

            // 分配内存
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // 根据我们想使用的配置加载一个实例
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // 设置模糊半径
            script.setRadius(10);

            //开始操作
            script.forEach(output);

            // 将结果copy到blurredBitmap中
            output.copyTo(blurredBitmap);

            //释放资源
            bitmap.recycle();

            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blur";
        }
    }

    //方式二加载图片
    class MyTarget implements Target {
        private ImageView mView;
        private ProgressDialog mDialog;

        public MyTarget(ImageView imageView) {
            mView=imageView;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (mDialog!=null) {
                mDialog.dismiss();
            }
            mView.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {}

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (mDialog==null) {
                mDialog = new ProgressDialog(mContext);
            }
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            mDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
            mDialog.setCanceledOnTouchOutside(true);// 设置在点击Dialog外是否取消Dialog进度条
            mDialog.show();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private View mView;

        public MyHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mImage = itemView.findViewById(R.id.picasso_recycler_item_image);
        }
    }
}

/**
 * Glide与Picasso比较

 使用方法

 1、Glide更容易使用，因为Glide的with()方法不光接受Context，还能接受Activity和Fragment，Context会自动从它们获取。Picasso只接受Context。
 2、将Activity/Fragment作为with()参数的好处是图片加载将会和Activity/Fragment的生命周期保持一致。比如在onPause状态暂停加载，在onResume状态的时候又自动重新加载。

 默认Bitmap格式

 1、Glide默认Bitmap格式是RGB_565，Picasso默认的Bitmap格式的是ARGB_8888。
 2、想要提高Glide的图片效果，Glide可以创建一个新的GlideModule将Bitmap格式转换为ARGB_8888：

 public class GlideConfiguration implements GlideModule {
        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
            builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        }

        @Override
        public void registerComponents(Context context, Glide glide) {}
}

 同时在AndroidManifest.xml中将GlideModule定义为meta-data。
         <meta-data
         android:name="com.wiggins.glide.GlideConfiguration"
         android:value="GlideModule" />



 加载策略

 1、Picasso是加载了全尺寸的图片到内存，然后让GPU来实时重绘大小。而Glide加载的大小和ImageView的大小是一致的，因此会更小。
 2、Picasso也可以指定加载的图片大小：

 Picasso.with(mActivity).load(Constant.picUrl).resize(768, 432).into(mIvPic);


 但是问题在于你需要主动计算ImageView的大小，或者说你的ImageView大小是具体的值（而不是wrap_content），你也可以这样：

 Picasso.with(mActivity).load(Constant.picUrl).fit().centerCrop().into(mIvPic);

 Image质量细节

 将ImageView还原到真实大小时，Glide加载的图片没有Picasso那么平滑。

 图像和内存

 同样将1080 × 1920像素的图片加载到768 × 432的ImageView中，Glide加载的图片质量要差于Picasso。这是因为Glide默认的Bitmap格式是RGB-565，比ARGB-8888格式的内存开销大约要小一半。

 GIF显示

 1、Glide支持GIF显示，而Picasso不支持GIF显示。
 2、因为Glide和Activity/Fragment的生命周期是一致的，因此GIF动画也会自动的随Activity/Fragment的状态而进行暂停或者重放。Glide缓存在GIF这里也是一样，会调整大小然后缓存。

 缓存策略

 1、Picasso和Glide在磁盘缓存策略上有很大的不同。Picasso缓存的是全尺寸的，而Glide缓存的是跟ImageView尺寸相同的。
 2、Picasso只缓存一个全尺寸的，而Glide会为每种大小的ImageView缓存一次。尽管一张图片已经缓存了一次，但是假如你要在另外一个地方再次以不同尺寸显示时还需要重新下载，然后将其调整成新尺寸的大小缓存起来。
 3、具体来说就是：假如在第一个页面有一个200 x 200的ImageView，在第二个页面有一个100 x 100的ImageView，这两个ImageView本来是要显示同一张图片，但是Glide却需要下载两次。
 4、可以改变以上这种行为，让Glide既缓存全尺寸又缓存其他尺寸：

 Glide.with(this).load(Constant.image_dynamic).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivImage);

 下次在任何ImageView中加载图片的时候，全尺寸的图片将从缓存中取出重新调整大小，然后进行缓存。

 5、Glide的这种缓存方式优点是加载显示非常快，而Picasso的方式则因为需要在显示之前重新调整大小而导致一些延迟，不过Glide比Picasso需要更大的空间来进行缓存。

 其他

 1、包的大小：Picasso(v2.5.1)的大小约118kb，而Glide(v3.5.2)的大小约430kb。
 2、方法数目：Picasso和Glide的方法个数分别是840和2678个。对于DEX文件65535个方法限制来说，2678是一个相当大的数字了。建议在使用Glide的时候开启ProGuard。
 3、Glide可以将任何的本地视频解码成一张静态图片，而Picasso不可以。





 Fresco优缺点

 Fresco是Facebook开源Android平台上一个强大的图片加载库。

 主要特点

 两个内存缓存加上Native缓存构成了三级缓存；
 支持流式，可以类似网页上模糊渐进方式显示图片；
 对多帧动画图片支持更好，如：Gif、WebP等。

 内存管理

 Fresco最大的亮点在于它的内存管理。Android中的Bitmap占用大量的内存，在Android 5.0以下系统中，这会显著地引发界面卡顿，而使用Fresco将会很好地解决这个问题。Fresco会将图片放到一个特别的内存区域，当图片不再显示的时候，占用的内存会自动被释放。这会使得APP更加流畅，减少因图片内存占用而引发的OOM，当APP包含的图片较多时这个效果尤其明显。

 图像

 Fresco支持图像的渐进式呈现，渐进式的图片格式先呈现大致的图片轮廓，然后随着图片下载的继续，逐渐呈现清晰的图片。这在低网速情况下浏览图片十分有帮助，可以带来更好地用户体验。另外Fresco支持加载GIF、WebP格式。

 优点

 图片存储在安卓系统的匿名共享内存，而不是虚拟机的堆内存中，图片的中间缓冲数据也存放在本地堆内存。所以应用程序有更多的内存使用，不会因为图片加载而导致OOM，同时也减少垃圾回收器频繁调用回收Bitmap导致的界面卡顿，性能更高；
 渐进式加载JPEG图片，支持图片从模糊到清晰加载；
 图片可以以任意的中心点显示在ImageView，而不仅仅是图片的中心；
 JPEG图片改变大小也是在native进行的，不是在虚拟机的堆内存，同样减少OOM的发生；
 很好的支持GIF图片显示。

 缺点

 框架较大，影响Apk体积；
 使用较繁琐。






 ImageLoader优缺点

 比较老的框架、稳定、加载速度适中。

 优点

 多线程下载图片，图片可以来源于网络、文件、assets以及drawable中等；
 支持自定义的配置ImageLoader，例如线程池、图片下载器、内存缓存策略、硬盘缓存策略、图片显示选项以及其他的一些配置；
 支持图片的内存缓存、文件系统缓存或者SD卡缓存；
 默认实现多种内存缓存算法，如Size最大先删除、使用最少先删除、最近最少使用先删除、先进先删除、时间最长先删除等；
 支持图片下载过程监听；
 根据控件(ImageView)的大小对Bitmap进行裁剪，减少Bitmap占用过多的内存；
 较好的控制图片的加载过程，例如暂停图片加载、重新开始加载图片等，一般使用在ListView、GridView中，通过PauseOnScrollListener接口控制滑动过程中暂停加载图片，停止滑动的时候去加载图片；
 提供在较慢的网络下对图片进行加载。

 缺点

 不支持GIF图片加载，使用稍微繁琐，并且缓存机制没有和Http的缓存很好的结合，完全是自己的一套缓存机制。
 Picasso优缺点

 使用方便、一行代码完成加载图片并显示、框架体积小。

 优点

 自带统计检测功能：支持图片缓存使用检测，包括：缓存命中率、已使用内存大小、节省的流量等；
 支持优先级处理：每次任务调度前会选择优先级高的任务，比如App页面中Banner的优先级高于Icon时就很适用；
 支持延迟到图片尺寸计算完成加载；
 支持飞行模式、并发线程数根据网络状态变化：手机切换到飞行模式或网络状态变换时会自动调整线程池最大并发数；
 “无”本地缓存：无”本地缓存不是说没有本地缓存，而是Picasso自己没有实现，交给了Square的另外一个网络库OkHttp去实现。这样的好处是可以通过请求Response Header中的Cache-Control及Expired来控制图片的过期时间。

 缺点

 不支持GIF，缓存图片是未缩放的，默认使用ARGB_8888格式缓存图片，缓存体积大。
 Glide优缺点

 优点

 图片占用内存回收及时，能减少因内存不足造成的崩溃，生命周期和Activity/Fragment一致；
 默认Bitmap格式是RGB_565，减少内存资源占用；
 Glide比Universal-Image-Loader占用的内存要小一些；
 图片显示效果为渐变，更加平滑；
 Glide可以将任何的本地视频解码成一张静态图片；
 支持Gif、WebP、缩略图等。

 缺点

 框架较大，影响Apk体积。
 */
