package com.example.srijith.recyclerviewdemo.helpers

import com.example.srijith.recyclerviewdemo.type.*
import org.junit.Test

import org.junit.Assert.*
import java.util.ArrayList

class ReorderItemHelperTest {

    val list: MutableList<ReorderItem> = object : ArrayList<ReorderItem>() {
        init {
            add(HeaderFavourite())
            add(ImageItem("Frederick Hoffman", "https://randomuser.me/api/portraits/men/52.jpg", ButtonType.REMOVE))
            add(ImageItem("Calvin Young", "https://randomuser.me/api/portraits/men/78.jpg", ButtonType.REMOVE))
            add(ImageItem("Jeanette Reid", "https://randomuser.me/api/portraits/women/37.jpg", ButtonType.REMOVE))
            add(ImageItem("Flenn Wilson", "https://randomuser.me/api/portraits/men/40.jpg", ButtonType.REMOVE))
            add(HeaderOthers())
            add(ImageItem("Jeanette Simmmons", "https://randomuser.me/api/portraits/women/3.jpg", ButtonType.ADD))
            add(ImageItem("Wallace Lambert", "https://randomuser.me/api/portraits/men/53.jpg", ButtonType.ADD))
            add(ImageItem("Andy Clark", "https://randomuser.me/api/portraits/men/68.jpg", ButtonType.ADD))
            add(ImageItem("olivia obrien", "https://randomuser.me/api/portraits/women/92.jpg", ButtonType.ADD))
            add(ImageItem("Debbie Bennett", "https://randomuser.me/api/portraits/women/34.jpg", ButtonType.ADD))
            add(ImageItem("Jimmy Spoon", "https://randomuser.me/api/portraits/women/93.jpg", ButtonType.ADD))
            add(ImageItem("Bernice Lawson", "https://randomuser.me/api/portraits/women/20.jpg", ButtonType.ADD))
            add(ImageItem("Camila Elliott", "https://randomuser.me/api/portraits/women/60.jpg", ButtonType.ADD))
            add(ImageItem("Gerald Webb", "https://randomuser.me/api/portraits/men/55.jpg", ButtonType.ADD))
            add(ImageItem("Russell Hart", "https://randomuser.me/api/portraits/men/18.jpg", ButtonType.ADD))
            add(ImageItem("Joyce Mccoy", "https://randomuser.me/api/portraits/women/82.jpg", ButtonType.ADD))
            add(ImageItem("Daryl Banks", "https://randomuser.me/api/portraits/men/4.jpg", ButtonType.ADD))
            add(ImageItem("Veronica Vargas", "https://randomuser.me/api/portraits/women/14.jpg", ButtonType.ADD))
            add(ImageItem("Natalie Jacobs", "https://randomuser.me/api/portraits/women/0.jpg", ButtonType.ADD))
            add(ImageItem("Beverly Kennedy", "https://randomuser.me/api/portraits/women/30.jpg", ButtonType.ADD))
        }
    }

    @Test
    fun `should identify position of Headers`() {
        val positionTracker = ReorderItemHelper(list)

        positionTracker.identifyPositionsAndCount()

        assertEquals(0, positionTracker.positionOfHeaderFav)
        assertEquals(5, positionTracker.positionOfHeaderOther)
    }

    @Test
    fun `should count items in Favourite section`() {
        val positionTracker = ReorderItemHelper(list)

        positionTracker.identifyPositionsAndCount()

        assertEquals(4, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should return zero if no imageItems in Favourite section`() {
        val list: MutableList<ReorderItem> = object : ArrayList<ReorderItem>() {
            init {
                add(HeaderFavourite())
                add(HeaderOthers())
                add(ImageItem("Jeanette Simmmons", "https://randomuser.me/api/portraits/women/3.jpg", ButtonType.ADD))
                add(ImageItem("Wallace Lambert", "https://randomuser.me/api/portraits/men/53.jpg", ButtonType.ADD))
            }
        }

        val positionTracker = ReorderItemHelper(list)
        positionTracker.identifyPositionsAndCount()

        assertEquals(0, positionTracker.countOfFavouriteItems)
    }


    @Test
    fun `should return zero if there are no imageItems in entire list`() {
        val list: MutableList<ReorderItem> = object : ArrayList<ReorderItem>() {
            init {
                add(HeaderFavourite())
                add(HeaderOthers())
            }
        }

        val positionTracker = ReorderItemHelper(list)
        positionTracker.identifyPositionsAndCount()

        assertEquals(0, positionTracker.countOfFavouriteItems)
    }
}