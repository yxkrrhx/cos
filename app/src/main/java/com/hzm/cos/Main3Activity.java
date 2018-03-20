package com.hzm.cos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    protected RecyclerView mRcv;
    EAdapter mEAdapter;
    List<ItemBean> mList = new ArrayList<>();

    public static void startToMe(Context ctx) {
        Intent intent = new Intent(ctx, Main3Activity.class);
        ctx.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main3);

        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mEAdapter = new EAdapter(mList);
        mEAdapter.openLoadAnimation();
        mRcv.setAdapter(mEAdapter);
        createData();

        mEAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               MeizuActivity.startToMe(Main3Activity.this);
            }
        });
    }


    private void createData(){
        for (int i=0;i<20;i++) {
            ItemBean itemBean=new ItemBean();
            itemBean.setName("第"+i+"个");
            mList.add(itemBean);
        }

        if (mEAdapter!=null) mEAdapter.notifyDataSetChanged();

    }


    public class EAdapter extends BaseQuickAdapter<ItemBean, BaseViewHolder> {
        public EAdapter(@Nullable List<ItemBean> data) {
            super(R.layout.ev_adapter_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ItemBean item) {
            helper.setText(R.id.tv, item.getName());
        }
    }



}
