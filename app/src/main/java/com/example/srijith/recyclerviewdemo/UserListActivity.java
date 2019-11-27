package com.example.srijith.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.srijith.recyclerviewdemo.type.ReorderItem;
import com.example.srijith.recyclerviewdemo.type.User;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private static final String TAG = "UserListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        RecyclerView userRecyclerView = findViewById(R.id.recyclerview_user_list);

        final UserListAdapter adapter = new UserListAdapter();

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setSpanSizeLookup(
                adapter.getSpanSize(layoutManager));

        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        adapter.setTouchHelper(touchHelper);

        userRecyclerView.setLayoutManager(layoutManager);
        userRecyclerView.setAdapter(adapter);
        touchHelper.attachToRecyclerView(userRecyclerView);

        UsersData usersData = new UsersData();
        List<ReorderItem> usersList = usersData.getUsersList();
        adapter.setUserList(usersList);
    }

}
