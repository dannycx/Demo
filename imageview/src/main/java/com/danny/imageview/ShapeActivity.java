package com.danny.imageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danny.imageview.shape.ShapeView;

public class ShapeActivity extends AppCompatActivity {
    private ShapeView mShapeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);
        mShapeView=findViewById(R.id.shape_view);
    }
}
