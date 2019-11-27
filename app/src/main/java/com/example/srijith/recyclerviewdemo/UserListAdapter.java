package com.example.srijith.recyclerviewdemo;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.srijith.recyclerviewdemo.type.Header;
import com.example.srijith.recyclerviewdemo.type.Placeholder;
import com.example.srijith.recyclerviewdemo.type.ReorderItem;
import com.example.srijith.recyclerviewdemo.type.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Srijith on 08-10-2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements SwipeAndDragHelper.ActionCompletionContract {

    private static final int MAX_FAVOURITES = 6;

    private List<ReorderItem> finalList;
    private ItemTouchHelper touchHelper;

    private BadgeListOrganizer badgeListOrganizer = new BadgeListOrganizer();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ReorderItem.Type.PLACEHOLDER.id) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_list_item_placeholder, parent, false);
            return new PlaceholderViewHolder(view);
        } else if (viewType == ReorderItem.Type.FAV_HEADER.id || viewType == ReorderItem.Type.OTHER_HEADER.id) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_list_section_header, parent, false);
            return new SectionHeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_user_list_item, parent, false);
            return new UserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ReorderItem.Type.ITEM_USER.id) {
            User user = (User) finalList.get(position);
            ((UserViewHolder) holder).username.setText(user.getName());
            Glide.with(holder.itemView).load(user.getImageUrl()).into(((UserViewHolder) holder).userAvatar);

            ((UserViewHolder) holder).customizeAddRemoveButton(position > badgeListOrganizer.getPositionOfHeaderOther());

            ((UserViewHolder) holder).buttonAddRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    badgeListOrganizer.identifyHeaderPositions(finalList);
                    badgeListOrganizer.countItemsInFav(finalList);

                    int positionFavHeader = badgeListOrganizer.getPositionOfHeaderFav();
                    int positionOtherHeader = badgeListOrganizer.getPositionOfHeaderOther();
                    int countFav = badgeListOrganizer.getFavSectionCount();

                    int position = holder.getAdapterPosition();
                    boolean hasMinOneBadge = countFav > 1;
                    boolean isFavBadgeSectionFull = countFav >= MAX_FAVOURITES;
                    if (hasMinOneBadge && ((UserViewHolder) holder).buttonAddRemove.getText().toString().contains("-")) {
//                        removePlaceholders();
                        moveItem(position, positionOtherHeader);
                    } else if (!isFavBadgeSectionFull && ((UserViewHolder) holder).buttonAddRemove.getText().toString().contains("+")) {
//                        removePlaceholders();
                        moveItem(position, positionOtherHeader);
                    } else if (isFavBadgeSectionFull && ((UserViewHolder) holder).buttonAddRemove.getText().toString().contains("+")) {
                        moveItem(positionFavHeader + 1, position - 1);
                        moveItem(position, positionOtherHeader - 1);
                    }

//                    badgeListOrganizer.identifyHeaderPositions(finalList);
//                    badgeListOrganizer.countItemsInFav(finalList);
//                    addPlaceholders();
                }
            });

            ((UserViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getAdapterPosition();
                    int positionOtherHeader = badgeListOrganizer.getPositionOfHeaderOther();
                    if (position < positionOtherHeader) {
                        touchHelper.startDrag(holder);
                    }
                    return false;
                }
            });

        } else if (itemViewType == ReorderItem.Type.FAV_HEADER.id || itemViewType == ReorderItem.Type.OTHER_HEADER.id) {
            Header header = (Header) finalList.get(position);
            SectionHeaderViewHolder headerViewHolder = (SectionHeaderViewHolder) holder;
            headerViewHolder.sectionTitle.setText(header.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return finalList == null ? 0 : finalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return finalList.get(position).getType();
    }

    public void setUserList(List<ReorderItem> usersList) {
        this.finalList = usersList;

        badgeListOrganizer.identifyHeaderPositions(finalList);
        badgeListOrganizer.countItemsInFav(finalList);
//        addPlaceholders();

        notifyDataSetChanged();
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        boolean isDraggedAboveTitle = newPosition == 0;
        boolean isDraggedBeyondLastFavouriteBadge = newPosition >= badgeListOrganizer.getFavSectionCount() + 1;
        boolean isFavouriteSectionFull = badgeListOrganizer.getFavSectionCount() > MAX_FAVOURITES;

        if (isDraggedAboveTitle || isDraggedBeyondLastFavouriteBadge || isFavouriteSectionFull) {
            Log.e("ok", "Can't move that!");
            return;
        }

        ReorderItem movedItem = finalList.get(oldPosition);
        if (movedItem.getType() == ReorderItem.Type.ITEM_USER.id) {
            moveItem(oldPosition, newPosition);
        }
    }

    private void addPlaceholders() {
        int posHeaderFav = badgeListOrganizer.getPositionOfHeaderFav();
        int countFav = badgeListOrganizer.getFavSectionCount();

        int openSlots = MAX_FAVOURITES - countFav;
        if (openSlots > 0) {
            for (int i = 1; i <= openSlots; i++) {
                finalList.add(posHeaderFav + countFav + i, new Placeholder());
            }

            notifyItemRangeChanged(0, MAX_FAVOURITES);
        }
    }

    private void removePlaceholders() {
        Set<Integer> itemsToRemove = new HashSet<>();
        for (int i = 0; i < finalList.size(); i++) {
            if (finalList.get(i).getType() == ReorderItem.Type.PLACEHOLDER.id) {
                itemsToRemove.add(i);
            }
        }

        if (itemsToRemove.size() > 0) {
            for (Integer position : itemsToRemove) {
                finalList.remove(position.intValue());
            }
            notifyItemRangeChanged(0, MAX_FAVOURITES);
        }
    }

    private void moveItem(int oldPosition, int newPosition) {
        ReorderItem targetUser = finalList.remove(oldPosition);
        finalList.add(newPosition, targetUser);

        badgeListOrganizer.identifyHeaderPositions(finalList);
        badgeListOrganizer.countItemsInFav(finalList);

        notifyItemMoved(oldPosition, newPosition);
        notifyItemChanged(newPosition);
    }

    public void setTouchHelper(ItemTouchHelper touchHelper) {
        this.touchHelper = touchHelper;
    }

    public GridLayoutManager.SpanSizeLookup getSpanSize(
            final GridLayoutManager gridLayoutManager) {
        return new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (getItemViewType(position) == ReorderItem.Type.OTHER_HEADER.id
                        || getItemViewType(position) == ReorderItem.Type.FAV_HEADER.id) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        };
    }
}
