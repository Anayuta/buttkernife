package com.example.butterknife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.viewinject.ViewInjectUtil;
import com.example.viewinject.annotation.BindView;
import com.example.viewinject.annotation.ContentView;
import com.example.viewinject.annotation.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_show_txt)
    private Button btn_show_txt;
    @BindView(R.id.tv_hello)
    TextView tv_hello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtil.bind(this);
    }

    @OnClick(R.id.btn_show_txt)
    public void showText(String view) {
        tv_hello.setText("i come on!");
    }
}
