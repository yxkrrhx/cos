package com.hzm.cos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    protected ImageView mScrollingHeader;
    protected LinearLayout mEditSearch;
    protected TextView mTv;
    protected NestedScrollView mNestedScrollView;

    public static void startToMe(Context ctx) {
        Intent intent = new Intent(ctx, Main2Activity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main2);
        initView();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv) {
            Main3Activity.startToMe(this);
        }
    }

    private void initView() {
        mScrollingHeader = (ImageView) findViewById(R.id.scrolling_header);
        mEditSearch = (LinearLayout) findViewById(R.id.edit_search);
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setOnClickListener(Main2Activity.this);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view);
    }
}
