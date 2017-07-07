package com.jhly.app.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.jhly.app.BaseActivity;
import com.jhly.app.R;
import com.jhly.app.api.Result;
import com.jhly.app.api.RootUrl;
import com.jhly.app.adapter.MyRecycleViewAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;


import okhttp3.Response;

public class ShowActivity extends BaseActivity implements View.OnClickListener {
    private String revoke_url = "http://192.168.1.168/subscribe/revoke";
    private Button show;
    private Button revoke;
    //private String cookie;
    private RecyclerView recyclerView;
    private MyRecycleViewAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Result result = (Result) msg.obj;
            adapter = new MyRecycleViewAdapter(result,ShowActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
            recyclerView.setAdapter(adapter);
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_show);
        show = findview(R.id.bt_show);
        revoke = findview(R.id.bt_revoke);
        recyclerView = findview(R.id.rv_0);
    }

    @Override
    protected void initData() {
        getSubTitle().setText("更多");
        getToolbarTitle().setText("已预订计划");
        getSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("已订");
            }
        });

    }

    @Override
    protected void initListener() {
        show.setOnClickListener(this);
        revoke.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_show:
                SharedPreferences sp = getSharedPreferences("cookie", 0);
                String cookie = sp.getString("cookie",null);
                if(null == cookie)
                    new AlertDialog.Builder(this).setTitle("提示").setMessage("请先扫码").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    }).show();
            OkGo.<String>get(RootUrl.url+"show")
                    .tag(this)
                    .headers("cookie",cookie)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                            Message message = new Message();
                            Gson gson = new Gson();
                            Result result = gson.fromJson(response.body(),Result.class);
                            message.obj = result;
                            handler.sendMessage(message);
                        }
                        @Override
                        public String convertResponse(Response response) throws Throwable {
                            return super.convertResponse(response);
                        }
                    });
                break;
            case R.id.bt_revoke:
                OkGo.<Dialog>delete(RootUrl.url+"revoke")
                        .execute(new Callback<Dialog>() {
                            @Override
                            public void onStart(Request<Dialog, ? extends Request> request) {

                            }

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<Dialog> response) {

                            }

                            @Override
                            public void onCacheSuccess(com.lzy.okgo.model.Response<Dialog> response) {

                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<Dialog> response) {

                            }

                            @Override
                            public void onFinish() {

                            }

                            @Override
                            public void uploadProgress(Progress progress) {

                            }

                            @Override
                            public void downloadProgress(Progress progress) {

                            }

                            @Override
                            public Dialog convertResponse(Response response) throws Throwable {
                                return null;
                            }
                        });

                break;
            default:
                break;
        }
    }


}
