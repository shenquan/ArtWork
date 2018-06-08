package com.example.kotlinmodule

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.TypedValue
import kotlinx.android.synthetic.main.test_activity.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.Ref
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip
import org.jetbrains.anko.px2sp
import org.jetbrains.anko.toast

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
//        val textView = findViewById(R.id.test) as TextView
        val textView = test//或者不用赋值，直接使用
        textView.setOnClickListener {
            //            Toast.makeText(mContext, "点击了我", Toast.LENGTH_SHORT).show()
            toast("点击了我")
//            toast(R.string.app_name)
//            longToast("Wow, such duration")
//            startActivity(intentFor<TestActivity>("id" to 5))
//            startActivity<TestActivity>("id" to 5, "name" to "ActivityName")

//            error("test")

//            alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
//                yesButton { toast("Oh…") }
//                noButton {}
//            }.show()

//            val dialog = progressDialog(message = "Please wait a bit…", title = "Fetching data")

//            snackbar(textView, "1")//有问题
//            snackbar(textView, R.string.app_name)
//            longSnackbar(textView, "Wow, such duration")
//            snackbar(textView, "Action, reaction", "Click me!") {
            //                doStuff()
//            }


//            textView.setTextSize(20f)//默认为sp
//            textView.textSize = 20f

//            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, dip(20).toFloat())

            var size: Float = px2dip(30)
            var size2: Float = px2sp(30)

//            var x: Int = 10
//            var y: Float = x.toFloat()
//            val str2 = null.toString()
            val ref: Ref<TestActivity> = this.asReference()//弱引用
            async(UI) {

            }

        }

    }

}
