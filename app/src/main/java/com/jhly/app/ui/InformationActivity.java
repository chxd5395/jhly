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


    @Override
    protected void initView() {
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
        mLayout.onLoading();
        OkGo.<String>get(RootUrl.url+"ladingbill/"+code)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        if(response.code()==200) {
                            final String re = response.body();
                            gson = new Gson();
                            info = gson.fromJson(re, InformationUtil.class);
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
                        } else if(response.code() == 302){
                            mLayout.onEmpty();
                            showToast("已预约");
                            Bundle bundle1 = new Bundle();
                            bundle1.putInt("code",302);
                            openActivity(ReserveActivity.class,bundle1);
                        }else if(response.code()==404){
                            Log.e("0","未查到");
                            mLayout.onError();
                        }else if(response.code()==204) {
                            mLayout.onEmpty();
                        }
                    }
                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        mLayout.onError();
                    }
                });
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
