package com.example.srijith.recyclerviewdemo.type;

public class Header extends ReorderItem {

    private String title;

    public Header(String title, int type) {
        super(type);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
