package com.danny.file.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.danny.file.R;


/**
 * Created by danny on 17-9-27.
 */

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_start_stop);

        Button buttonStart = (Button) findViewById(R.id.startService);
        buttonStart.setOnClickListener(this);
        Button buttonStop = (Button) findViewById(R.id.stopService);
        buttonStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.startService:
                Intent intentStart=new Intent(this,MyService.class);
                startService(intentStart);
                break;
            case R.id.stopService:
                Intent intentStop=new Intent(this,MyService.class);
                startService(intentStop);
                break;
            default:
                break;
        }
    }
}
