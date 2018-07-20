package com.jeong_woochang.sunrinthon.Retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeong_woochang.sunrinthon.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

public class PlayRecyclerAdapter extends RecyclerView.Adapter<PlayRecyclerAdapter.ViewHolder> {

    ArrayList<PlayData> arrayList=new ArrayList<>();
    Context context;

    public PlayRecyclerAdapter(Context mContext){
        context=mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout=(LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_play,parent,false);
        ViewHolder vh=new ViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayData tmp=arrayList.get(position);
        ImageView img=(ImageView)holder.layout.findViewById(R.id.imageIcon);
        TextView title=(TextView)holder.layout.findViewById(R.id.title);
        TextView content=(TextView)holder.layout.findViewById(R.id.content);
        title.setText(tmp.getTitle());
        content.setText(tmp.getContent());
        Picasso.with(context)
                .load("http://18.222.191.92:3000/paralympiques/"+tmp.getTitle()+".png")
                .into(img);
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

    public void addItem(String title, String content, String type){
        PlayData data=new PlayData();
        data.setTitle(title);
        data.setContent(content);
        data.setImg(type);
        arrayList.add(data);
    }
}
