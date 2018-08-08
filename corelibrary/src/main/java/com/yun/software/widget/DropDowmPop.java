package com.yun.software.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yun.software.R;


/**
 * Created by dongjunkun on 2015/6/17.
 */
public class DropDowmPop extends LinearLayout {

    private boolean isopen=false;
    private FrameLayout containerView;
    //弹出菜单父布局
    private FrameLayout popupMenuViews;
    private View maskView;
    //遮罩颜色
    private int maskColor = 0x88888888;
    private float menuHeighPercent = 0.5f;
    public DropDowmPop(Context context) {
        super(context, null);
    }

    public DropDowmPop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDowmPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
        maskColor = a.getColor(R.styleable.DropDownMenu_ddmaskColor, maskColor);
        menuHeighPercent = a.getFloat(R.styleable.DropDownMenu_ddmenuMenuHeightPercent,menuHeighPercent);
        a.recycle();
        containerView = new FrameLayout(context);
        containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(containerView,0);
    }
    /**
     * 初始化DropDownMenu
     *
     * @param popupViews
     * @param contentView
     */
    public void setDropDownMenu(@NonNull View  popupViews, @NonNull View contentView) {
        if (popupViews==null) {
            throw new IllegalArgumentException(" popupViews not null");
        }

        containerView.addView(contentView, 0);
        maskView = new View(getContext());
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        containerView.addView(maskView, 1);
        maskView.setVisibility(GONE);
        if (containerView.getChildAt(2) != null){
            containerView.removeViewAt(2);
        }
        popupMenuViews = new FrameLayout(getContext());
        popupMenuViews.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (DeviceUtils.getScreenSize(getContext()).y)));
        popupMenuViews.setVisibility(GONE);
        containerView.addView(popupMenuViews, 2);
//        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT/2, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupViews.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        popupMenuViews.addView(popupViews);
    }
    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if(!isopen){
            return;
        }
            popupMenuViews.setVisibility(View.GONE);
            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            maskView.setVisibility(GONE);
            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
        isopen=false;
    }
    /**
     * 切换菜单
     *
     */
    public void openMenu() {
        if(isopen){
            return;
        }
                        popupMenuViews.setVisibility(View.VISIBLE);
                        popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                        maskView.setVisibility(VISIBLE);
                        maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                        popupMenuViews.getChildAt(0).setVisibility(View.VISIBLE);
         isopen=true;
    }

}
