package com.meetyou.cn.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.EditText;

import com.meetyou.cn.mvp.ui.widget.OnCommitListener;
import com.meetyou.library.utils.AppManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;


/**
 * Created by admin on 2018/3/20.
 */

public class MeetYDialogUtils {
    /**
     * 根据类型展示不同QMUIdialog
     *
     * @param iconType QMUITipDialog.Builder.ICON_TYPE_LOADING 加载中
     *                 QQMUITipDialog.Builder.ICON_TYPE_SUCCESS 成功
     *                 QMUITipDialog.Builder.ICON_TYPE_FAIL 失败
     *                 QMUITipDialog.Builder.ICON_TYPE_INFO  警告 感叹号
     * @param content
     * @return
     */
    public static QMUITipDialog createTipDialog(@QMUITipDialog.Builder.IconType int iconType, String content) {
        return new QMUITipDialog.Builder(AppManager.getAppManager().currentActivity())
                .setIconType(iconType)
                .setTipWord(content)
                .create();
    }


    /**
     * 标题 + 内容 + 自定义按钮名称
     *
     * @param title
     * @param content
     * @param actionName
     * @param onCommitListener
     * @return
     */
    public static QMUIDialog createMessageNegativeDialog(String title, String content, String actionName, final OnCommitListener onCommitListener) {
        return new QMUIDialog.MessageDialogBuilder(AppManager.getAppManager().currentActivity())
                .setTitle(title)
                .setMessage(content)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction(0, actionName, QMUIDialogAction.ACTION_PROP_NEGATIVE, (dialog, index) -> {
                    if (onCommitListener != null) {
                        onCommitListener.onCommit(index, null);
                    }
                    dialog.dismiss();
                }).create();
    }


}
