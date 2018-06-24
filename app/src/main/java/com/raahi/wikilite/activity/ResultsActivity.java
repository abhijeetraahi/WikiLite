package com.raahi.wikilite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.raahi.wikilite.R;
import com.raahi.wikilite.adapter.ResultAdapter;
import com.raahi.wikilite.model.ResultData;
import com.raahi.wikilite.retrofit.ApiClient;
import com.raahi.wikilite.retrofit.ApiInterface;
import com.raahi.wikilite.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Raahi on 24-06-2018.
 */

public class ResultsActivity extends AppCompatActivity {
    private static final String TAG = ResultsActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RelativeLayout no_data;
    private ResultData mResultData;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerview);
        no_data =  findViewById(R.id.no_data_found_layout);

        mSessionManager = new SessionManager(ResultsActivity.this); // Session manager to store latest information

        getSearchData(getIntent().getStringExtra("searchQuery"));
    }

    @Override
    public boolean onSupportNavigateUp(){
        startActivity(new Intent(ResultsActivity.this, HomeActivity.class));
        finish();
        return true;
    }

    private void getSearchData(String searchQuery) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Map<String, String> params = new HashMap<String, String>();
        params.put("gpssearch", searchQuery);

        Call<ResultData> call = apiService.getSearchData(params);
        call.enqueue(new Callback<ResultData>() {
            @Override
            public void onResponse(Call<ResultData> call, Response<ResultData> response) {
                if (response.isSuccessful()) {
                    Log.v(TAG + " Success", new Gson().toJson(response.body()));
                    mResultData = response.body();
                    setRecyclerView();
                } else
                    Log.v(TAG +" unSuccess", new Gson().toJson(response.errorBody()));
            }

            @Override
            public void onFailure(Call<ResultData> call, Throwable t) {
                Log.e(TAG + " error",  t.toString());
            }
        });
    }

    private void setRecyclerView(){
        if (mResultData != null && mResultData.getQuery().getPages().size() != 0) {

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            ResultAdapter adapter = new ResultAdapter(mResultData,this);
            recyclerView.setAdapter(adapter);
        } else {
            showNoData();
        }
    }

    public void showNoData() {
        recyclerView.setVisibility(View.GONE);
        no_data.setVisibility(View.VISIBLE);
    }
}
