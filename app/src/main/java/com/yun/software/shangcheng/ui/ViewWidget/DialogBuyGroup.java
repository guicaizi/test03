package com.yun.software.shangcheng.ui.ViewWidget;


import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun.software.shangcheng.R;
import com.yun.software.shangcheng.ui.ViewWidget.popView.AbsDialog;
import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DialogBuyGroup extends AbsDialog {
    @Bind(R.id.iv_dialog_icon)
    ImageView ivDialogIcon;
    @Bind(R.id.tv_dialog_name)
    TextView tvDialogName;
    @Bind(R.id.tv_dialog_money)
    TextView tvDialogMoney;
    @Bind(R.id.tv_dialog_size)
    TextView tvDialogSize;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.lin_top)
    LinearLayout linTop;
    @Bind(R.id.tag_2)
    TextView tag2;
    @Bind(R.id.number_button)
    NumberButton numberButton;
    @Bind(R.id.tv_tag_commit)
    TextView tvTagCommit;
    @Bind(R.id.et_group_phone)
    EditText etGroupPhone;
    @Bind(R.id.et_group_quan)
    EditText etGroupQuan;
    private Activity context;
    private CommitDatelistente poplistenter;


    public DialogBuyGroup(Activity context) {
        super(context, R.style.dialog_normal);
        this.context = context;
        setContentView(R.layout.dialog_buy_group);
        getWindow().setGravity(Gravity.TOP);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    @Override
    protected void initView() {
        ButterKnife.bind(this,getWindow().getDecorView());
        etGroupPhone.setText("123");

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvTagCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commetdate();
                dismiss();
            }
        });

    }
    public void commetdate(){
        String phone=etGroupPhone.getText().toString();
        String quan=etGroupQuan.getText().toString();
        if(StringUtils.isEmpty(phone)){
            Tools.showInfo(context,"电话号码不能为空");
            return;

        }
        if(!StringUtils.isMobile(phone)){
            Tools.showInfo(context,"电话号码格式有误");
            return;

        }
        String number=numberButton.getNumber()+"";

        poplistenter.commitdate(phone,quan,number);
    }



    public void setDatelistenter(CommitDatelistente  poplistenter){
        this.poplistenter=poplistenter;
    }
    public interface CommitDatelistente{
            void commitdate(String phone, String quan, String number);


    }
}
