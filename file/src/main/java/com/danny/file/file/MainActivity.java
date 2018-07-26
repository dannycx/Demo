package com.danny.file.file;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.danny.file.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "file_test" + File.separator + "1.txt";
    private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    private Context mContext;
    private Button mCreate;
    private Button mDelete;
    private Button mWrite;
    private Button mRead;

    private Button mRead2;
    private Button mWrite2;
    private EditText mEditContent;
    private TextView mTextContent;

    private Button mDeleteDirectory;
    private EditText mDirectoryName;

    private Button mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initView();
    }

    private void initView() {
        mCreate=findViewById(R.id.file_create);
        mDelete=findViewById(R.id.file_delete);
        mWrite=findViewById(R.id.file_write);
        mRead=findViewById(R.id.file_read);

        mRead2=findViewById(R.id.file_read_btn);
        mWrite2=findViewById(R.id.file_write_btn);
        mEditContent=findViewById(R.id.file_write_content);
        mTextContent=findViewById(R.id.file_read_content);

        mDeleteDirectory=findViewById(R.id.file_delete_directory);
        mDirectoryName=findViewById(R.id.file_delete_directory_name);

        mQuery=findViewById(R.id.file_query);

        mCreate.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mWrite.setOnClickListener(this);
        mRead.setOnClickListener(this);
        mDeleteDirectory.setOnClickListener(this);
        mRead2.setOnClickListener(this);
        mWrite2.setOnClickListener(this);
        mQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file_create:
                createFile();
                break;
            case R.id.file_delete:
                deleteFile();
                break;
            case R.id.file_write:
                writeFile();
                break;
            case R.id.file_read:
                readFile();
                break;
            case R.id.file_delete_directory:
                deleteDirectory();
                break;
            case R.id.file_read_btn:
                show();
                break;
            case R.id.file_write_btn:
                write2File();
                break;
            case R.id.file_query:
                startActivity(new Intent(mContext,MachineFileActivity.class));
                break;
        }
    }

    private void show() {mTextContent.setText(read2File("a.txt"));}

    private void write2File() {
        String content = mEditContent.getText().toString().trim();
        if (!TextUtils.isEmpty(content)){
            write("a.txt", content);
            mEditContent.setText("");
        }else {
            Toast.makeText(mContext,"请输入内容!",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 写入项目data/data/package/files/
     * @param content 内容
     */
    private void write(String fileName, String content) {
        try {
            FileOutputStream fos=openFileOutput(fileName, Context.MODE_PRIVATE);
            PrintStream ps=new PrintStream(fos);
            ps.println(content);
            ps.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取项目data/data/package/files/
     * @param fileName 文件名
     */
    private String read2File(String fileName) {
        try {
            FileInputStream fis=openFileInput(fileName);
            StringBuilder sb=new StringBuilder();
            byte[] bys=new byte[1024];
            int len=0;
            while ((len=fis.read(bys))!=-1){
                sb.append(new String(bys,0,len));
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * sd卡操作
     */
    private void readFile() {FileOperation.readFile(PATH);}

    private void writeFile() {
        FileOperation.writeFile(PATH,"张三:15312345678\n李四:15212345678\n");
        FileOperation.writeFile(PATH,"王五:15812345678\n李二:15112345678\n");
    }

    private void deleteFile() {FileOperation.deleteFile(PATH);}

    private void createFile() {FileOperation.createFile(PATH);}

    private void deleteDirectory() {
        String directory = mDirectoryName.getText().toString().trim();

        if (!TextUtils.isEmpty(directory)){
//            FileOperation.deleteDirectory(directory);
            FileUtils.deleteDir(new File(ROOT_PATH,directory));
        }else {
            Toast.makeText(mContext,"请输入要删除的文件夹名称!",Toast.LENGTH_SHORT).show();
        }
    }
}
