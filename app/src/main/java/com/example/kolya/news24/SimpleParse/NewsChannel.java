package com.example.kolya.news24.SimpleParse;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import io.realm.RealmList;
import io.realm.RealmObject;


public class NewsChannel extends RealmObject {

    private  String title;
    private  String link;
    private  String description;
    private String creator;
    private ChannelImage image;
    public RealmList<NewsItem> newsItem;

    @Element(name = "title")
    public void setTitle(String title)
    {
        this.title = title;
    }

    @Element(name = "title")
    public String getTitle()
    {
        return title;
    }


    @Element(name = "link")
    public void setLinks(String link)
    {
        this.link = link;
    }

    @Element(name = "link")
    public String getLinks()
    {
        return link;
    }


    @Element(name = "description")
    public void setDescription(String description)
    {
        this.description = description;
    }

    @Element(name = "description")
    public String getDescription()
    {
        return description;
    }


    @Element(name = "creator")
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    @Element(name = "creator")
    public String getCreator()
    {
        return creator;
    }

    @Element(name = "image")
    public void setImages(ChannelImage image)
    {
        this.image = image;
    }

    @Element(name = "image")
    public ChannelImage getImages()
    {
        return image;
    }


    @ElementList(entry = "item",inline = true)
    public void setNewsItem(RealmList<NewsItem> newsItem)
    {
        this.newsItem = newsItem;
    }

    @ElementList(entry = "item",inline = true)
    public RealmList<NewsItem> getNewsItem()
    {
        return newsItem;
    }

}
