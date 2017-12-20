package com.lzy.mywheelsthree.http.loading1;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.mywheelsthree.R;


/**
 * description:弹窗浮动加载进度条
 * Created by bullet on 2017/11/14.
 */

public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private static Dialog mLoadingDialog;
    private static AVLoadingIndicatorView indicatorview;
    private static TextView textView;

//    /**
//     * 显示加载对话框
//     * @param activity 上下文
//     * @param msg 对话框显示内容
//     * @param cancelable 对话框是否可以取消
//     */
//    public static Dialog showDialogForLoading(Activity activity, String msg, boolean cancelable) {
//        if (mLoadingDialog == null) {
//            View view = LayoutInflater.from(activity).inflate(R.layout.view_loading_one, null, false);
//            indicatorview = (AVLoadingIndicatorView) view.findViewById(R.id.indicator);
//            indicatorview.setIndicator(new BallSpinFadeLoaderIndicator());
//            indicatorview.show();
//            textView = view.findViewById(R.id.loading_one_text);
//            textView.setVisibility(View.VISIBLE);
//            textView.setText(msg);
//            mLoadingDialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
//            mLoadingDialog.setCancelable(cancelable);
//            mLoadingDialog.setCanceledOnTouchOutside(false);
//            mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT));
//            mLoadingDialog.show();
//        }
//        return mLoadingDialog;
//    }

    public static Dialog showDialogForLoading(Activity activity) {

        View view = LayoutInflater.from(activity).inflate(R.layout.view_loading_one, null, false);
        indicatorview =  view.findViewById(R.id.indicator);
        indicatorview.setIndicator(new BallSpinFadeLoaderIndicator());
        indicatorview.show();
        mLoadingDialog = new Dialog(activity, R.style.ActionSheetDialogStyle);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();

        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     **/
    public static void cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog.dismiss();
            if (indicatorview != null) indicatorview.hide();

            mLoadingDialog = null;
            indicatorview = null;
        }
    }
}
