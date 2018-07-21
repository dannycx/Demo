package com.danny.databinding.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danny.databinding.R;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new DataBindingFragment()).commit();
    }
}
