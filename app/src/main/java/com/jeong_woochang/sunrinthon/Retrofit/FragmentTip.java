package com.jeong_woochang.sunrinthon.Retrofit;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jeong_woochang.sunrinthon.R;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

public class FragmentTip extends Fragment {

    public FragmentTip(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final TipRecyclerAdapter tipRecyclerAdapter=new TipRecyclerAdapter(getContext());
        LinearLayout layout=(LinearLayout)inflater.inflate(R.layout.fragment_tip,container,false);
        RecyclerView recyclerView=(RecyclerView)layout.findViewById(R.id.tip_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(tipRecyclerAdapter);

        Client.INSTANCE.getRetrofitService().gettipList().enqueue(new retrofit2.Callback<ArrayList<tipRepo>>() {
            @Override
            public void onResponse(Call<ArrayList<tipRepo>> call, Response<ArrayList<tipRepo>> response) {
                if (response.code()==200){
                    for(int i=0;i<response.body().size();i++){
                        System.out.println(response.body().get(i).title + response.body().get(i).content);
                        tipRecyclerAdapter.addItem(response.body().get(i).title, response.body().get(i).content, response.body().get(i).img);
                    }
                }
                tipRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<tipRepo>> call, Throwable t) {

            }
        });

        return layout;
    }
}
