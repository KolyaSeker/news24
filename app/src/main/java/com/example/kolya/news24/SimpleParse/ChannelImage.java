package com.example.kolya.news24.SimpleParse;


import org.simpleframework.xml.Element;

import io.realm.RealmObject;


public class ChannelImage extends RealmObject {
    private String title;
    private String url;
    private String link;

    @Element(name = "title")
    public String getTitle() {
        return title;
    }
    @Element(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }
    @Element(name = "url")
    public String getUrl() {
        return url;
    }
    @Element(name = "url")
    public void setUrl(String url) {
        this.url = url;
    }

    @Element(name = "link")
    public String getLink() {
        return link;
    }
    @Element(name = "link")
    public void setLink(String link) {
        this.link = link;
    }
}
