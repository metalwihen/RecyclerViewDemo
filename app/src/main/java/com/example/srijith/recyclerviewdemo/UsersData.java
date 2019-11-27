package com.example.srijith.recyclerviewdemo;

import com.example.srijith.recyclerviewdemo.type.HeaderFavourite;
import com.example.srijith.recyclerviewdemo.type.HeaderOthers;
import com.example.srijith.recyclerviewdemo.type.Placeholder;
import com.example.srijith.recyclerviewdemo.type.ReorderItem;
import com.example.srijith.recyclerviewdemo.type.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srijith on 08-10-2017.
 */

public class UsersData {

    private List<ReorderItem> usersList = new ArrayList<ReorderItem>() {
        {
            add(new HeaderFavourite());
            add(new User("Frederick Hoffman", "https://randomuser.me/api/portraits/men/52.jpg"));
            add(new User("Calvin Young", "https://randomuser.me/api/portraits/men/78.jpg"));
            add(new User("Jeanette Reid", "https://randomuser.me/api/portraits/women/37.jpg"));
            add(new User("Flenn Wilson", "https://randomuser.me/api/portraits/men/40.jpg"));
//            add(new Placeholder());
            add(new HeaderOthers());
            add(new User("Jeanette Simmmons", "https://randomuser.me/api/portraits/women/3.jpg"));
            add(new User("Wallace Lambert", "https://randomuser.me/api/portraits/men/53.jpg"));
            add(new User("Andy Clark", "https://randomuser.me/api/portraits/men/68.jpg"));
            add(new User("olivia obrien", "https://randomuser.me/api/portraits/women/92.jpg"));
            add(new User("Debbie Bennett", "https://randomuser.me/api/portraits/women/34.jpg"));
            add(new User("Jimmy Spoon", "https://randomuser.me/api/portraits/women/93.jpg"));
            add(new User("Bernice Lawson", "https://randomuser.me/api/portraits/women/20.jpg"));
            add(new User("Camila Elliott", "https://randomuser.me/api/portraits/women/60.jpg"));
            add(new User("Gerald Webb", "https://randomuser.me/api/portraits/men/55.jpg"));
            add(new User("Russell Hart", "https://randomuser.me/api/portraits/men/18.jpg"));
            add(new User("Joyce Mccoy", "https://randomuser.me/api/portraits/women/82.jpg"));
            add(new User("Daryl Banks", "https://randomuser.me/api/portraits/men/4.jpg"));
            add(new User("Veronica Vargas", "https://randomuser.me/api/portraits/women/14.jpg"));
            add(new User("Natalie Jacobs", "https://randomuser.me/api/portraits/women/0.jpg"));
            add(new User("Beverly Kennedy", "https://randomuser.me/api/portraits/women/30.jpg"));
        }
    };

    public List<ReorderItem> getUsersList() {
        return usersList;
    }
}
