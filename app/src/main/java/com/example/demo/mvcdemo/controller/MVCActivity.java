package com.example.demo.mvcdemo.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.mvcdemo.bean.DataResponse;
import com.example.demo.mvcdemo.model.CharacterModel;
import com.example.demo.mvcdemo.model.CharacterModelImpl;
import com.example.demo.mvcdemo.model.OnCharacterListener;

public class MVCActivity extends AppCompatActivity implements OnCharacterListener {
    Button btnRequest;
    TextView tvPath;
    TextView tvName;
    EditText etId;
    CharacterModel characterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvcactivity);
        characterModel = new CharacterModelImpl();
        initView();
    }

    private void initView() {
        btnRequest = findViewById(R.id.btn_request);
        tvPath = findViewById(R.id.tv_path);
        tvName = findViewById(R.id.tv_name);
        etId = findViewById(R.id.et_id);

        btnRequest.setOnClickListener(this);
    }

    public void display(DataResponse response) {
        DataResponse.DataBean dataBean = response.getData();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPath.setText(dataBean.getPath());
                tvName.setText(dataBean.getName());
            }
        });
    }

    @Override
    public void onError() {
        Toast.makeText(this,"获取失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(DataResponse dataBean) {
        display(dataBean);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_request:
                characterModel.getCharacter("1288fd9e9d424efba8d63523b5a0a6de","26",this);
        }
    }
}