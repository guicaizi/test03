package com.yun.software.shangcheng.ui.ViewWidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.utils.StringUtils;


/**
 * 仿IOS的弹出框
 *
 * @来自网上
 */
public class UIAlertOtherView extends Dialog {

    private Context context;
    private String title;
    private String bottomText;
    private ClickSureListenerInterface clicksureListenerInterface;

    public UIAlertOtherView(Context context, String title,String bottomeText) {
        super(context, R.style.UIAlertViewStyle);
        this.context = context;
        this.title = title;
        this.bottomText=bottomeText;
    }

    public interface ClickSureListenerInterface {

        public void doBottom();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        inite();
    }

    public void inite() {
        setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ui_alert_view_other, null);
        setContentView(view);
        TextView tvSure = (TextView) view.findViewById(R.id.tvsure);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);

        if (StringUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }
        tvSure.setText(bottomText);
        tvSure.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    public void setClicklistener(ClickSureListenerInterface clickListenerInterface) {
        this.clicksureListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            int id = v.getId();
            switch (id) {
                case R.id.tvsure:
                    clicksureListenerInterface.doBottom();
                    break;
                default:
                    break;
            }
        }
    }

    ;
}
