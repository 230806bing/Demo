package com.example.demo.spinnerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //预定义变量
    Spinner spinner;
    List<String> listForSpinner = new ArrayList<>();
    ArrayAdapter<String> adapterForSpinner;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        //变量初始化
        spinner = findViewById(R.id.spinner);// 引用Spinner控件
        textView = findViewById(R.id.tv_log);

        //给字符串数组赋初值
        listForSpinner.add("C语言");
        listForSpinner.add("Python");
        listForSpinner.add("Java");
        listForSpinner.add("C++");
        //设置适配器
        adapterForSpinner = new ArrayAdapter<>(SpinnerActivity.this, R.layout.item_for_custom_spinner, listForSpinner);
        spinner.setAdapter(adapterForSpinner);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String language = parent.getItemAtPosition(position).toString();
        textView.setText(language);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}