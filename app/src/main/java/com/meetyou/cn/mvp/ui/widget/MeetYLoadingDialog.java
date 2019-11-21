package com.meetyou.cn.mvp.ui.widget;

import com.meetyou.cn.utils.MeetYDialogUtils;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * Created by admin on 2018/3/20.
 */

public class MeetYLoadingDialog extends AbsDialogView {

    QMUITipDialog qmuiDialog;

    @Override
    public void showDialog() {
        if(qmuiDialog == null){
            qmuiDialog = MeetYDialogUtils.createTipDialog(QMUITipDialog.Builder.ICON_TYPE_LOADING,"正在加载");
        }
        qmuiDialog.show();
    }

    @Override
    public void dismiss() {
        if(qmuiDialog!=null&&qmuiDialog.isShowing()){
            qmuiDialog.dismiss();
        }
    }
}
