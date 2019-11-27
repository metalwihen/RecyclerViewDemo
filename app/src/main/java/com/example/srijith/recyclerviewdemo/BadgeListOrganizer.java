package com.example.srijith.recyclerviewdemo;

import com.example.srijith.recyclerviewdemo.type.ReorderItem;

import java.util.List;

public class BadgeListOrganizer {

    private int positionHeaderFav = -1;
    private int positionHeaderOther = -1;
    private int countItemsInFav = 0;

    public void identifyHeaderPositions(List<ReorderItem> items) {
        int positionHeaderFav = -1;
        int positionHeaderOther = -1;
        int position = 0;
        for (ReorderItem item : items) {
            if (item.getType() == ReorderItem.Type.FAV_HEADER.id) {
                positionHeaderFav = position;
            } else if (item.getType() == ReorderItem.Type.OTHER_HEADER.id) {
                positionHeaderOther = position;
            }

            if (positionHeaderFav != -1 && positionHeaderOther != -1) {
                break;
            }
            position++;
        }

        this.positionHeaderFav = positionHeaderFav;
        this.positionHeaderOther = positionHeaderOther;
    }

    public void countItemsInFav(List<ReorderItem> items) {
        int position = 0;
        int countItemsInFav = 0;
        for (ReorderItem item : items) {
            if (item.getType() == ReorderItem.Type.ITEM_USER.id &&
                    position > positionHeaderFav && position < positionHeaderOther) {
                countItemsInFav++;
            }
            position++;
        }
        this.countItemsInFav = countItemsInFav;
    }

    public int getFavSectionCount() {
        return countItemsInFav;
    }

    public int getPositionOfHeaderOther() {
        return positionHeaderOther;
    }

    public int getPositionOfHeaderFav() {
        return positionHeaderFav;
    }
}
