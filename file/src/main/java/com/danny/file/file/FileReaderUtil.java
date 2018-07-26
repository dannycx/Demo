package com.danny.file.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流操作工具类
 * Created by danny on 2018/5/7.
 */

public class FileReaderUtil {

    public static String stream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] bys=new byte[1024];
        int len=-1;
        while ((len=is.read(bys))!=-1){
            baos.write(bys,0,len);
        }
        baos.close();
        return baos.toString();
    }
}
