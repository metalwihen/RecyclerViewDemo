package com.example.srijith.recyclerviewdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import com.example.srijith.recyclerviewdemo.helpers.ReorderItemHelper
import com.example.srijith.recyclerviewdemo.helpers.SwipeAndDragHelper

class BadgeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        val userRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_user_list)

        val dataRepo = DataRepo()
        val usersList = dataRepo.usersList

        val adapter = ReorderItemListAdapter(usersList, ReorderItemHelper(usersList, 6, true))

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.spanSizeLookup = adapter.getSpanSize(layoutManager)

        val swipeAndDragHelper = SwipeAndDragHelper(adapter)
        val touchHelper = ItemTouchHelper(swipeAndDragHelper)
        adapter.setTouchHelper(touchHelper)

        userRecyclerView.layoutManager = layoutManager
        userRecyclerView.adapter = adapter
        touchHelper.attachToRecyclerView(userRecyclerView)
    }

    companion object {
        private val TAG = "BadgeListActivity"
    }

}
