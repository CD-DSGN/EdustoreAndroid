package com.grandmagic.edustore.model;

/**
 * Created by zhangmengqi on 2017/3/20.
 */

public class NewsData {
    private String url;
    private String title;
    private String content;

    public NewsData(String url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
