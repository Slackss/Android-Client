package com.jeong_woochang.sunrinthon

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.jeong_woochang.sunrinthon.Retrofit.Client
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

class SignActivity : BaseActivity() {


    override var viewId: Int = R.layout.activity_sign_up
    override var toolbarId: Int? = R.id.toolbar
    override fun onCreate() {


        findViewById<Button>(R.id.sign_btn).setOnClickListener {
            Client.retrofitService.logUp(name_tv.text.toString(), id_tv.text.toString(), pw_tv.text.toString()).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    when (response!!.code()) {
                        200 -> {
                            Toast.makeText(this@SignActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                            finish ()
                        }
                        405 -> Toast.makeText(this@SignActivity, "회원가입 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show()
                        500 -> Toast.makeText(this@SignActivity, "회원가입 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Void>?, t: Throwable?) {

                }


            })
        }
    }
}