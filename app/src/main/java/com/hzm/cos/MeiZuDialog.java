package com.hzm.cos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by hzm on 2018/3/5 in CQ.
 * Desc:
 */

public class MeiZuDialog extends AppCompatDialog {

    Context context;
    public MeiZuDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.dialog_layout);
        initDialogParams();
    }

    public void initDialogParams(){
        final Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.TOP);//dialog显示的位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth()); // 宽度设置为屏幕的 * 0.8，根据实际情况调整
        params.height=(int) (d.getHeight());
        params.alpha = 1.0f;// 设置Dialog透明度
        dialogWindow.setDimAmount(0.5f); //设置当dialog弹出后，对activity的遮罩层的透明度
        dialogWindow.setAttributes(params);
    }

}
