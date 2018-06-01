package com.example.sqhan.artwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.example.sqhan.artwork.R;
import com.example.sqhan.artwork.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sqhan on 2018/6/1
 * <p>
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

public class APageActivity extends BaseActivity {

    @BindView(R.id.open_b_page)
    Button openBPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_page_layout);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.open_b_page)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, BPageActivity.class);
        startActivity(intent);

    }
}
