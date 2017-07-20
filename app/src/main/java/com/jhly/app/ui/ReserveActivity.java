package com.jhly.app.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jhly.app.BaseActivity;
import com.jhly.app.R;
import com.jhly.app.api.DialogCallback;
import com.jhly.app.api.Result;
import com.jhly.app.api.RootUrl;
import com.jhly.app.sqlLite.OrderDb;
import com.jhly.app.view.MyTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class ReserveActivity extends BaseActivity implements View.OnClickListener {
    private MyTextView mtv;
    private TextView success;
    private TextView info;
    private Button cancel;
    private ImageButton refresh;
    private ViewPager vp;
    private List<View> viewList;//资源的集合
    private ViewGroup vg;//放置圆点
    //实例化原点View
    private ImageView iv_point;
    private ImageView []ivPointArray;

    private boolean isLooper;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            final Result result = (Result) msg.obj;
            try {
                        Log.d("标记","插入数据");
                        new OrderDb(ReserveActivity.this,result).insert(result.getCv().getId(),result.getCv().getName(),result.getCv().getPhone());
                        Log.d("标记1","插入成功");
                mtv.setText("我的车牌号:\n"+result.getLv().getVehicle());
                info.setText("尊敬的客户: " + result.getCv().getName() + " 您预订的是: " + result.getPv().getSrc() + "\n下单时间为: " + result.getTime() + "\n提货时间为: " + result.getPv().getBeginTime() + "--" + result.getPv().getEndTime() + "\n请您及时到达！");
            }catch (Exception e){
                mtv.setText("我的车牌号:\n获取失败...");
                success.setText("此提单已撤销，无需重复操作！");
                success.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reserve);
        getToolbarTitle().setText("提单预约");
        getSubTitle().setText("更多");
        mtv = findview(R.id.mtv);
        success = findview(R.id.appCompatTextView);
        info = findview(R.id.tv_info);
        cancel = findview(R.id.bt_cancel);
        refresh = findview(R.id.ib_refresh);

    }

    @Override
    protected void initData() {
        int code = getIntent().getExtras().getInt("code",0);
        switch (code){
            case 0:
            case 200:
            case 201:
                success.setVisibility(View.VISIBLE);
                OkGo.<String>get(RootUrl.url+"show")
                        .tag(this)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                Gson gson = new Gson();
                                Result result = gson.fromJson(response.body(),Result.class);
                                Message message = new Message();
                                message.obj = result;
                                handler.sendMessage(message);
                            }
                            @Override
                            public String convertResponse(Response response) throws Throwable {
                                return super.convertResponse(response);
                            }
                        });
                break;
            case 400:
                success.setText("预约失败!");
                success.setVisibility(View.VISIBLE);
                break;
            case 302:
                success.setText("已预约");
                success.setVisibility(View.VISIBLE);
                OkGo.<String>get(RootUrl.url+"show")
                        .tag(this)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                Gson gson = new Gson();
                                Result result = gson.fromJson(response.body(),Result.class);
                                Message message = new Message();
                                message.obj = result;
                                handler.sendMessage(message);
                            }
                            @Override
                            public String convertResponse(Response response) throws Throwable {
                                return super.convertResponse(response);
                            }
                        });
            default:
                break;
        }
    }

    @Override
    protected void initListener() {
        cancel.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_cancel:
                new AlertDialog.Builder(this).setTitle("提示").setMessage("确定撤回？数据将不可更改").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OkGo.<Activity>delete(RootUrl.url+"revoke")
                                .execute(new DialogCallback(ReserveActivity.this) {
                                    @Override
                                    public void onSuccess(com.lzy.okgo.model.Response response) {
                                        if(204 == response.code()){
                                            Toast.makeText(ReserveActivity.this,"撤销成功",Toast.LENGTH_SHORT).show();
                                            success.setText("已撤销");
                                            success.setVisibility(View.VISIBLE);
                                        }else{
                                            //撤销失败
                                            success.setText("撤销失败");
                                            success.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onError(com.lzy.okgo.model.Response response) {
                                        super.onError(response);
                                        success.setText("撤销失败");
                                        success.setVisibility(View.VISIBLE);
                                    }
                                });
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
                break;
            case R.id.ib_refresh:
                initData();
        }
    }

}
