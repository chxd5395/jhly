package com.jhly.app.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jhly.app.BaseActivity;
import com.jhly.app.R;

public class ReserveActivity extends BaseActivity {

    @Override
    protected void initView() {
        setContentView(R.layout.activity_reserve);
        getToolbarTitle().setText("提单预约");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
