package com.example.kolya.news24.events;

import com.example.kolya.news24.SimpleParse.NewsItem;

import java.util.List;


public class TrySaveNewsEvent {
    public List<NewsItem> items;

    public TrySaveNewsEvent(List<NewsItem> items) {
        this.items = items;
    }
}
