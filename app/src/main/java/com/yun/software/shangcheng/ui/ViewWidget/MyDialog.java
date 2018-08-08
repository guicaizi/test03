package com.yun.software.shangcheng.ui.ViewWidget;

/**
 * Created by pang on 2017/4/4.
 *  自定义 dialog对话框
 */

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.software.shangcheng.R;


public class MyDialog extends Dialog {
    private TextView tv_create_aciton;
    private TextView tv_create_chatting;
    private ImageView img_create_close;
    private Context mcontext;
    private CreateAllpopClickListener  poplistenter;



    public MyDialog(Context context) {
        super(context, R.style.MyDialog);
        setOwnerActivity((Activity) context);
        this.mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_touxiang);
        //按空白处取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
//        initView();
        //初始化界面控件的事件
//        initEvent();
    }


    public void setPoplistenter(CreateAllpopClickListener  poplistenter){
        this.poplistenter=poplistenter;
    }
    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
          img_create_close.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  poplistenter.creat_close();
              }
          });
        tv_create_aciton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poplistenter.creat_action();
            }
        });
        tv_create_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poplistenter.creat_chart();
            }
        });
    }



    /**
     * 初始化界面控件
     */
    private void initView() {

//       tv_create_aciton=(TextView) findViewById(R.id.tv_create_action);
//       tv_create_chatting=(TextView) findViewById(R.id.tv_create_chatting);
        img_create_close=(ImageView) findViewById(R.id.img_close_create);

        
    }

    /**
     * 动态设置Activity背景透明度
     *
     * @param isopen
     */
    public void setWindowAlpa(boolean isopen) {
        if (Build.VERSION.SDK_INT < 11) {
            return;
        }
        final Window window = ((Activity) mcontext).getWindow();
        final WindowManager.LayoutParams lp = window.getAttributes();
        window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ValueAnimator animator;
        if (isopen) {
            animator = ValueAnimator.ofFloat(1.0f, 0.5f);
        } else {
            animator = ValueAnimator.ofFloat(0.5f, 1.0f);
        }
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                lp.alpha = alpha;
                window.setAttributes(lp);
            }
        });
        animator.start();
    }
   public interface CreateAllpopClickListener{

       void creat_chart();
       void creat_action();
       void creat_close();


   }


}
