package com.example.kotlinmodule

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.widget.TextView
import android.widget.Toast

/**
 * Created by sqhan on 2018/6/7
 *
 *
 * 站在顶峰，看世界
 * 落在谷底，思人生
 */

class TestActivity : FragmentActivity() {
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity)
        mContext = this
        initIndex()
    }

    private fun initIndex() {
        initView()
    }

    private fun initView() {
        val textView = findViewById(R.id.test) as TextView
        textView.setOnClickListener { Toast.makeText(mContext, "点击了我", Toast.LENGTH_SHORT).show() }

        var x: Int = 1 * 2
        var y: Int = 10 * 2


    }

}
