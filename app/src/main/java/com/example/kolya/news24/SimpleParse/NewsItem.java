package com.example.kolya.news24.SimpleParse;

import org.simpleframework.xml.Element;

import io.realm.RealmObject;


public class NewsItem extends RealmObject{
    private  String titleItem;
    private  String linkItem;
    private  String description;
    private String pubDate;
    private String guid;
    private String creator;
    private String date;
    private String imageUrl;

    @Element(name = "title")
    public void setTitle(String titleItem)
    {
        this.titleItem = titleItem;
    }

    @Element(name = "title")
    public String getTitle()
    {
        return titleItem;
    }


    @Element(name = "link")
    public void setLink(String linkItem)
    {
        this.linkItem = linkItem;
    }

    @Element(name = "link")
    public String getLink()
    {
        return linkItem;
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


    @Element(name = "pubDate")
    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }

    @Element(name = "pubDate")
    public String getPubDate()
    {
        return pubDate;
    }

    @Element(name = "guid")
    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    @Element(name = "guid")
    public String getGuid()
    {
        return guid;
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


    @Element(name = "date")
    public void setItemDate(String date)
    {
        this.date = date;
    }

    @Element(name = "date")
    public String getItemDate()
    {
        return date;
    }


    public String getImageUrl() {
        if(imageUrl == null && description != null)
        {
            int beginIndex = description.indexOf("src=") + 5;
            int endIndex = description.lastIndexOf(".jpg'>") + 4;
            return description.substring(beginIndex, endIndex);
        }
        return null;
    }
}
