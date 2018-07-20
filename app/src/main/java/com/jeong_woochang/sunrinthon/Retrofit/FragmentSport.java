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

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jeong-woochang on 2018. 7. 20..
 */

public class FragmentSport extends Fragment {
    public FragmentSport(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final PlayRecyclerAdapter tipRecyclerAdapter=new PlayRecyclerAdapter(getContext());
        LinearLayout layout=(LinearLayout)inflater.inflate(R.layout.fragment_sport,container,false);
        RecyclerView recyclerView=(RecyclerView)layout.findViewById(R.id.sport_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(tipRecyclerAdapter);
        Client.INSTANCE.getRetrofitService().getplayList().enqueue(new retrofit2.Callback<ArrayList<playRepo>>() {
            @Override
            public void onResponse(Call<ArrayList<playRepo>> call, Response<ArrayList<playRepo>> response) {
                if (response.code()==200){
                    for(int i=0;i<response.body().size();i++){
                        System.out.println(response.body().get(i).name +"$@$@$"+ response.body().get(i).content);
                        tipRecyclerAdapter.addItem(response.body().get(i).name, response.body().get(i).content, "");
                    }
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
