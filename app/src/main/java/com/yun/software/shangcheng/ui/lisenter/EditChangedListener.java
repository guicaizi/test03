package com.yun.software.shangcheng.ui.lisenter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.yun.software.shangcheng.ui.utils.StringUtils;
import com.yun.software.shangcheng.ui.utils.Tools;


/**
 * 限制 输入框的 长度
 */
public class EditChangedListener implements TextWatcher {
    private Context context;
    private EditText editText;
    private CharSequence temp;// 监听前的文本
    private int editStart;// 光标开始位置
    private int editEnd;// 光标结束位置
    private int charMaxNum = 0;
    private String addString;
    private int maxlength;

    /**
     * @param context  上下文
     * @param editText 监听的输入框
     * @param maxNum   最多输入的字数
     */
    public EditChangedListener(Context context, EditText editText, int maxNum) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.editText = editText;
        this.charMaxNum = maxNum;

    }

    public EditChangedListener(Context context, EditText editText, int maxNum, int maxlength) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.editText = editText;
        this.charMaxNum = maxNum;
        this.maxlength = maxlength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i("yang", "输入文本之前的状态" + s + "start" + start + "count" + count + "after" + after);
        temp = s;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i("yang", "输入文字中的状态" + s + "start" + start + "count" + count + "before" + before);
        //		Log.i("yang", "输入文字中的状态，count是一次性输入字符数");
        //		 mTvAvailableCharNum.setText("还能输入" + (charMaxNum - s.length()) +
        //		 "字符");
        //		if (s.length()>=charMaxNum) {
        //			Tools.showInfo(context,"最多输入"+charMaxNum +
        //					 "个字符");
        //		}
    }
    long submitImageTIme;
    boolean cacluttime=true;
    @Override
    public void afterTextChanged(Editable s) {
        Log.i("yang", "输入文字后的状态" + s);
        editStart = editText.getSelectionStart();
        editEnd = editText.getSelectionEnd();
        if (temp.length() > charMaxNum) {
            if(cacluttime){
                Tools.showInfo(context, "你最多输入" + charMaxNum + "个字符");
                submitImageTIme = System.currentTimeMillis();
                cacluttime=false;
            }
            final long margintime = StringUtils.showTimMargin(submitImageTIme);
            if(margintime>3){
                Tools.showInfo(context, "你最多输入" + charMaxNum + "个字符");
                cacluttime=true;
            }
            s.delete(editStart - 1, editEnd);
            editText.setText(s);
            editText.setSelection(editText.getText().toString().length());
        }
    }
}