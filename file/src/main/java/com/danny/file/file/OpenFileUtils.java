package com.danny.file.file;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

/**
 * 打开文件
 * Created by danny on 5/28/18.
 */

public class OpenFileUtils {
    /**声明各种类型文件的dataType**/
    private static final String DATA_TYPE_ALL = "*/*";//未指定明确的文件类型，不能使用精确类型的工具打开，需要用户选择
    private static final String DATA_TYPE_APK = "application/vnd.android.package-archive";
    private static final String DATA_TYPE_VIDEO = "video/*";
    private static final String DATA_TYPE_AUDIO = "audio/*";
    private static final String DATA_TYPE_HTML = "text/html";
    private static final String DATA_TYPE_IMAGE = "image/*";
    private static final String DATA_TYPE_PPT = "application/vnd.ms-powerpoint";
    private static final String DATA_TYPE_EXCEL = "application/vnd.ms-excel";
    private static final String DATA_TYPE_WORD = "application/msword";
    private static final String DATA_TYPE_CHM = "application/x-chm";
    private static final String DATA_TYPE_TXT = "text/plain";
    private static final String DATA_TYPE_PDF = "application/pdf";

    private static OpenFileUtils sInstance=null;
    private static Context mContext;
//OpenFileUtils.getInstance(Context context).openFile(String filePath)

    public static OpenFileUtils getInstance(Context context){
        mContext=context;
        if (sInstance==null){
            sInstance=new OpenFileUtils();
        }
        return sInstance;
    }
    /**
     * 获取对应文件的Uri
     * @param intent 相应的Intent
     * @param file 文件对象
     * @return
     */
    private Uri getUri(Intent intent, File file) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //判断版本是否在7.0以上
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /**
     * 打开文件
     * @param filePath 文件的全路径，包括到文件名
     */
    public void openFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()){
            //如果文件不存在
            Toast.makeText(mContext, "打开失败，原因：文件已经被移动或者删除", Toast.LENGTH_SHORT).show();
            return;
        }
    /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase(Locale.getDefault());
    /* 依扩展名的类型决定MimeType */
        Intent intent = null;
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            intent =  generateVideoAudioIntent(filePath,DATA_TYPE_AUDIO);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            intent = generateVideoAudioIntent(filePath,DATA_TYPE_VIDEO);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_IMAGE);
        } else if (end.equals("apk")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_APK);
        }else if (end.equals("html") || end.equals("htm")){
            intent = generateHtmlFileIntent(filePath);
        } else if (end.equals("ppt")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_PPT);
        } else if (end.equals("xls")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_EXCEL);
        } else if (end.equals("doc")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_WORD);
        } else if (end.equals("pdf")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_PDF);
        } else if (end.equals("chm")) {
            intent = generateCommonIntent(filePath,DATA_TYPE_CHM);
        } else if (end.equals("txt")) {
            intent = generateCommonIntent(filePath, DATA_TYPE_TXT);
        } else {
            intent = generateCommonIntent(filePath,DATA_TYPE_ALL);
        }
        mContext.startActivity(intent);
    }

    /**
     * 产生打开视频或音频的Intent
     * @param filePath 文件路径
     * @param dataType 文件类型
     * @return
     */
    private Intent generateVideoAudioIntent(String filePath, String dataType){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        File file = new File(filePath);
        intent.setDataAndType(getUri(intent,file), dataType);
        return intent;
    }

    /**
     * 产生打开网页文件的Intent
     * @param filePath 文件路径
     * @return
     */
    private Intent generateHtmlFileIntent(String filePath) {
        Uri uri = Uri.parse(filePath)
                .buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content")
                .encodedPath(filePath)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, DATA_TYPE_HTML);
        return intent;
    }

    /**
     * 产生除了视频、音频、网页文件外，打开其他类型文件的Intent
     * @param filePath 文件路径
     * @param dataType 文件类型
     * @return
     */
    private Intent generateCommonIntent(String filePath, String dataType) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        File file = new File(filePath);
        Uri uri = getUri(intent, file);
        intent.setDataAndType(uri, dataType);
        return intent;
    }
}
