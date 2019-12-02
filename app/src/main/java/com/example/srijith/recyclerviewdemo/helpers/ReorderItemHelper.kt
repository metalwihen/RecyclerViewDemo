package com.example.srijith.recyclerviewdemo.helpers

import android.util.Log
import com.example.srijith.recyclerviewdemo.type.ButtonType
import com.example.srijith.recyclerviewdemo.type.ImageItem
import com.example.srijith.recyclerviewdemo.type.ReorderItem

class ReorderItemHelper(private val list: MutableList<ReorderItem>) {

    companion object {
        const val MAX_FAVOURITES = 6
    }

    val positionOfHeaderFav = 0

    var positionOfHeaderOther = -1
        private set

    var countOfFavouriteItems = 0
        private set

    fun init(callback: AdapterCallback) {
        identifyPositionsAndCount()
        updateItemButtonState(callback)
    }

    fun handleItemButtonAction(position: Int, callback: AdapterCallback) {
        identifyPositionsAndCount()

        val item = list[position] as ImageItem

        val hasMinOneBadge = countOfFavouriteItems > 1
        if (hasMinOneBadge && item.buttonType == ButtonType.REMOVE) {
            moveItem(position, positionOfHeaderOther, callback)
        } else if (!isFavouriteSectionFull() && item.buttonType == ButtonType.ADD) {
            moveItem(position, positionOfHeaderOther, callback)
        } else if (isFavouriteSectionFull() && item.buttonType == ButtonType.ADD) {
            moveItem(positionOfHeaderFav + 1, position - 1, callback)
            moveItem(position, positionOfHeaderOther, callback)
        }
    }

    fun isDragAllowed(pos: Int): Boolean {
        return isFavourite(pos)
    }

    fun onDragView(oldPosition: Int, newPosition: Int, callback: AdapterCallback) {
        val isDraggedAboveHeaderFav = newPosition <= positionOfHeaderFav
        val isDraggedBeyondHeaderOther = newPosition >= positionOfHeaderOther
        if (isDraggedAboveHeaderFav || isDraggedBeyondHeaderOther) {
            return
        }

        if (list[oldPosition].type == ReorderItem.Type.ITEM.id) {
            moveItem(oldPosition, newPosition, callback)
        }
    }

    private fun identifyPositionsAndCount() {
        var positionHeaderOther = 0
        var countItemsInFavSection = 0

        for (position in 0 until list.size) {
            if (list[position].type == ReorderItem.Type.ITEM.id) {
                countItemsInFavSection++
            }

            if (list[position].type == ReorderItem.Type.OTHER_HEADER.id) {
                positionHeaderOther = position
                break
            }
        }

        this.countOfFavouriteItems = countItemsInFavSection
        this.positionOfHeaderOther = positionHeaderOther
    }

    private fun updateItemButtonState(callback: AdapterCallback) {
        val isFavSectionAlmostEmpty = countOfFavouriteItems == 1
        for (pos in 0 until list.size) {
            if (list[pos].type == ReorderItem.Type.ITEM.id) {
                val item = list[pos] as ImageItem
                if (isFavSectionAlmostEmpty && isFavourite(pos)) {
                    isChanged(pos, item.buttonType, ButtonType.HIDDEN, {
                        item.buttonType = ButtonType.HIDDEN
                        callback.onItemChanged(pos)
                    })
                } else if (!isFavSectionAlmostEmpty && isFavourite(pos)) {
                    isChanged(pos, item.buttonType, ButtonType.REMOVE, {
                        item.buttonType = ButtonType.REMOVE
                        callback.onItemChanged(pos)
                    })
                } else {
                    isChanged(pos, item.buttonType, ButtonType.ADD, {
                        item.buttonType = ButtonType.ADD
                        callback.onItemChanged(pos)
                    })
                }
            }
        }
    }

    private fun moveItem(oldPosition: Int, newPosition: Int, callback: AdapterCallback) {
        val targetUser = list.removeAt(oldPosition)
        list.add(newPosition, targetUser)

        identifyPositionsAndCount()

        callback.onItemMoved(oldPosition, newPosition)
        updateItemButtonState(callback)
    }

    private fun <T> isChanged(pos: Int, originalValue: T, newValue: T, callback: () -> Unit) {
        if (originalValue != newValue) {
            callback.invoke()
        }
    }

    private fun isFavouriteSectionFull(): Boolean {
        return countOfFavouriteItems >= MAX_FAVOURITES
    }

    private fun isFavourite(pos: Int): Boolean {
        return pos in (positionOfHeaderFav + 1) until positionOfHeaderOther
    }

//    private fun addPlaceholders() {
//        val posHeaderFav = badgeListOrganizer.positionOfHeaderFav
//        val countFav = badgeListOrganizer.countOfFavouriteItems
//
//        val openSlots = MAX_FAVOURITES - countFav
//        if (openSlots > 0) {
//            for (i in 1..openSlots) {
//                finalList.add(posHeaderFav + countFav + i, Placeholder())
//            }
//
//            notifyItemRangeChanged(0, MAX_FAVOURITES)
//        }
//    }
//
//    private fun removePlaceholders() {
//        val itemsToRemove = HashSet<Int>()
//        for (i in finalList.indices) {
//            if (finalList[i].type == ReorderItem.Type.PLACEHOLDER.id) {
//                itemsToRemove.add(i)
//            }
//        }
//
//        if (itemsToRemove.size > 0) {
//            for (position in itemsToRemove) {
//                finalList.removeAt(position)
//            }
//            notifyItemRangeChanged(0, MAX_FAVOURITES)
//        }
//    }

    interface AdapterCallback {
        fun onItemMoved(oldPos: Int, newPos: Int)
        fun onItemChanged(pos: Int)
    }

}