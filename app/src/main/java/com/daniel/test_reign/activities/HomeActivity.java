package com.daniel.test_reign.activities;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daniel.test_reign.R;
import com.daniel.test_reign.adapters.HitsAdapter;
import com.daniel.test_reign.adapters.viewholder.HitsViewHolder;
import com.daniel.test_reign.core.helper.RecyclerItemTouchHelper;
import com.daniel.test_reign.core.models.HitsObject;
import com.daniel.test_reign.core.models.HomeObject;
import com.daniel.test_reign.core.retrofit.ServiceUtils;
import com.daniel.test_reign.core.retrofit.methods.ApiMethods;
import com.daniel.test_reign.fragments.DetailFragment;
import com.daniel.test_reign.utils.PreferenceUtils;
import com.daniel.test_reign.utils.SystemUtils;
import com.daniel.test_reign.utils.UserUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private ApiMethods mAPIService;
    private List<HitsObject> auxListHits = new ArrayList<>();
    private List<Integer> listPos = new ArrayList<>();

    private RecyclerView listHome;
    private SwipeRefreshLayout mSwipe;
    private HitsAdapter adapter;

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
        mSwipe = findViewById(R.id.swipeHome);

    }

    private void initData() {

        listHome.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listHome.setLayoutManager(linearLayoutManager);
        listHome.setItemAnimator(new DefaultItemAnimator());
        listHome.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mSwipe.setOnRefreshListener(this);
        mSwipe.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        createItemHelper();

        mSwipe.post(new Runnable() {
            @Override
            public void run() {

                mSwipe.setRefreshing(true);
                validateNetwork();

            }
        });

    }

    private void createItemHelper() {

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(listHome);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(listHome);

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

        auxListHits = listHits;

        Collections.sort(auxListHits, new Comparator<HitsObject>() {
            @Override
            public int compare(HitsObject o1, HitsObject o2) {

                return Integer.parseInt(UserUtils.convertTimeInMillis(o1.getCreated_at())) -
                        Integer.parseInt(UserUtils.convertTimeInMillis(o2.getCreated_at()));
            }
        });

        checkDeleteElement();

        adapter = new HitsAdapter(auxListHits, listener);
        listHome.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        PreferenceUtils.deleteDataHits(this);
        PreferenceUtils.setDataHits(auxListHits, this);

    }

    private void checkDeleteElement() {

        if (listPos.size() > 0) {

            Iterator<HitsObject> it = auxListHits.iterator();
            while (it.hasNext()) {
                HitsObject user = it.next();
                for (int id : listPos) {
                    if (user.getStory_id() == id) {
                        it.remove();
                        break;
                    }
                }
            }

        }

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
        validateNetwork();
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof HitsViewHolder) {

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete row")
                    .setMessage("Are you sure you want to delete this row?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            final HitsObject deletedItem = auxListHits.get(viewHolder.getAdapterPosition());
                            listPos.add(deletedItem.getStory_id());
                            adapter.removeItem(viewHolder.getAdapterPosition());

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }
}
