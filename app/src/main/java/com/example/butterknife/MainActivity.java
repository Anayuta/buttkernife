package com.example.butterknife;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.viewinject.ViewInjectUtil;
import com.example.viewinject.annotation.BindHeadView;
import com.example.viewinject.annotation.BindView;
import com.example.viewinject.annotation.ContentView;
import com.example.viewinject.annotation.OnClick;
import com.example.viewinject.annotation.OnLongClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_show_txt)
    private Button btn_show_txt;
    @BindView(R.id.tv_hello)
    TextView tv_hello;

    @BindView(R.id.lv)
    ListView listView;

    @BindHeadView(R.id.tv_head)
    TextView textView;

//    LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtil.bind(this);
        View view = LayoutInflater.from(this).inflate(R.layout.head, null);
        ViewInjectUtil.bind(this,view);
        listView.addHeaderView(view);
//        textView = (TextView) view.findViewById(R.id.tv_head);
        textView.setText("我是head");
        listView.setAdapter(new SimpleAdapter());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("我是注解headerview");
            }
        },3000);
    }

    @OnClick(R.id.btn_show_txt)
    public void showText(View view) {
        tv_hello.setText("i come on!");
    }

    @OnLongClick(R.id.tv_hello)
    public void testLongClick(View view) {
        tv_hello.setText("longClick...");
    }

    class SimpleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item, null);
            return view;
        }
    }
}
