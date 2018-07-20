package com.jeong_woochang.sunrinthon.Retrofit

import android.support.v4.app.Fragment
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.jeong_woochang.sunrinthon.Adapter.RecyclerAdapter
import com.jeong_woochang.sunrinthon.R
import kotlinx.android.synthetic.main.fragment_c2c.view.*
import kotlinx.android.synthetic.main.item_c2c.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

class FragmentC2C : Fragment(),TextToSpeech.OnInitListener{
    private val items = java.util.ArrayList<Data>()
    private var recyclerView: RecyclerView? = null
    private var adapter: RecyclerAdapter? = null
    private var tts: TextToSpeech? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tts = TextToSpeech(context, this)
        speakOut("에스엔에스에서 친구들과 이야기를 나눠보세요")
        val view = inflater.inflate(R.layout.fragment_c2c, container, false)
        view.fab.setOnClickListener {
            items += Data("글 제목", "작성자", "1분 전", "질문있습니다!", "http://18.222.191.92:3000/profile.png")
            recyclerView!!.adapter.notifyDataSetChanged()
        }
        recyclerView = view!!.findViewById(R.id.recyclerView)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        recyclerView!!.adapter = RecyclerAdapter(items, inflater.context)
        adapter = recyclerView!!.adapter as RecyclerAdapter?

        Client.retrofitService.getsnsList().enqueue(object : Callback<ArrayList<snsRepo>> {
            override fun onResponse(call: Call<ArrayList<snsRepo>>?, response: Response<ArrayList<snsRepo>>?) {
                val repo = response!!.body()

                when (response.code()) {
                    200 -> {
                        repo!!.indices.forEach {
                            items += Data(repo[it].id, repo[it].writer, repo[it].date, repo[it].content, repo[it].img)
                            recyclerView!!.adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<snsRepo>>?, t: Throwable?) {
                Log.v("C2cTest", "fail!!")
            }
        })

        return view
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

    override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}