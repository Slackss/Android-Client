package com.jeong_woochang.sunrinthon.Retrofit;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeong_woochang.sunrinthon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

class TipRecyclerAdapter extends RecyclerView.Adapter<TipRecyclerAdapter.ViewHolder> implements TextToSpeech.OnInitListener {

    ArrayList<TipData> arrayList=new ArrayList<>();

    Context context;

    TextToSpeech tts;

    public TipRecyclerAdapter(Context _context){
        context=_context;
        tts=new TextToSpeech(context,this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout=(LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tip,parent,false);
        ViewHolder vh=new ViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        TipData tmp=arrayList.get(position);
        ImageView img=(ImageView)holder.layout.findViewById(R.id.imageIcon);
        TextView title=(TextView)holder.layout.findViewById(R.id.title);
        TextView content=(TextView)holder.layout.findViewById(R.id.content);
        title.setText(tmp.getTitle());
        content.setText(tmp.getContent());
        Picasso.with(context)
                .load(tmp.getImg())
                .into(img);

        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                speakOut(arrayList.get(position).getTitle()+" "+arrayList.get(position).getContent());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layout;

        public ViewHolder(LinearLayout _layout) {
            super(_layout);
            layout=_layout;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            tts.setLanguage(Locale.KOREA);

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void addItem(String title, String content, String img){
        TipData data=new TipData();
        data.setTitle(title);
        data.setContent(content);
        data.setImg(img);
        arrayList.add(data);
    }

    private void speakOut(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null,null);
    }
}
