package com.yun.software.shangcheng.ui.ViewWidget.popView;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yun.software.shangcheng.R;


public class DialogBookShelfQuit extends AbsDialog {
	private Activity context;
    private CreateAllpopClickListener  poplistenter;
    private LinearLayout lin_action;
    private LinearLayout lin_chatting;
    private LinearLayout lin_video;
    private ImageView img_create_close;
	public DialogBookShelfQuit(Activity context) {
		super(context, R.style.dialog_normal);
		this.context = context;
		setContentView(R.layout.dialog_touxiang);
		setProperty(1, 1);
        setScreenBgDarken();

	}
    public void setPoplistenter(CreateAllpopClickListener  poplistenter){
        this.poplistenter=poplistenter;
    }
    public interface CreateAllpopClickListener{

        void creat_chart();
        void creat_action();
        void creat_close();
        void creat_vidoe();


    }
	@Override
	protected void initView() {
        lin_action=(LinearLayout) findViewById(R.id.lin_action);
        lin_chatting=(LinearLayout) findViewById(R.id.lin_chatting);
        lin_video=(LinearLayout) findViewById(R.id.lin_upload_video);
        img_create_close=(ImageView) findViewById(R.id.img_close_create);

    }

	@Override
	protected void initData() {
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}
	
	@Override
	protected void setListener() {
        lin_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poplistenter.creat_action();
            }
        });
        lin_chatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poplistenter.creat_chart();
            }
        });
        lin_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poplistenter.creat_vidoe();
            }
        });
        img_create_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poplistenter.creat_close();
            }
        });

		
	}



}
