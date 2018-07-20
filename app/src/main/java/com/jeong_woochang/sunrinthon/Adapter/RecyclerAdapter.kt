package com.jeong_woochang.sunrinthon.Adapter


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jeong_woochang.sunrinthon.R
import com.jeong_woochang.sunrinthon.Retrofit.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_c2c.view.*
import java.util.*


internal class RecyclerAdapter(private val dataList: ArrayList<Data>) : RecyclerView.Adapter<RecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent)

    override fun onBindViewHolder(holder: Holder, position: Int) {

        with(holder.itemView) {
            val data = dataList[position]

            writeTv.text = data.writer
            dateTv.text = data.content
            contentTv.text = data.date
            Picasso.with(context)
                    .load(data.img)
                    .into(imageIcon);
        }
    }
    override fun getItemCount(): Int = dataList.size

    inner class Holder(parent: ViewGroup)
        : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_c2c, parent, false))
}
