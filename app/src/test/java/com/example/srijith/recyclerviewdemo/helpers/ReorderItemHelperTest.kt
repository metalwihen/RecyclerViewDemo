package com.example.srijith.recyclerviewdemo.helpers

import com.example.srijith.recyclerviewdemo.type.*
import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.util.*

class ReorderItemHelperTest {

    val MAX_FAVOURITE = 6

    private val sampleList: MutableList<ReorderItem> = object : ArrayList<ReorderItem>() {
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

    private val almostEmptyFavouriteList: MutableList<ReorderItem> = object : ArrayList<ReorderItem>() {
        init {
            add(HeaderFavourite())
            add(ImageItem("Frederick Hoffman", "https://randomuser.me/api/portraits/men/52.jpg", ButtonType.REMOVE))
            add(HeaderOthers())
            add(ImageItem("Jeanette Simmmons", "https://randomuser.me/api/portraits/women/3.jpg", ButtonType.ADD))
            add(ImageItem("Wallace Lambert", "https://randomuser.me/api/portraits/men/53.jpg", ButtonType.ADD))
        }
    }

    private val fullFavouriteList: MutableList<ReorderItem> = object : ArrayList<ReorderItem>() {
        init {
            add(HeaderFavourite())
            add(ImageItem("Frederick Hoffman", "https://randomuser.me/api/portraits/men/52.jpg", ButtonType.REMOVE))
            add(ImageItem("Camila Elliott", "https://randomuser.me/api/portraits/women/60.jpg", ButtonType.ADD))
            add(ImageItem("Gerald Webb", "https://randomuser.me/api/portraits/men/55.jpg", ButtonType.ADD))
            add(ImageItem("Russell Hart", "https://randomuser.me/api/portraits/men/18.jpg", ButtonType.ADD))
            add(ImageItem("Joyce Mccoy", "https://randomuser.me/api/portraits/women/82.jpg", ButtonType.ADD))
            add(ImageItem("Jeanette Simmmons", "https://randomuser.me/api/portraits/women/3.jpg", ButtonType.ADD))
            add(HeaderOthers())
            add(ImageItem("Wallace Lambert", "https://randomuser.me/api/portraits/men/53.jpg", ButtonType.ADD))
        }
    }

    @Test
    fun `should count items in Favourite section`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)

        positionTracker.init(callback)

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
        val positionTracker = ReorderItemHelper(list, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)

        positionTracker.init(callback)

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
        val positionTracker = ReorderItemHelper(list, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)

        positionTracker.init(callback)

        assertEquals(0, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should update item position to favourite section if add button clicked`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        positionTracker.handleItemButtonAction(8, callback)

        assertEquals(5, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should update item view if add button clicked`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        positionTracker.init(mock(ReorderItemHelper.AdapterCallback::class.java))

        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.handleItemButtonAction(8, callback)

        verify(callback).onItemMoved(8, 5)
        verify(callback).onItemChanged(5)
    }


    @Test
    fun `should update item position from favourite section if remove button clicked`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        positionTracker.init(mock(ReorderItemHelper.AdapterCallback::class.java))

        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.handleItemButtonAction(2, callback)

        assertEquals(3, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should update item view if remove button clicked`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, false)
        val callbackInit = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callbackInit)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)

        positionTracker.handleItemButtonAction(2, callback)

        verify(callback).onItemMoved(2, 5)
        verify(callback).onItemChanged(5)
    }


    @Test
    fun `should hide button of item view if last item in favourite section`() {
        val positionTracker = ReorderItemHelper(almostEmptyFavouriteList, MAX_FAVOURITE, false)

        positionTracker.init(mock(ReorderItemHelper.AdapterCallback::class.java))

        assertEquals(1, positionTracker.countOfFavouriteItems)
        assertEquals(ButtonType.HIDDEN, (almostEmptyFavouriteList[1] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (almostEmptyFavouriteList[3] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (almostEmptyFavouriteList[4] as ImageItem).buttonType)
    }

    @Test
    fun `should hide button of item view if last item in favourite section after button clicks`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, false)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalFavCount = positionTracker.countOfFavouriteItems

        positionTracker.handleItemButtonAction(2, callback)
        positionTracker.handleItemButtonAction(1, callback)
        positionTracker.handleItemButtonAction(1, callback)

        assertEquals(4, originalFavCount)
        assertEquals(1, positionTracker.countOfFavouriteItems)

        assertEquals("Flenn Wilson", (sampleList[1] as ImageItem).name)
        assertEquals(ButtonType.HIDDEN, (sampleList[1] as ImageItem).buttonType)
    }


    @Test
    fun `should show minus button for item in favourite section and plus button in other section`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)

        positionTracker.init(mock(ReorderItemHelper.AdapterCallback::class.java))

        assertEquals(ButtonType.REMOVE, (sampleList[1] as ImageItem).buttonType)
        assertEquals(ButtonType.REMOVE, (sampleList[2] as ImageItem).buttonType)
        assertEquals(ButtonType.REMOVE, (sampleList[4] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (sampleList[12] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (sampleList[20] as ImageItem).buttonType)
    }

    @Test
    fun `should show minus button for item in favourite section and plus button in other section even after button action`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        positionTracker.handleItemButtonAction(2, callback) // remove
        positionTracker.handleItemButtonAction(12, callback) // add

        assertEquals(ButtonType.REMOVE, (sampleList[1] as ImageItem).buttonType)
        assertEquals(ButtonType.REMOVE, (sampleList[2] as ImageItem).buttonType)
        assertEquals(ButtonType.REMOVE, (sampleList[3] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (sampleList[10] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (sampleList[12] as ImageItem).buttonType)
        assertEquals(ButtonType.ADD, (sampleList[19] as ImageItem).buttonType)
    }

    @Test
    fun `should ensure favourite item count does not cross max limit when button clicked to add items`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        positionTracker.init(mock(ReorderItemHelper.AdapterCallback::class.java))
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        val originalFavouriteCount = positionTracker.countOfFavouriteItems

        positionTracker.handleItemButtonAction(12, callback) // add
        positionTracker.handleItemButtonAction(12, callback) // add
        positionTracker.handleItemButtonAction(12, callback) // add

        assertEquals(4, originalFavouriteCount)
        assertEquals(MAX_FAVOURITE, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should ensure favourite items change order but not cross max limit when button clicked to add items`() {
        val positionTracker = ReorderItemHelper(fullFavouriteList, MAX_FAVOURITE, true)
        positionTracker.init(mock(ReorderItemHelper.AdapterCallback::class.java))
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        val originalFavouriteCount = positionTracker.countOfFavouriteItems
        val originalFirstItem = fullFavouriteList[1]

        positionTracker.handleItemButtonAction(8, callback) // add

        assertEquals(6, originalFavouriteCount)
        assertEquals(6, positionTracker.countOfFavouriteItems)
        assertNotEquals(originalFirstItem, fullFavouriteList[1])
        assertEquals("Frederick Hoffman", (originalFirstItem as ImageItem).name)
        assertEquals("Camila Elliott", (fullFavouriteList[1] as ImageItem).name)
        assertEquals("Frederick Hoffman", (fullFavouriteList[8] as ImageItem).name)
    }

    @Test
    fun `should update favourite count after button click to remove item`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalFavCount = positionTracker.countOfFavouriteItems

        positionTracker.handleItemButtonAction(2, callback) // remove

        assertEquals(4, originalFavCount)
        assertEquals(3, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should update favourite count after button click to add item`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalFavCount = positionTracker.countOfFavouriteItems

        positionTracker.handleItemButtonAction(12, callback) // add

        assertEquals(4, originalFavCount)
        assertEquals(5, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should update favourite count after random button click to add and remove item`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalFavCount = positionTracker.countOfFavouriteItems

        positionTracker.handleItemButtonAction(4, callback) // remove
        positionTracker.handleItemButtonAction(8, callback) // add
        positionTracker.handleItemButtonAction(12, callback) // add
        positionTracker.handleItemButtonAction(16, callback) // add
        positionTracker.handleItemButtonAction(19, callback) // add

        assertEquals(4, originalFavCount)
        assertEquals(6, positionTracker.countOfFavouriteItems)
    }

    @Test
    fun `should allow drag to reorder for only items in favourite section`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        val isDragAllowedForItemInFavSection = positionTracker.isDragAllowed(2)

        assertTrue(isDragAllowedForItemInFavSection)
    }

    @Test
    fun `should not allow drag to reorder for items not in favourite section`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        val isDragAllowedForItemInFavSection = positionTracker.isDragAllowed(12)

        assertFalse(isDragAllowedForItemInFavSection)
    }

    @Test
    fun `should not allow drag to reorder for list elements whose type is header`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, false)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        val isDragAllowedForHeaderFavourite = positionTracker.isDragAllowed(0)
        val isDragAllowedForHeaderOther = positionTracker.isDragAllowed(5)

        assertFalse(isDragAllowedForHeaderFavourite)
        assertFalse(isDragAllowedForHeaderOther)
    }

    @Test
    fun `should not allow drag to reorder for list elements whose type is placeholder`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        val isDragAllowedForPlaceholder = positionTracker.isDragAllowed(5)

        assertFalse(isDragAllowedForPlaceholder)
    }

    @Test
    fun `should change order if item views dragged within favourite section`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalItemAtPosition = sampleList[1]

        positionTracker.onDragView(1, 4, callback)

        verify(callback).onItemMoved(1, 4)
        assertEquals(originalItemAtPosition, sampleList[4])
    }

    @Test
    fun `should change order if item views dragged within favourite section and when count of favourite is max`() {
        val positionTracker = ReorderItemHelper(fullFavouriteList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalItemAtPosition = fullFavouriteList[2]

        positionTracker.onDragView(2, 6, callback)

        verify(callback).onItemMoved(2, 6)
        assertEquals(originalItemAtPosition, fullFavouriteList[6])
    }

    @Test
    fun `should not change order if item views dragged above header Favourites`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalItemAtPosition = sampleList[1]

        positionTracker.onDragView(1, 0, callback)

        assertEquals(originalItemAtPosition, sampleList[1])
        verify(callback, never()).onItemMoved(1, 0)
    }

    @Test
    fun `should not change order if item views dragged below header Others`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalItemAtPosition = sampleList[1]

        positionTracker.onDragView(1, 8, callback)

        assertEquals(originalItemAtPosition, sampleList[1])
        verify(callback, never()).onItemMoved(1, 8)
    }

    @Test
    fun `should not change order if item views dragged beyond last favourite item`() {
        val positionTracker = ReorderItemHelper(sampleList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)
        val originalItemAtPosition = sampleList[1]

        positionTracker.onDragView(1, 5, callback)

        assertEquals(originalItemAtPosition, sampleList[1])
        verify(callback, never()).onItemMoved(1, 5)
    }


    @Test
    fun `should add placeholders when favourite items less than the max limit`() {
        val positionTracker = ReorderItemHelper(almostEmptyFavouriteList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)

        positionTracker.init(callback)
        assertTrue(almostEmptyFavouriteList[2] is Placeholder)
        assertTrue(almostEmptyFavouriteList[3] is Placeholder)
        assertTrue(almostEmptyFavouriteList[4] is Placeholder)
        assertTrue(almostEmptyFavouriteList[5] is Placeholder)
        assertTrue(almostEmptyFavouriteList[6] is Placeholder)
        assertTrue(almostEmptyFavouriteList[7] is HeaderOthers)
    }

    @Test
    fun `should remove placeholders when items added to favourites`() {
        val positionTracker = ReorderItemHelper(almostEmptyFavouriteList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        positionTracker.handleItemButtonAction(4, callback)

        assertTrue(almostEmptyFavouriteList[2] is ImageItem)
        assertTrue(almostEmptyFavouriteList[3] is Placeholder)
        assertTrue(almostEmptyFavouriteList[4] is Placeholder)
        assertTrue(almostEmptyFavouriteList[5] is Placeholder)
        assertTrue(almostEmptyFavouriteList[6] is Placeholder)
        assertTrue(almostEmptyFavouriteList[7] is HeaderOthers)
    }

    @Test
    fun `should add placeholders when items removed from favourites`() {
        val positionTracker = ReorderItemHelper(fullFavouriteList, MAX_FAVOURITE, true)
        val callback = mock(ReorderItemHelper.AdapterCallback::class.java)
        positionTracker.init(callback)

        positionTracker.handleItemButtonAction(1, callback)
        positionTracker.handleItemButtonAction(2, callback)
        positionTracker.handleItemButtonAction(3, callback)
        positionTracker.handleItemButtonAction(1, callback)

        assertTrue(fullFavouriteList[2] is ImageItem)
        assertTrue(fullFavouriteList[3] is Placeholder)
        assertTrue(fullFavouriteList[4] is Placeholder)
        assertTrue(fullFavouriteList[5] is Placeholder)
        assertTrue(fullFavouriteList[7] is HeaderOthers)
    }

}