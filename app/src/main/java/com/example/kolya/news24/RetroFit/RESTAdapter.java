package com.example.kolya.news24.RetroFit;

import com.example.kolya.news24.SimpleParse.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RESTAdapter {
    @GET("rss/all.xml")
    Call<NewsResponse> getNews();
}


