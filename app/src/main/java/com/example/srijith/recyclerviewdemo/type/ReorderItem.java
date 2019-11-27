package com.example.srijith.recyclerviewdemo.type;

public class ReorderItem {

    private int type;

    public ReorderItem(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public enum Type {
        PLACEHOLDER(1),
        FAV_HEADER(2),
        OTHER_HEADER(3),
        ITEM_USER(4);

        public int id;

        Type(int id) {
            this.id = id;
        }
    }
}
