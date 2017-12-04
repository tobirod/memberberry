package com.newton.tr.member.Adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.newton.tr.member.Database.ItemRepo;
import com.newton.tr.member.Fragments.TabShop;
import com.newton.tr.member.Models.Item;
import com.newton.tr.member.R;

import java.util.ArrayList;

public class ItemRecyclerViewAdapter extends RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Item> itemList;
    private ItemRepo itemRepo;
    private TabShop tabShop;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TableLayout taskTable;
        public TextView taskDesc;
        public TextView taskDate;
        public CheckBox checkBox;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            taskTable = v.findViewById(R.id.taskTable);
            taskDesc = v.findViewById(R.id.taskDesc);
            taskDate = v.findViewById(R.id.taskDate);
            checkBox = v.findViewById(R.id.checkBox);
        }
    }

    public void add(int position, Item item) {
        itemList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position, Item item) {
        itemRepo.deleteItem(item.getId(), item.getProduct());
        int pos = itemList.indexOf(item);
        itemList.remove(pos);
        notifyItemRemoved(pos);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ItemRecyclerViewAdapter(ArrayList<Item> taskData, TabShop tabShop) {
        this.itemList = taskData;
        this.tabShop = tabShop;
        this.itemRepo = new ItemRepo();
    }

    @Override
    public ItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_row_task, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        final Item mItem = itemList.get(holder.getAdapterPosition());
        holder.taskDesc.setText(mItem.getProduct());
        holder.taskDate.setText(tabShop.getString(R.string.row_layout_date_prefix, mItem.getDateAdded()));


        if (mItem.getStatus()) {
            holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDesc.setTextColor(Color.LTGRAY);

            holder.taskDate.setPaintFlags(holder.taskDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDate.setTextColor(Color.LTGRAY);

        } else if (!mItem.getStatus()) {
            holder.taskDesc.setPaintFlags(0);
            int taskDescTextColor = R.color.charcoal;
            holder.taskDesc.setTextColor(holder.taskDesc.getResources().getColor(taskDescTextColor));

            holder.taskDate.setPaintFlags(0);
            int taskDateTextColor = R.color.darkGray;
            holder.taskDate.setTextColor(holder.taskDate.getResources().getColor(taskDateTextColor));

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

        holder.taskTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int taskID = mTask.getId();
                boolean taskStatus = mTask.getStatus();
                String taskDate = mTask.getDateAdded();
                String taskDesc = mTask.getTask();

                if (taskStatus) {
                    updateTaskStatus(taskID, 0, taskDate, taskDesc, adapterPosition);

                } else {
                    updateTaskStatus(taskID, 1, taskDate, taskDesc, adapterPosition);

                }
            }
        });

        holder.taskTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tabShop.editTask(mTask);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /*

    public void deleteCheckedTasks() {

        for(Iterator<Task> iterator = this.taskList.iterator(); iterator.hasNext(); ) {
            Task test = iterator.next();
            if(test.getIsChecked()) {
                taskRepo.deleteTask(test.getTaskId(), test.getTaskContent());
                iterator.remove();
            }

        }

        tabShop.setDeleteButtonVisibility(hasTasksToDelete());

        notifyDataSetChanged();
    }

    */

    public void deleteCheckedTasks() {

    }

    private void updateTaskStatus(int ID, int status, String dateAdded, String task, int pos) {
        taskRepo.updateTask(ID, status, dateAdded, task, task);
        Task mTask = taskList.get(pos);

        boolean newStatus = true;

        if (status == 0) {
            newStatus = false;
        }

        mTask.setStatus(newStatus);

        notifyDataSetChanged();
    }

    public void refreshRecyclerView() {
        taskList = taskRepo.getAllTasks();

        notifyDataSetChanged();
    }

    private boolean hasTasksToDelete() {

        for (int i = 0; i < taskList.size(); i++) {

            if (taskList.get(i).getIsChecked()) {
                return true;
            }
        }

        return false;
    }
}
