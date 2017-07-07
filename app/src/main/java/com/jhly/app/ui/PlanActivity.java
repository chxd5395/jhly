package com.jhly.app.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jhly.app.BaseActivity;
import com.jhly.app.api.CheckData;
import com.jhly.app.api.JsonCallback;
import com.jhly.app.api.Plan;
import com.jhly.app.R;
import com.jhly.app.api.RootUrl;
import com.jhly.app.adapter.MyAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.rawn_hwang.library.widgit.DefaultLoadingLayout;
import me.rawn_hwang.library.widgit.SmartLoadingLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PlanActivity extends BaseActivity {
    private ListView time;
    private EditText number;
    private EditText name;
    private EditText card;
    private Button submit;
    private MyAdapter adapter;
    private DefaultLoadingLayout layout;
    private ArrayList<Plan> list = new ArrayList();
    private Handler handler;
    private String cookie;
    private View view = null;
    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    @Override
    protected void initView() {
        view = getLayoutInflater().inflate(R.layout.activity_plan, null);
        setContentView(view);
        WindowManager systemService = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        systemService.getDefaultDisplay().getHeight();
        time = findview(R.id.lv_time);
        number = findview(R.id.et_number);
        name = findview(R.id.et_name);
        card = findview(R.id.et_card);
        submit = findview(R.id.bt_submit);
        getToolbarTitle().setText("计划");
        getSubTitle().setText("更多");
        layout = SmartLoadingLayout.createDefaultLayout(this,time);
        layout.onLoading();
        SharedPreferences info = getSharedPreferences("info", 0);
        name.setText(info.getString("name",null));
        number.setText(info.getString("num",null));
        card.setText(info.getString("card",null));
    }

    @Override
    protected void initData() {
        //进行联网获取listview数据
        SharedPreferences last_cookie = getSharedPreferences("cookie", 0);
        cookie = last_cookie.getString("cookie", null);
        Type type = new TypeToken<ArrayList<Plan>>() {
        }.getType();
        OkGo.<ArrayList<Plan>>get(RootUrl.url+"availablePlan")
                .execute(new JsonCallback<ArrayList<Plan>>(type) {
                             @Override
                             public void onSuccess(com.lzy.okgo.model.Response<ArrayList<Plan>> response) {
                                 list = response.body();
                                 adapter = new MyAdapter(list,getApplicationContext());
                                 time.setAdapter(adapter);
                                 layout.onDone();
                             }
                });
    }

    @Override
    protected void initListener() {
        time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //onSelected = i+1;
                int id = list.get(i).getId();
                SharedPreferences choose_id = getSharedPreferences("choose_id", MODE_PRIVATE);
                choose_id.edit().putInt("id",id).commit();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString("name",name.getText().toString());
                edit.putString("num",number.getText().toString());
                edit.putString("card",card.getText().toString());
                //进行正则匹配
                if(CheckData.isName(name.getText().toString())&&CheckData.isPhone(number.getText().toString())&&CheckData.isVehicle(card.getText().toString())){
                        edit.commit();
                    //进行数据提交
                    postData();
                    }else{
                    if(!CheckData.isName(name.getText().toString())){
                        showToast("请输入正确的姓名");
                    }if(!CheckData.isPhone(number.getText().toString()) ){
                        showToast("请输入正确的号码");
                    }if(!CheckData.isVehicle(card.getText().toString())) {
                        showToast("请输入正确的车牌");
                    }
                }
                }
        });
    }

    public void postData(){
        SharedPreferences choose_id = getSharedPreferences("choose_id", 0);
        int id = choose_id.getInt("id",0);
        Map<String,String> map = new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("phone",number.getText().toString());
        Gson gson = new Gson();
        String json = gson.toJson(map);
        Log.d("json",json);
        Log.i("vehicle->",card.getText().toString());
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(JSON, json);
        OkGo.<String>post(RootUrl.url+"submit/"+id+"?vehicle="+card.getText().toString())
                .upJson(json)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        //此处判断获取数据成功与否
                        String result = response.body();
                        final int code = response.code();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (code){
                                    case 302:
                                        new AlertDialog.Builder(PlanActivity.this).setTitle("提示").setMessage("已预约").setPositiveButton("确定",null).show();
                                        break;
                                    case 200:
                                        new AlertDialog.Builder(PlanActivity.this).setTitle("提示").setMessage("预约成功").setPositiveButton("确定",null).show();
                                        break;
                                    case 400:
                                        new AlertDialog.Builder(PlanActivity.this).setTitle("提示").setMessage("预约失败").setPositiveButton("确定",null).show();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                    }
                });
        //创建一个Request
//        final Request request = new Request.Builder()
//                .post(requestBody)
//                .addHeader("cookie",cookie)
//                .url(RootUrl.url+"submit/"+id+"?vehicle="+card.getText().toString())
//                .build();
//        //new call
//        Call call = client.newCall(request);
//        //请求加入调度
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //此处判断获取数据成功与否
//                String result = response.body().string();
//                final int code = response.code();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        switch (code){
//                            case 302:
//                                new AlertDialog.Builder(PlanActivity.this).setTitle("提示").setMessage("已预约").setPositiveButton("确定",null).show();
//                                break;
//                            case 200:
//                                new AlertDialog.Builder(PlanActivity.this).setTitle("提示").setMessage("预约成功").setPositiveButton("确定",null).show();
//                                break;
//                            case 400:
//                                new AlertDialog.Builder(PlanActivity.this).setTitle("提示").setMessage("预约失败").setPositiveButton("确定",null).show();
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode,event);
    }
}
