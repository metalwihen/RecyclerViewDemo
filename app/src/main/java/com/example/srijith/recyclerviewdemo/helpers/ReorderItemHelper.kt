package com.example.srijith.recyclerviewdemo.helpers

import com.example.srijith.recyclerviewdemo.type.ButtonType
import com.example.srijith.recyclerviewdemo.type.ImageItem
import com.example.srijith.recyclerviewdemo.type.Placeholder
import com.example.srijith.recyclerviewdemo.type.ReorderItem

class ReorderItemHelper(private val list: MutableList<ReorderItem>, val maxFavourites: Int, val usePlaceholders: Boolean = true) {

    private val positionOfHeaderFav = 0

    private var positionOfHeaderOther = -1

    var countOfFavouriteItems = 0
        private set

    fun init(callback: AdapterCallback) {
        updateViewIfChanged(callback)
    }

    fun handleItemButtonAction(position: Int, callback: AdapterCallback) {
        if (usePlaceholders) {
            removeAllPlaceholders(callback)
        }

        val item = list[position] as ImageItem
        val hasMinOneBadge = countOfFavouriteItems > 1
        if (hasMinOneBadge && item.buttonType == ButtonType.REMOVE) {
            moveItem(position, positionOfHeaderOther, callback)
        } else if (!isFavouriteSectionFull() && item.buttonType == ButtonType.ADD) {
            moveItem(position, positionOfHeaderFav + countOfFavouriteItems + 1, callback)
        } else if (isFavouriteSectionFull() && item.buttonType == ButtonType.ADD) {
            moveItem(positionOfHeaderFav + 1, position, callback)
            moveItem(position - 1, positionOfHeaderOther - 1, callback)
        } else {
            return
        }

        updateViewIfChanged(callback)
    }

    fun isDragAllowed(pos: Int): Boolean {
        return isFavourite(pos)
    }

    fun onDragView(oldPosition: Int, newPosition: Int, callback: AdapterCallback) {
        val isDraggedAboveHeaderFav = newPosition <= positionOfHeaderFav
        val isDraggedBeyondLastFavouriteItem = newPosition > positionOfHeaderFav + countOfFavouriteItems
        if (isDraggedAboveHeaderFav || isDraggedBeyondLastFavouriteItem) {
            return
        }

        if (list[oldPosition].type == ReorderItem.Type.ITEM.id) {
            moveItem(oldPosition, newPosition, callback)
            updateViewIfChanged(callback)
        }
    }

    private fun identifyPositionsAndCount() {
        var positionHeaderOther = 0
        var countItemsInFavSection = 0
        var countOfPlaceholders = 0

        for (position in 0 until list.size) {
            if (list[position].type == ReorderItem.Type.ITEM.id) {
                countItemsInFavSection++
            }

            if (list[position].type == ReorderItem.Type.PLACEHOLDER.id) {
                countOfPlaceholders++
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
                    isChanged(item.buttonType, ButtonType.HIDDEN, {
                        item.buttonType = ButtonType.HIDDEN
                        callback.onItemChanged(pos)
                    })
                } else if (!isFavSectionAlmostEmpty && isFavourite(pos)) {
                    isChanged(item.buttonType, ButtonType.REMOVE, {
                        item.buttonType = ButtonType.REMOVE
                        callback.onItemChanged(pos)
                    })
                } else {
                    isChanged(item.buttonType, ButtonType.ADD, {
                        item.buttonType = ButtonType.ADD
                        callback.onItemChanged(pos)
                    })
                }
            }
        }
    }

    private fun updateViewIfChanged(callback: AdapterCallback) {
        val originalCountFav = countOfFavouriteItems
        identifyPositionsAndCount()
        updateItemButtonState(callback)

        if (usePlaceholders) {
            isChanged(originalCountFav, countOfFavouriteItems) {
                configurePlaceholders(callback)
            }
        }

    }


    private fun moveItem(oldPosition: Int, newPosition: Int, callback: AdapterCallback) {
        val targetUser = list.removeAt(oldPosition)
        list.add(newPosition, targetUser)
        callback.onItemMoved(oldPosition, newPosition)
    }

    private fun <T> isChanged(originalValue: T, newValue: T, callback: () -> Unit) {
        if (originalValue != newValue) {
            callback.invoke()
        }
    }

    private fun isFavouriteSectionFull(): Boolean {
        return countOfFavouriteItems >= maxFavourites
    }

    private fun isFavourite(pos: Int): Boolean {
        val start = positionOfHeaderFav + 1
        return pos in start until start + countOfFavouriteItems
    }

    private fun configurePlaceholders(callback: AdapterCallback) {
        removeAllPlaceholders(callback)
        addPlaceholders(callback)
    }

    private fun addPlaceholders(callback: AdapterCallback) {
        val openSlots = maxFavourites - countOfFavouriteItems
        for (i in 1..openSlots) {
            val insertPosition = positionOfHeaderFav + countOfFavouriteItems + 1
            list.add(insertPosition, Placeholder())
            callback.onItemInserted(insertPosition)

            positionOfHeaderOther++
        }
    }

    private fun removeAllPlaceholders(callback: AdapterCallback) {
        val set = HashSet<ReorderItem>()
        for (item in list) {
            if (item is Placeholder) {
                set.add(item)
            }
        }

        if (set.size != 0) {
            positionOfHeaderOther -= set.size

            for (item in set) {
                val pos: Int = list.indexOf(item)
                list.remove(item)
                callback.onItemRemoved(pos)
            }
        }
    }

    interface AdapterCallback {
        fun onItemMoved(oldPos: Int, newPos: Int)
        fun onItemChanged(pos: Int)
        fun onItemInserted(pos: Int)
        fun onItemRemoved(pos: Int)
    }

}