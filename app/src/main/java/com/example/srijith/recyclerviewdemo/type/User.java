package com.example.srijith.recyclerviewdemo.type;

/**
 * Created by Srijith on 08-10-2017.
 */

public class User extends ReorderItem {

    private String name;
    private String imageUrl;

    public User(String name, String imageUrl) {
        super(Type.ITEM_USER.id);
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public User(User user) {
        super(user.getType());
        this.name = user.getName();
        this.imageUrl = user.getImageUrl();
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
