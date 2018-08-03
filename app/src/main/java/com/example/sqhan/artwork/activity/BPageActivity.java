package com.example.sqhan.artwork.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class BPageActivity extends BaseActivity {

    @BindView(R.id.open_a_page)
    Button openAPage;
    @BindView(R.id.just_show_text)
    TextView justShowText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b_page_layout);
        ButterKnife.bind(this);

        new Handler().post(() -> {
            int x = 1;
            System.out.println("Java 8 lambda表达式测试，x=" + x);
        });


    }

    @OnClick({R.id.open_a_page, R.id.just_show_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_a_page:
                Intent intent = new Intent(mContext, APageActivity.class);
                startActivity(intent);
                break;
            case R.id.just_show_text:
                break;
        }
    }
}
