package com.jeong_woochang.sunrinthon.Retrofit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jeong_woochang.sunrinthon.R;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

public class FragmentGame extends Fragment {
    public FragmentGame(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final PlayRecyclerAdapter tipRecyclerAdapter=new PlayRecyclerAdapter(getContext());
        LinearLayout layout=(LinearLayout)inflater.inflate(R.layout.fragment_game,container,false);
        RecyclerView recyclerView=(RecyclerView)layout.findViewById(R.id.game_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(tipRecyclerAdapter);

        Client.INSTANCE.getRetrofitService().getplayList().enqueue(new retrofit2.Callback<ArrayList<playRepo>>() {
            @Override
            public void onResponse(Call<ArrayList<playRepo>> call, Response<ArrayList<playRepo>> response) {
                if (response.isSuccessful()){
                    for(int i=0;i<response.body().size();i++)
                        tipRecyclerAdapter.addItem(response.body().get(i).name,response.body().get(i).content,"game");
                }
                tipRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<playRepo>> call, Throwable t) {

            }
        });
        return layout;
    }
}
