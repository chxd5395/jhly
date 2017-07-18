package com.jhly.app.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.jhly.app.BaseActivity;
import com.jhly.app.R;
import com.jhly.app.adapter.FirstPageRecycleViewAdapter;
import com.jhly.app.api.MyDividerItemDecoration;
import com.jhly.app.boardcast.NetBroadCast;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
import java.util.Stack;



public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQEST_CODE = 1;
    protected long lastClickTime;//记录上次点击按钮时间
    protected final static int CLICK_TIME = 500;//按钮连续点击最短时间间隔
    protected Stack<Activity> stack = new Stack<>();//activity栈
    private RecyclerView recyclerView;
//    private Button scan;//扫码
//    private Button mes;//个人信息
//    private Button show;
    private Button yes;
    private EditText scan_code;
    private NetBroadCast broadcast = new NetBroadCast();
    private FirstPageRecycleViewAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        yes = findview(R.id.bt_yes);
        scan_code = findview(R.id.et_scan);
        recyclerView = findview(R.id.rv_firstpage);
        getToolbar().inflateMenu(R.menu.menu_main);
        getToolbarTitle().setText("主界面");
        getSubTitle().setText("更多");
        getSubTitle().setVisibility(View.GONE);
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @Override
    protected void initData() {
        stack.push(this);
        ZXingLibrary.initDisplayOpinion(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);}
        //broadcast 注册
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(broadcast,filter);
        int[] bitmaps = {R.drawable.barcode_shortcut,R.drawable.yuyue,R.drawable.recode};
        String[] stringList = {"提单扫码","我的预约","提单记录"};
        adapter = new FirstPageRecycleViewAdapter(bitmaps, stringList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        yes.setOnClickListener(this);
        adapter.setOnItemClickListener(new FirstPageRecycleViewAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        //6.0以上动态申请权限
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //申请WRITE_EXTERNAL_STORAGE权限
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                                        1);}else{
                                Intent tent = new Intent(MainActivity.this,CaptureActivity.class);
                                startActivityForResult(tent,REQEST_CODE);
                            }
                        }else {
                            Intent tent = new Intent(MainActivity.this,CaptureActivity.class);
                            startActivityForResult(tent,REQEST_CODE);
                        }
                        break;
                    case 1:
                        openActivity(PlanActivity.class);
                        break;
                    case 2:
                        openActivity(ShowActivity.class);
                        break;
                }
            }
        });
        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                                1);}else{
                        Intent tent = new Intent(MainActivity.this,CaptureActivity.class);
                        startActivityForResult(tent,REQEST_CODE);
                    }
                }else {
                    Intent tent = new Intent(MainActivity.this,CaptureActivity.class);
                    startActivityForResult(tent,REQEST_CODE);
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_yes:
                if(!scan_code.getText().toString().isEmpty()){
                    Bundle bd = new Bundle();
                    String result = scan_code.getText().toString().trim();
                    bd.putString("result",result);
                    openActivity(InformationActivity.class,bd);}else {
                    showToast("请输入提单号");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    Bundle bd = new Bundle();
                    bd.putString("result",result);
                    openActivity(InformationActivity.class,bd);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent tent = new Intent(MainActivity.this,CaptureActivity.class);
                    startActivityForResult(tent,REQEST_CODE);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "请在应用管理中打开相机的权限！", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(this, "请在应用管理中打开储存空间的权限！", Toast.LENGTH_SHORT).show();
                    finish();
                }

        }
    }

    /**
     * 验证上次点击按钮时间，防止重复点击
     */
    public boolean verifyClickTime(){
        if(System.currentTimeMillis() - lastClickTime <= CLICK_TIME){
            return false;
        }
        lastClickTime = System.currentTimeMillis();
        return true;
    }

    /**
     * 双击退出程序
     */
    protected long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_BACK == keyCode){
            //判断是否在两秒内连续点击
            if(System.currentTimeMillis() - exitTime > 2000){
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            }else{
                finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void finishAll(){
        for(int i = 0;i< stack.size();i++) {
            Activity activity = stack.pop();
            activity.finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}
