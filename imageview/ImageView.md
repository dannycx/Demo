# 图片处理

### 图片三级缓存
###### 内存缓存，磁盘缓存，网络获取(控件上加载一张图片会先从内存获取，没有就从本地获取，最后再从网络获取)

#### 网络获取
- 网络获取通过HttpURLConnection实现，直接将流通过BitmapFactory的api转换为一张bitmap图片
- 此处没对图片做缩略处理，只是简单实现网络加载图片,代码如下(子线程中调用，可使用AsyncTask实现）
```Java
/**
 * 下载网络图片
 * @param url 网络链接
 * @return bitmap对象
 */
private Bitmap download(String url) {
      HttpURLConnection conn=null;
      try {
         conn = (HttpURLConnection) new URL(url).openConnection();
         conn.setRequestMethod("GET");
         conn.setConnectTimeout(5000);
         conn.setReadTimeout(5000);
         conn.connect();
         int responseCode = conn.getResponseCode();
         if (responseCode == 200){
             InputStream is = conn.getInputStream();
             Bitmap bitmap = BitmapFactory.decodeStream(is);
             return bitmap;
         }
     } catch (IOException e) {
         e.printStackTrace();
     }finally {
         if (conn!=null){
             conn.disconnect();
         }
     }
     return null;
}
```
#### 本地缓存
###### 该方法可在网络获取bitmap图片后进行调用，使用url通过md5加密后作为文件名(当然也可以使用url+当前时间进行加密后作为文件名，可以对bitmap进行有效期管理，此处不在实现，有兴趣的话可自行实现)
```Java
/**
 * 设置缓存
 * @param url 文件名
 * @param bitmap bitmap对象
 */
public void setCache(String url, Bitmap bitmap){
    File dir = new File(LOCAL_Path);
    if (!dir.exists() || !dir.isDirectory()){
        dir.mkdirs();
    }
    try {
        String fileName = MD5Encoder.encode(url);
        File file = new File(dir,fileName);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
    
/**
 * 获取本地缓存数据
 * @param url 文件名
 * @return bitmap对象
 */
public Bitmap getCache(String url){
    try {
        File file = new File(LOCAL_Path,MD5Encoder.encode(url));
        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        return bitmap;
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}
```

#### 内存缓存
- 内存缓存主要是使用集合进行缓存数据
- 栈:  存变量,方法声明,引用    堆:  对象
###### 几种引用介绍
- 强引用:垃圾回收器不会回收
- 软引用:垃圾回收期,在内存充足时不回收-SoftReference
- 弱引用:检测到就回收-WeakReference
- 虚引用:形同虚设-PhantomReference
```java
private HashMap<String,SoftReference<Bitmap>> mCache = new HashMap<>();//不建议使用,极不可靠

//存
SoftReference<Bitmap> soft = new SoftReference<>(bitmap);
mCache.put(url,soft);

///取
SoftReference<Bitmap> soft = mCache.get(url);
if (soft != null) {
   return soft.get();
}
```

###### Android2.3之后Android虚拟机更倾向于回收持有软引用和弱引用对象，所以此种方式极不可靠，进一步优化,使用LruCache，内部维护一个LinkedHashMap和mMaxSize，采用最近最少使用算法，优化内存占用问题
```java
//google建议用LruCache,内部实现LinkedHashMap,采用最近最少使用算法
private LruCache<String,Bitmap> mLruCache;
//获取分配最大内存数:Runtime.getRuntime().maxMemory()
int maxSize = (int) (Runtime.getRuntime().maxMemory());
mLruCache = new LruCache<>(maxSize / 8);//分配八分之一空间用来缓存图片

//存
mLruCache.put(url,bitmap);

//取
return mLruCache.get(url);
```

## Glide使用
###### 使用简单，一行代码就可以实现将一个url转为图片设置到控件中
```java
Glide.with(Context).load(url).into(mIcon);
```
#### 优势
- context对象可以为activity，fragment，若传activity，fragment可将Glide与Activity和Fragment生命周期进行绑定
- Glide可以加载gif图片，可以讲video格式视频转换为一张图片
- 根据控件大小进行内存缓存，同一张图片运用在不同大小的控件上，会缓存两张图片，以空间节省时间
- 图片采用bitmap：rgb-565，相对与picasso节省内存空间
- 可以加载文件下图片，资源文件下图片，以及通过url获取图片

#### 改变图片大小方法
- 
