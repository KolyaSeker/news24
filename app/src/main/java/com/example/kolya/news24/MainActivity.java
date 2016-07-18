package com.example.kolya.news24;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kolya.news24.Adapter.RecyclerViewAdapter;
import com.example.kolya.news24.RetroFit.RESTAdapter;
import com.example.kolya.news24.SimpleParse.NewsItem;
import com.example.kolya.news24.SimpleParse.NewsResponse;
import com.example.kolya.news24.events.TrySaveNewsEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    public Realm realm;
    public List<NewsItem> cachedNewsItem;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String BASE_URL = "http://24tv.ua/";

    OkHttpClient client = new OkHttpClient();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();

    RESTAdapter retrofitAdapter = retrofit.create(RESTAdapter.class);


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void newsSaveEventHandler(TrySaveNewsEvent event) {
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm.beginTransaction();
        realm.copyToRealm(event.items);
        realm.commitTransaction();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light);


        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int firstPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstPos > 0) {
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    swipeRefreshLayout.setEnabled(true);
                }
            }
        });

        if (isOnline()) {
            Call<NewsResponse> call = retrofitAdapter.getNews();
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    List<NewsItem> items = response.body().getChann().getNewsItem();
                    EventBus.getDefault().post(new TrySaveNewsEvent(items));
                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, getApplicationContext());
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.v("NORESPONSE", "No responce from 24tv");
                }
            });

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Call<NewsResponse> call = retrofitAdapter.getNews();
                    call.enqueue(new Callback<NewsResponse>() {
                        @Override
                        public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                            List<NewsItem> items = response.body().getChann().getNewsItem();
                            EventBus.getDefault().post(new TrySaveNewsEvent(items));
                            RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, getApplicationContext());
                            swipeRefreshLayout.setRefreshing(false);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<NewsResponse> call, Throwable t) {
                            Log.v("NORESPONSE", "No responce from 24tv");
                        }
                    });
                }
            });


        } else {
            if (realm == null) {
                realm = Realm.getDefaultInstance();
            }
            cachedNewsItem = realm.where(NewsItem.class).findAll();
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(cachedNewsItem, getApplicationContext());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent;
                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.nav_home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.navigation_sub_item_3:
                        if (realm == null) {
                            realm = Realm.getDefaultInstance();
                        }
                        realm.beginTransaction();
                        realm.where(NewsItem.class).findAll().deleteAllFromRealm();
                        realm.commitTransaction();


                        recyclerView.removeAllViews();

                        Toast.makeText(getApplicationContext(), "Clear a cache", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();

                        break;

                    case R.id.navigation_sub_item_2:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();

                        intent = new Intent(MainActivity.this, InformationActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        drawerLayout.closeDrawers();
                        break;

                    case R.id.nav_exit:
                        finish();
                }
                return true;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
        return nInfo != null && nInfo.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
        }
    }
}



