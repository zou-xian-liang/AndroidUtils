package com.ds.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 键盘工具类
 *
 * 注：抽取到工具类中，在华为手机上可能会出现隐藏键盘无效的情况
 *      可将该方法直接放在需要设置键盘隐藏的Activity或者Fragment中
 */
public class KeyBoardUtil {

    private static boolean isOpen;
    /**
     * 显示软键盘
     */
    public static void openSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(et, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(EditText et) {
        InputMethodManager inputMethodManager = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏当前界面的键盘
     *
     * 此种方法不需要知道当前界面是哪个View有焦点
     * @param activity
     */
    public static void hideSoft(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 键盘是否可见
     * @param activity
     * @return  true：显示
     *          false：没显示
     */
    public static boolean isOpen(final Activity activity){
        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            // 获取当前页面窗口的显示范围
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int screenHeight = ScreenUtils.getScreenHeight(activity);
            int keyboardHeight = screenHeight - rect.bottom; // 输入法的高度
            if (Math.abs(keyboardHeight) > screenHeight / 5) {//键盘可见
                isOpen = true;
            } else {//键盘不可见
                isOpen = false;
            }
        });

        return isOpen;
    }
}
