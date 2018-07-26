package com.danny.file.file;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by danny on 17-12-1.
 */

public class FileUtils {
    public static String SDPATH = Environment.getExternalStorageDirectory().getPath();

    /**
     * 保存图片
     */
    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        Log.d("text", SDPATH);
        try {
            if (!isFileExist("")) {
                //File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建sd卡文件夹
     * @param dirName
     */
    public static void createSDDir(String dirName){
        File dir = new File(SDPATH,dirName);
        if (!dir.exists()){dir.mkdir();}
    }

    /**
     * 文件是否存在
     * @param fileName 文件名
     * @return true-是文件并且存在 false-不是文件\或者不存在
     */
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            return file.exists();
        }else {
            return false;
        }
    }

    /**
     * 删除文件
     */
    public static void delFile(String fileName){
        File file = new File(SDPATH,fileName);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    /**
     * 删除文件夹和文件夹里面的文件
     */
    public static void deleteDir(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if(file.isDirectory()){
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                deleteDir(childFiles[i]);
            }
            file.delete();
        }
    }
}
