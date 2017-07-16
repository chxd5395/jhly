package com.jhly.app.api;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.request.base.Request;

import java.lang.reflect.Type;

/**
 * Created by r on 2017/7/8.
 */

public abstract class DialogCallback extends JsonCallback {
    private ProgressDialog progressDialog;

    public DialogCallback(Activity activity){
        initDialog(activity);
    }

    public void initDialog(Activity activity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请求网络中...");
    }

    @Override
    public void onStart(Request request) {
        if(progressDialog != null || !progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void onFinish() {
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
