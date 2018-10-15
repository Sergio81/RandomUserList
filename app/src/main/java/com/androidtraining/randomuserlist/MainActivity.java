package com.androidtraining.randomuserlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.androidtraining.randomuserlist.api.RandomAPI;
import com.androidtraining.randomuserlist.modules.RandomResponse;
import com.androidtraining.randomuserlist.modules.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //region private fields
    private static final String BASE_URL = "https://randomuser.me/";
    private static final String TAG = "MainActivity_TAG";
    private static final int USERS_COUNT = 30;

    private Retrofit client;
    private RandomAPI randomAPI;

    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private List<Result> myDataset;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView = findViewById(R.id.myRecyclerView);

        client = prepareRetrofitClient();
        randomAPI = client.create(RandomAPI.class);

        getUsers();
    }

    private void initializeRecyclerView() {
        myRecyclerView.setHasFixedSize(true);

        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        myAdapter= new MyAdapter(myDataset);
        myRecyclerView.setAdapter(myAdapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private Retrofit prepareRetrofitClient(){
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return client;
    }

    private void getUsers() {
        randomAPI.getRandomUsers(USERS_COUNT).enqueue(new Callback<RandomResponse>() {
            @Override
            public void onResponse(Call<RandomResponse> call, Response<RandomResponse> response) {
                myDataset = new ArrayList<Result>();

                if(response.isSuccessful()){
                    RandomResponse randomUser = response.body();
                    if(randomUser != null){
                        Result userInfo = randomUser.getResults().get(0);

                        for(Result result : randomUser.getResults()){

                            myDataset.add(result);
                        }

                        initializeRecyclerView();
                    }
                }
            }

            @Override
            public void onFailure(Call<RandomResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
