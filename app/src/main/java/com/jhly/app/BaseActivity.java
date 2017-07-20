package com.jhly.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {
    protected TextView mTitle;
    protected TextView mSubTitle;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = findview(R.id.toolbar);
        mTitle = findview(R.id.toolbar_title);
        mSubTitle = findview(R.id.toolbar_subtitle);
        if(mToolbar != null){
            setSupportActionBar(mToolbar);
        }if(mTitle != null){
            mTitle.setText(getTitle());
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        initView();
        initData();
        initListener();

        setNavigationBarStatusBarTranslucent(this);
    }

    protected abstract void initView();
    protected abstract void initData();
    protected abstract void initListener();

    @Override
    protected void onStart() {
        super.onStart();
        if(null != getToolbar() && isShowBacking()){
            showBack();
        }
    }

    protected void showBack() {
        //getToolbar().setNavigationIcon(R.mipmap.back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected  boolean isShowBacking(){
        return true;
    }

    public Toolbar getToolbar(){
        return findview(R.id.toolbar);
    }

    public TextView getToolbarTitle(){
        return findview(R.id.toolbar_title);
    }

    public TextView getSubTitle(){
        return findview(R.id.toolbar_subtitle);
    }

    /**
     * activity跳转
     */
    public void openActivity(Class<?> targetActivity){
        openActivity(targetActivity,null);
    }

    public void openActivity(Class<?> targetActivityClass,Bundle bundle){
        Intent intent = new Intent(this,targetActivityClass);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



    /**
     * 使用findview避免强转
     */
    public<T> T findview(int id){
        T view = (T)findViewById(id);
        return view;
    }

    /**
     * 打印Toast
     */
    public void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 导航栏，状态栏透明
     * @param activity
     */
    public void setNavigationBarStatusBarTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
