package com.example.srijith.recyclerviewdemo

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.srijith.recyclerviewdemo.helpers.ReorderItemHelper
import com.example.srijith.recyclerviewdemo.helpers.SwipeAndDragHelper
import com.example.srijith.recyclerviewdemo.type.*

class ReorderItemListAdapter(
        private val finalList: MutableList<ReorderItem>,
        private val organizer: ReorderItemHelper) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), SwipeAndDragHelper.ActionCompletionContract, ReorderItemHelper.AdapterCallback {

    private lateinit var touchHelper: ItemTouchHelper

    init {
        organizer.init(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ReorderItem.Type.PLACEHOLDER.id ->
                PlaceholderViewHolder(
                        layoutInflater.inflate(R.layout.layout_user_list_item_placeholder, parent, false))

            ReorderItem.Type.FAV_HEADER.id,
            ReorderItem.Type.OTHER_HEADER.id ->
                SectionHeaderViewHolder(
                        layoutInflater
                                .inflate(R.layout.layout_user_list_section_header, parent, false))
            else -> {
                val holder = ImageItemViewHolder(
                        layoutInflater
                                .inflate(R.layout.layout_user_list_item, parent, false))
                holder.setButtonClickListener {
                    organizer.handleItemButtonAction(holder.adapterPosition, this)
                }
                holder.itemView.setOnTouchListener { view, motionEvent ->
                    if (isDragAllowed(holder)) {
                        touchHelper.startDrag(holder)
                    }
                    true
                }
                holder
            }

        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        if (itemViewType == ReorderItem.Type.ITEM.id) {
            val itemHolder = viewHolder as ImageItemViewHolder
            itemHolder.bind(finalList[position] as ImageItem)
        } else if (itemViewType == ReorderItem.Type.FAV_HEADER.id || itemViewType == ReorderItem.Type.OTHER_HEADER.id) {
            val header = finalList[position] as Header
            val headerViewHolder = viewHolder as SectionHeaderViewHolder
            headerViewHolder.sectionTitle.text = header.title
        }
    }

    override fun getItemCount(): Int {
        return finalList.size
    }

    override fun getItemViewType(position: Int): Int {
        return finalList[position].type
    }

    override fun allowMove(type: Int): Boolean {
        return when (type) {
            ReorderItem.Type.ITEM.id -> true
            else -> false
        }
    }

    private fun isDragAllowed(viewHolder: RecyclerView.ViewHolder): Boolean {
        return organizer.isDragAllowed(viewHolder.adapterPosition)
    }

    override fun onViewMoved(oldPosition: Int, newPosition: Int) {
        organizer.onDragView(oldPosition, newPosition, this)
    }

    override fun onItemMoved(oldPos: Int, newPos: Int) {
        notifyItemMoved(oldPos, newPos)
    }

    override fun onItemChanged(pos: Int) {
        notifyItemChanged(pos)
    }

    fun setTouchHelper(touchHelper: ItemTouchHelper) {
        this.touchHelper = touchHelper
    }

    fun getSpanSize(
            gridLayoutManager: GridLayoutManager): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (getItemViewType(position) == ReorderItem.Type.OTHER_HEADER.id || getItemViewType(position) == ReorderItem.Type.FAV_HEADER.id) {
                    gridLayoutManager.spanCount
                } else {
                    1
                }
            }
        }
    }
}


