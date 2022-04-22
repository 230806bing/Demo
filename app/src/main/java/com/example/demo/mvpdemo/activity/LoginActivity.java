package com.example.demo.mvpdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo.R;
import com.example.demo.mvpdemo.presenter.LoginPresenter;
import com.example.demo.mvpdemo.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private EditText mUserNameEdit;
    private EditText mPasswordEdit;
    private Button mLoginBtn;

    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();
        setData();
    }

    private void setData() {
        mPresenter = new LoginPresenter(this);
    }

    private void setView() {
        mUserNameEdit = findViewById(R.id.et_name);
        mPasswordEdit = findViewById(R.id.et_pwd);
        mLoginBtn = findViewById(R.id.btn_login);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

                mPresenter.login();
            }
        });
    }

    @Override
    public String getUserName() {
        return mUserNameEdit.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEdit.getText().toString();
    }

    @Override
    public void onLoginSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "登陆成功！", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onLoginFails() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "登录失败！", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter = null;
    }
}