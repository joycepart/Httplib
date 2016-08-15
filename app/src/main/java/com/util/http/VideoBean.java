package com.util.http;

/**
 * Created by bixinwei on 16/8/15.
 */
public class VideoBean {
    private String id;
    private String name;
    private String title;

    @Override
    public String toString() {
        return "name：" + name + " title:" + title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
