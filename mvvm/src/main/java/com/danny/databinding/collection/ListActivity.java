package com.danny.databinding.collection;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.danny.databinding.R;
import com.danny.databinding.databinding.ActivityListBinding;
import com.danny.databinding.list.Person;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        List<Person> people = new ArrayList<>();
        people.add(new Person("http://avatar.csdn.net/4/9/8/1_a10615.jpg","张三"));
        people.add(new Person(null,"李思"));
        binding.setList(people);
    }
}
