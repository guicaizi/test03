package com.yun.software.shangcheng.ui.ViewWidget;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.utils.ImageUtils;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.widget.XColorDrawable;


public class XLoadingDialog extends Dialog {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static XLoadingDialog dialog;
    private Context context;
    private TextView loadingMessage;
    private ProgressBar progressBar;
    private LinearLayout loadingView;
    private XColorDrawable drawable;

    public XLoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
        drawable = new XColorDrawable();
        setContentView(R.layout.xloading_dialog);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        loadingMessage = (TextView) findViewById(R.id.xframe_loading_message);
        progressBar = (ProgressBar) findViewById(R.id.xframe_loading_progressbar);
        loadingView = (LinearLayout) findViewById(R.id.xframe_loading_view);
        loadingMessage.setPadding(15, 0, 0, 0);
        drawable.setColor(Color.WHITE);
        ImageUtils.setBackground(loadingView, drawable);
    }

    public static XLoadingDialog with(Context context) {
        if (dialog == null) {
            dialog = new XLoadingDialog(context);
        }
        return dialog;
    }
    public XLoadingDialog setOrientation(int orientation) {
        loadingView.setOrientation(orientation);
        if (orientation == HORIZONTAL) {
            loadingMessage.setPadding(15, 0, 0, 0);
        } else {
            loadingMessage.setPadding(0, 15, 0, 0);
        }
        return dialog;
    }

    public XLoadingDialog setBackgroundColor(@ColorInt int color) {
        drawable.setColor(color);
        ImageUtils.setBackground(loadingView, drawable);
        return dialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dialog != null)
            dialog = null;
    }

    public XLoadingDialog setCanceled(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
        return dialog;
    }

    public XLoadingDialog setMessage(String message) {
        if (StringUtils.isNotEmpty(message)) {
            loadingMessage.setText(message);
        }
        return this;
    }

    public XLoadingDialog setMessageColor(@ColorInt int color) {
        loadingMessage.setTextColor(color);
        return this;
    }

    public void setDefault() {
        setBackgroundColor(Color.parseColor("#aa000000"));
        setMessageColor(Color.WHITE);
    }
}
