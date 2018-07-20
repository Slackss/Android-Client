package com.jeong_woochang.sunrinthon

import android.Manifest
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.jeong_woochang.sunrinthon.Retrofit.Client
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*


class LoginActivity : BaseActivity(), TextToSpeech.OnInitListener {

    override var viewId: Int = R.layout.activity_login
    override var toolbarId: Int? = R.id.toolbar
    private var tts: TextToSpeech? = null
    override fun onCreate() {

        val permissionlistener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {}

            override fun onPermissionDenied(deniedPermissions: java.util.ArrayList<String>?) {
                Toast.makeText(this@LoginActivity, "권한 거부됨\\n\" ${deniedPermissions.toString()}", Toast.LENGTH_LONG).show()
            }
        }
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("내 위치를 표시하기 위해 장소 권한이 필요합니다")
                .setDeniedMessage("설정] > [권한] 에서 권한을 허용할 수 있습니다.")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                .check()

        tts = TextToSpeech(this, this)

        login_btn.setOnClickListener {
            Client.retrofitService.logIn(id_tv.text.toString(), pw_tv.text.toString()).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                    when (response!!.code()) {
                        200 -> {
                            finish()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            speakOut("로그인 성공. 지금은 메인페이지입니다")
                        }
                        405 -> {
                            Toast.makeText(this@LoginActivity, "로그인 실패 : 아이디나 비번이 올바르지 않습니다", Toast.LENGTH_LONG).show()
                            speakOut("아이디 또는 비밀번호가 틀렸습니다 다시 입력해 주세요")
                        }
                        500 -> {
                            Toast.makeText(this@LoginActivity, "로그인 실패 : 서버 오류", Toast.LENGTH_LONG).show()
                            speakOut("서버 오류입니다")
                        }
                    }
                }

                override fun onFailure(call: Call<Void>?, t: Throwable?) {

                }


            })


        }

        signup_go.setOnClickListener { startActivity(Intent(this@LoginActivity, SignActivity::class.java)) }
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(text:String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
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
