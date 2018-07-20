package com.jeong_woochang.sunrinthon.Adapter


import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jeong_woochang.sunrinthon.ContentActivity
import com.jeong_woochang.sunrinthon.MainActivity
import com.jeong_woochang.sunrinthon.R
import com.jeong_woochang.sunrinthon.Retrofit.Data
import com.jeong_woochang.sunrinthon.WriteActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_c2c.view.*
import java.util.*


internal class RecyclerAdapter(private val dataList: ArrayList<Data>, private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.Holder>(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {

        with(holder.itemView) {
            val data = dataList[position]

            writeTv.text = data.writer
            dateTv.text = data.content
            contentTv.text = data.date
            Picasso.with(context)
                    .load(data.img)
                    .into(imageIcon)
            writeTv.setOnLongClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
                return@setOnLongClickListener true
            }

            writeTv.setOnClickListener {
                var intent=Intent(context, ContentActivity::class.java)
                intent.putExtra("title",writeTv.text.toString())
                intent.putExtra("content",dateTv.text.toString())
            }

            dateTv.setOnLongClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
                return@setOnLongClickListener true
            }

            dateTv.setOnClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
            }

            contentTv.setOnLongClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
                return@setOnLongClickListener true
            }

            contentTv.setOnClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
            }

            imageIcon.setOnLongClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
                return@setOnLongClickListener true
            }

            imageIcon.setOnClickListener {
                speakOut(writeTv.text.toString()+" "+contentTv.text.toString()+" "+dateTv.text.toString())
            }
        }
    }

    override fun getItemCount(): Int = dataList.size
    inner class Holder(parent: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_c2c, parent, false))

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

    private fun speakOut(text:String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }
}
