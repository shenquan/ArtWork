package com.example.sqhan.artwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;
import com.example.sqhan.artwork.service.SimpleService;

/**
 * Created by sqhan on 2018/5/3.
 */

public class SimpleServiceActivity extends BaseActivity implements View.OnClickListener {

    private Button startService;
    private Button stopService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.simple_servie_layout);
        startService = (Button) findViewById(R.id.startService);
        stopService = (Button) findViewById(R.id.stopService);

    }

    @Override
    protected void initEvents() {
        super.initEvents();
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, SimpleService.class);
        if (v.getId() == R.id.startService) {
            startService(intent);
        } else if (v.getId() == R.id.stopService) {
            stopService(intent);
        }
    }

}
