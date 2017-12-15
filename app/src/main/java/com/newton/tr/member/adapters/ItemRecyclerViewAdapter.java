package com.newton.tr.member.adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.newton.tr.member.R;
import com.newton.tr.member.database.ItemRepo;
import com.newton.tr.member.fragments.TabShop;
import com.newton.tr.member.models.Item;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Item> itemList;
    private TabShop tabShop;
    private ItemRepo itemRepo;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public CheckBox checkBox;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            itemName = v.findViewById(R.id.itemName);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }

    public ItemRecyclerViewAdapter(ArrayList<Item> itemData, TabShop tabShop) {
        this.itemList = itemData;
        this.tabShop = tabShop;
        this.itemRepo = new ItemRepo();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemRecyclerViewAdapter.ViewHolder holder, int position) {
        final Item mItem = itemList.get(holder.getAdapterPosition());
        final int pos = holder.getAdapterPosition();
        holder.itemName.setText(mItem.getName());

        if (mItem.getStatus()) {
            holder.itemName.setPaintFlags(holder.itemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemName.setTextColor(Color.LTGRAY);

        } else if (!mItem.getStatus()) {
            holder.itemName.setPaintFlags(0);
            int itemTextColor = R.color.charcoal;
            holder.itemName.setTextColor(holder.itemName.getResources().getColor(itemTextColor));

        }

        holder.checkBox.setChecked(mItem.getIsChecked());
        tabShop.setDeleteButtonVisibility(hasTasksToDelete());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    mItem.setIsChecked(true);

                } else {
                    mItem.setIsChecked(false);

                }

                tabShop.setDeleteButtonVisibility(hasTasksToDelete());
            }
        });

        holder.itemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemID = mItem.getId();
                boolean itemStatus = mItem.getStatus();
                String itemName = mItem.getName();

                if (itemStatus) {
                    updateItemStatus(itemID, 0, itemName, pos);

                } else {
                    updateItemStatus(itemID, 1, itemName, pos);

                }
            }
        });

        holder.itemName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                tabShop.editItem(mItem);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void deleteCheckedItems() {

        for(Iterator<Item> iterator = this.itemList.iterator(); iterator.hasNext(); ) {
            Item item = iterator.next();

            if(item.getIsChecked()) {
                itemRepo.deleteItem(item.getId(), item.getName());
                iterator.remove();

            }
        }

        tabShop.setDeleteButtonVisibility(hasTasksToDelete());
        notifyDataSetChanged();
    }

    private void updateItemStatus(int ID, int status, String name, int pos) {

        itemRepo.updateItem(ID, status, name, name);
        Item mItem = itemList.get(pos);
        boolean newStatus = true;

        if (status == 0) {
            newStatus = false;

        }

        mItem.setStatus(newStatus);
        notifyDataSetChanged();
    }

    public void refreshRecyclerView() {

        itemList = itemRepo.getAllItems();
        notifyDataSetChanged();
    }

    private boolean hasTasksToDelete() {

        for (int i = 0; i < itemList.size(); i++) {

            if (itemList.get(i).getIsChecked()) {
                return true;
            }
        }

        return false;
    }
}
