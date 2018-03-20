package com.hzm.cos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv) {
            Main2Activity.startToMe(this);
        }
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setOnClickListener(MainActivity.this);
    }
}
