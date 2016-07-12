package com.example.kolya.news24.SimpleParse;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import io.realm.RealmObject;


@Root(name = "rss" )
public class NewsResponse extends RealmObject {
    @Element (name = "channel")
    public NewsChannel chann;

    @Attribute (name = "version")
    public String version;


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public NewsChannel getChann() {
        return chann;
    }

    public void setChann(NewsChannel chann) {
        this.chann = chann;
    }


}
