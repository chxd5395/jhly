package com.jhly.app.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.jhly.app.BaseActivity;
import com.jhly.app.api.InformationUtil;
import com.jhly.app.R;
import com.jhly.app.api.RootUrl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import me.rawn_hwang.library.widgit.DefaultLoadingLayout;
import me.rawn_hwang.library.widgit.SmartLoadingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InformationActivity extends BaseActivity implements View.OnClickListener {
    private TextView variety;
    private TextView license;
    private TextView weight;
    private TextView company;
    private Button wrong;
    private Button right;
    private InformationUtil info;
    private DefaultLoadingLayout mLayout;
    private Gson gson;
    private String cookie;


    @Override
    protected void initView() {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_information,null);
        setContentView(view);
        variety = findview(R.id.tv_variety);
        license = findview(R.id.tv_license);
        weight = findview(R.id.tv_weight);
        company = findview(R.id.tv_company);
        wrong = findview(R.id.bt_wrong);
        right = findview(R.id.bt_right);
        mLayout = SmartLoadingLayout.createDefaultLayout(this,view);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String code = bundle.getString("result");
        Log.d("RE",code);
        mLayout.onLoading();
        OkGo.<String>get(RootUrl.url+"ladingbill/"+code)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        if(response.code()==200) {
                            final String re = response.body();
                            gson = new Gson();
                            info = gson.fromJson(re, InformationUtil.class);
                            Log.d("re", re);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    variety.setText(info.getScode());
                                    license.setText(info.getVehicle());
                                    weight.setText(info.getWeight());
                                    company.setText(info.getAim());
                                    mLayout.onDone();
                                }
                            });
                        }else if(response.code()==404){
                            Log.e("0","未查到");
                            //mLayout.onEmpty();
                        }else if(response.code()==204) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLayout.onEmpty();
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        mLayout.onError();
                    }
                });
//        OkHttpClient mOkHttpClient = new OkHttpClient();
//        //创建一个Request
//        final Request request = new Request.Builder()
//                .get()
//                .url(RootUrl.url+"ladingbill/"+code)
//                .build();
//        //new call
//        Call call = mOkHttpClient.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                runOnUiThread( new Runnable() {
//                    @Override
//                    public void run() {
//                        mLayout.onError();
//                    }
//                });
//                //
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                cookie = response.header("Set-Cookie");
//                SharedPreferences preferences = getSharedPreferences("cookie", MODE_PRIVATE);
//                SharedPreferences.Editor edit = preferences.edit();
//                edit.putString("cookie",cookie);
//                edit.commit();
//                if(response.code()==200) {
//                    final String re = response.body().string();
//
//                    gson = new Gson();
//                    info = gson.fromJson(re, InformationUtil.class);
//                    Log.d("re", re);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            variety.setText(info.getScode());
//                            license.setText(info.getVehicle());
//                            weight.setText(info.getWeight());
//                            company.setText(info.getAim());
//                            mLayout.onDone();
//                        }
//                    });
//                }else if(response.code()==404){
//                    Log.e("0","未查到");
//                    //mLayout.onEmpty();
//                }else if(response.code()==204) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mLayout.onEmpty();
//                        }
//                    });
//                }
//                else
//                {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            new AlertDialog.Builder(InformationActivity.this).setTitle("提示").setMessage("已预约").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                   InformationActivity.this.finish();
//                                }
//                            }).show();
//                        }
//                    });
//                    Log.d("1","已预约");
//                }
//
//
//            }
//        });
   }

    @Override
    protected void initListener() {
        wrong.setOnClickListener(this);
        right.setOnClickListener(this);
        mLayout.setErrorButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //正确后直接请求计划
            case R.id.bt_right:
                final AlertDialog.Builder alertDialog = new Builder(this);
                alertDialog.setTitle("提示");
                alertDialog.setMessage("确定提交？");
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openActivity(PlanActivity.class);
                    }
                });
                alertDialog.show();
                break;
            //错误后重新扫码请求
            case R.id.bt_wrong:
                showToast("计划有误，请重试");
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
