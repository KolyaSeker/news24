package com.example.kolya.news24;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.example.kolya.news24.Adapter.RecyclerViewAdapter;
import com.example.kolya.news24.SimpleParse.NewsItem;
import com.example.kolya.news24.SimpleParse.NewsResponse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    List<NewsItem> newsItemList;
    NewsResponse newsResponse;
    Realm realm;

    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        new FeedTask().execute();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FeedTask().execute();
            }
        });


        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light);

        swipeRefreshLayout.setRefreshing(false);

        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(newsItemList);
        realm.commitTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public class FeedTask extends AsyncTask<Void, Void, List<NewsItem>> {

        @Override
        protected List<NewsItem> doInBackground(Void... params) {
            try {

                //Creating OKHTTP Client
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://24tv.ua/rss/all.xml")
                        .build();

                //Transport the request and wait for response
                Response response = client.newCall(request).execute();

                String result = response.body().string();

                Serializer serializer = new Persister();

                newsResponse = serializer.read(NewsResponse.class, result.toString());



                Log.v("newsResponse", newsResponse.chann.getNewsItem().toString());
                return newsResponse.getChann().getNewsItem();

            } catch (Exception e) {
                Log.v("newsResponse", e.getMessage());
                return null;
            }

        }

        @Override
        public void onPostExecute(List<NewsItem> list) {
            if (list != null) {
                newsItemList = list;
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(list, getApplicationContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }


}



