package com.aoppp.magnetsearch.domain;

import java.io.Serializable;

/**
 * Created by guguyanhua on 6/23/15.
 */
public class Magnet implements Serializable{

    String title;

    String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Magnet{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
