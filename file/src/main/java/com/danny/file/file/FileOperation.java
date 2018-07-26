package com.danny.file.file;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件操作工具类
 * Created by danny on 5/8/18.
 */

public class FileOperation {
    private static final String TAG = FileOperation.class.getSimpleName();

    /**
     * 按行读取文件
     */
    public static void readFile(String filePath) {
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                Log.d(TAG, "readFile: " + tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以追加方式写文件
     */
    public static void writeFile(String filePath, String conent) {
        createFile(filePath);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建文件
     */
    public static void createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Log.d(TAG, "createFile: 文件已存在");
        } else {
            try {
                File fileParent = file.getParentFile();
                Log.e(TAG, "createFile: " + fileParent.getAbsolutePath());

                if (fileParent != null) {
                    if (!fileParent.exists()) {
                        fileParent.mkdirs();
                    }
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 以追加方式写文件，效率低
     */
    public static void writeFileByFileWriter(String filePath, String content) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(filePath), true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        File file=new File(filePath);
        if (file.exists()){
            if (file.isFile()){
                file.delete();
            }else {
                Log.d(TAG, "deleteFile: 文件夹");
            }
        }else {
            Log.d(TAG, "deleteFile: 文件不存在");
        }
    }

    /**
     * 删除文件夹,针对目录下只有文件
     */
    public static void deleteDirectory(String filePath) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + filePath;
        File file=new File(path);
        if (file.exists()){
            if (file.isFile()){
                file.delete();
            }else {
                File[] files = file.listFiles();
                for (int i=0;i<files.length;i++){
                    Log.d(TAG, "deleteFile: 文件夹");
                    deleteFile(files[i].getAbsolutePath());
                }
                file.delete();
            }
        }else {
            Log.d(TAG, "deleteFile: 文件不存在");
        }
    }

    /**
     * 将数据存到文件中
     *
     * @param context context
     * @param data 需要保存的数据
     * @param fileName 文件名
     */
    private void saveDataToFile(Context context, String data, String fileName) {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            /**
             * "data"为文件名,MODE_PRIVATE表示如果存在同名文件则覆盖，
             * 还有一个MODE_APPEND表示如果存在同名文件则会往里面追加内容
             */
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null)
                {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件中读取数据
     * @param context context
     * @param fileName 文件名
     * @return 从文件中读取的数据
     */
    private String loadDataFromFile(Context context, String fileName)
    {
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            /**
             * 注意这里的fileName不要用绝对路径，只需要文件名就可以了，系统会自动到data目录下去加载这个文件
             */
            fileInputStream = context.openFileInput(fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String result = "";
            while ((result = bufferedReader.readLine()) != null) {
                stringBuilder.append(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
}
