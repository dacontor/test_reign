package com.daniel.test_reign.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daniel.test_reign.R;
import com.daniel.test_reign.adapters.HitsAdapter;
import com.daniel.test_reign.core.models.HitsObject;
import com.daniel.test_reign.core.models.HomeObject;
import com.daniel.test_reign.core.retrofit.ServiceUtils;
import com.daniel.test_reign.core.retrofit.methods.ApiMethods;
import com.daniel.test_reign.core.views.CustomProgressDialog;
import com.daniel.test_reign.fragments.DetailFragment;
import com.daniel.test_reign.utils.PreferenceUtils;
import com.daniel.test_reign.utils.SystemUtils;
import com.daniel.test_reign.utils.UserUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ApiMethods mAPIService;
    private CustomProgressDialog progress;

    private RecyclerView listHome;

    private SwipeRefreshLayout mSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initVariables();
        initData();

    }

    private void initVariables() {

        mAPIService = ServiceUtils.getAPIService();
        listHome = findViewById(R.id.listHome);

        listHome.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listHome.setLayoutManager(linearLayoutManager);

        mSwipe = findViewById(R.id.swipeHome);
        mSwipe.setOnRefreshListener(this);
        mSwipe.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

    }

    private void initData() {

        mSwipe.post(new Runnable() {

            @Override
            public void run() {

                mSwipe.setRefreshing(true);
                validateNetwork();

            }
        });

    }

    private void validateNetwork() {

        if (PreferenceUtils.getListHits(this) == null)
            if (SystemUtils.isNetworkAvailable(this))
                callService();
            else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
                mSwipe.setRefreshing(false);
            }
        else if (SystemUtils.isNetworkAvailable(this))
            callService();
        else {
            initAdapter(PreferenceUtils.getListHits(this));
            mSwipe.setRefreshing(false);
        }

    }

    private void callService() {

        mSwipe.setRefreshing(true);

        mAPIService.getData().enqueue(new Callback<HomeObject>() {
            @Override
            public void onResponse(@NonNull Call<HomeObject> call, @NonNull Response<HomeObject> response) {

                if (response.body() != null &&
                        response.body().getHits().size() > 0)
                    initAdapter(response.body().getHits());
                else
                    Toast.makeText(HomeActivity.this, "Error al obtener listado", Toast.LENGTH_LONG).show();

                mSwipe.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<HomeObject> call, @NonNull Throwable t) {
                Log.v("HomeActivity", t.toString());
                mSwipe.setRefreshing(false);
                Toast.makeText(HomeActivity.this, "Error al obtener listado", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initAdapter(List<HitsObject> listHits) {

        Collections.sort(listHits, new Comparator<HitsObject>() {
            @Override
            public int compare(HitsObject o1, HitsObject o2) {

                return Integer.parseInt(UserUtils.convertTimeInMillis(o1.getCreated_at())) -
                        Integer.parseInt(UserUtils.convertTimeInMillis(o2.getCreated_at()));
            }
        });


        HitsAdapter adapter = new HitsAdapter(listHits, listener);
        listHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        PreferenceUtils.setChampionships(listHits, this);

    }

    private HitsAdapter.OnItemClickListener listener = new HitsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(HitsObject data, View view) {
            if (data != null)
                initFragment(DetailFragment.newInstance(data));
        }
    };

    private void initFragment(Fragment fragment) {

        SystemUtils.replaceFragment(HomeActivity.this,
                R.id.container,
                "DetailFragment",
                true,
                null,
                fragment);

    }

    @Override
    public void onRefresh() {
        callService();
    }
}
