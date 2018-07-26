package com.danny.file.ergodic;

/**
 * 是否是文件夹封装
 * Created by danny on 6/12/18.
 */

public class Directory {
    private boolean mIsDirectory;
    private String mFileName;

    public boolean isDirectory() {return mIsDirectory;}
    public void setDirectory(boolean directory) {mIsDirectory = directory;}
    public String getFileName() {return mFileName;}
    public void setFileName(String fileName) {mFileName = fileName;}
}
