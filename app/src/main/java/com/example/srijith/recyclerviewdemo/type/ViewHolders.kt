package com.example.srijith.recyclerviewdemo.type

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.srijith.recyclerviewdemo.R

class PlaceholderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class SectionHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var sectionTitle: TextView = itemView.findViewById(R.id.textview_section_header)
}

class ImageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val avatar: ImageView = itemView.findViewById(R.id.imageview_profile)
    val name: TextView = itemView.findViewById(R.id.textview_name)
    val buttonAddRemove: TextView = itemView.findViewById(R.id.button_add_remove)

    fun setButtonClickListener(callback: () -> Unit) {
        buttonAddRemove.setOnClickListener {
            callback.invoke()
        }
    }

    fun bind(item: ImageItem) {
        Glide.with(itemView).load(item.imageUrl).into(avatar)
        name.text = item.name
        when (item.buttonType) {
            ButtonType.HIDDEN -> buttonAddRemove.visibility = View.GONE
            ButtonType.ADD -> {
                buttonAddRemove.visibility = View.VISIBLE
                buttonAddRemove.setBackgroundResource(R.color.green)
                buttonAddRemove.text = "+"
            }
            ButtonType.REMOVE -> {
                buttonAddRemove.visibility = View.VISIBLE
                buttonAddRemove.setBackgroundResource(R.color.red)
                buttonAddRemove.text = "-"
            }
        }
    }
}

