package com.yun.software.shangcheng.ui.lisenter;

import android.view.View;

/**
 * Created by yanliang
 * on 2018/5/22 11:41
 */

public interface ZongStateListener {
    void player(int postion);
    void zan(View view, int postion);
    void detail(int postion);
    void comment(int postion);
    void attention(int postion);
    void createrinfor(int postion);

}
