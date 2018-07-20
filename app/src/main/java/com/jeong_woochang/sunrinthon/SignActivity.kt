package com.jeong_woochang.sunrinthon

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.jeong_woochang.sunrinthon.Retrofit.Client
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

class SignActivity : BaseActivity(), TextToSpeech.OnInitListener {


    override var viewId: Int = R.layout.activity_sign_up
    override var toolbarId: Int? = R.id.toolbar
    private var tts: TextToSpeech? = null
    override fun onCreate() {

        tts = TextToSpeech(this, this)
        findViewById<Button>(R.id.sign_btn).setOnClickListener {
            Client.retrofitService.logUp(name_tv.text.toString(), id_tv.text.toString(), pw_tv.text.toString()).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    when (response!!.code()) {
                        200 -> {
                            Toast.makeText(this@SignActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                            finish()
                            speakOut("회원가입 성공 즐거운 시간 되세요")
                        }
                        405 -> {
                            Toast.makeText(this@SignActivity, "회원가입 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show()
                            speakOut("회원가입 실패")
                        }
                        500 -> {
                            Toast.makeText(this@SignActivity, "회원가입 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                            speakOut("서버 오류");
                        }
                    }
                }

                override fun onFailure(call: Call<Void>?, t: Throwable?) {

                }


            })
        }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            } else {
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}