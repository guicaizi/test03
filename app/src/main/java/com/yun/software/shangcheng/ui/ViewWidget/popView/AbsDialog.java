package com.yun.software.shangcheng.ui.ViewWidget.popView;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

public abstract class AbsDialog extends Dialog {

	public AbsDialog(Context context) {
		super(context);
	}

	public AbsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public AbsDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
		initData();
		setListener();
	}

	@Override
	public void setContentView(View view) {
		super.setContentView(view);
		initView();
		initData();
		setListener();
	}

	@Override
	public void setContentView(View view, LayoutParams params) {
		super.setContentView(view, params);
		initView();
		initData();
		setListener();
	}

	@SuppressWarnings("deprecation")
	protected void setProperty(int width, int height) {
		Window window = getWindow();
		WindowManager.LayoutParams p = window.getAttributes();
		Display d = getWindow().getWindowManager().getDefaultDisplay();
		p.height = (int) (d.getHeight() * width);
		p.width = (int) (d.getWidth() * height);
		window.setAttributes(p);
	}
	protected void setScreenBgDarken() {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.8f;
		lp.dimAmount = 0.8f;
		getWindow().setAttributes(lp);
	}

	protected abstract void initView();

	protected abstract void initData();

	protected abstract void setListener();


}
