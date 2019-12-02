package com.example.srijith.recyclerviewdemo.type

open class ReorderItem(val type: Int) {
    enum class Type private constructor(var id: Int) {
        PLACEHOLDER(1),
        FAV_HEADER(2),
        OTHER_HEADER(3),
        ITEM(4)
    }
}

open class Header(val title: String, type: Int) : ReorderItem(type)

class HeaderFavourite : Header("Favourites", Type.FAV_HEADER.id)
class HeaderOthers : Header("Others", Type.OTHER_HEADER.id)
class Placeholder : ReorderItem(Type.PLACEHOLDER.id)

data class ImageItem(val name: String,
                     val imageUrl: String,
                     var buttonType: ButtonType) : ReorderItem(Type.ITEM.id)

enum class ButtonType {
    HIDDEN,
    ADD,
    REMOVE
}