package com.newton.tr.member.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.newton.tr.member.Database.TaskRepo;
import com.newton.tr.member.Fragments.TabTask;
import com.newton.tr.member.Models.Task;
import com.newton.tr.member.R;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Task> taskList;
    private TaskRepo taskRepo;
    private TabTask tabTask;

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

    public void add(int position, Task task) {
        taskList.add(position, task);
        notifyItemInserted(position);
    }

    public void remove(int position, Task task) {
        taskRepo.deleteTask(task.getId(), task.getTask());
        int pos = taskList.indexOf(task);
        taskList.remove(pos);
        notifyItemRemoved(pos);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskRecyclerViewAdapter(ArrayList<Task> taskData, TabTask tabTask) {
        this.taskList = taskData;
        this.tabTask = tabTask;
        this.taskRepo = new TaskRepo();
    }

    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Task mTask = taskList.get(holder.getAdapterPosition());
        holder.taskDesc.setText(mTask.getTask());
        holder.taskDate.setText(tabTask.getString(R.string.row_layout_date_prefix, mTask.getDateAdded()));


        if (mTask.getStatus()) {
            holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDesc.setTextColor(Color.LTGRAY);

            holder.taskDate.setPaintFlags(holder.taskDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDate.setTextColor(Color.LTGRAY);

        } else if (!mTask.getStatus()) {
            holder.taskDesc.setPaintFlags(0);
            int taskDescTextColor = R.color.charcoal;
            holder.taskDesc.setTextColor(holder.taskDesc.getResources().getColor(taskDescTextColor));

            holder.taskDate.setPaintFlags(0);
            int taskDateTextColor = R.color.darkGray;
            holder.taskDate.setTextColor(holder.taskDate.getResources().getColor(taskDateTextColor));

        }

        holder.checkBox.setChecked(mTask.getIsChecked());
        tabTask.setDeleteButtonVisibility(hasTasksToDelete());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    mTask.setIsChecked(true);

                } else {
                    mTask.setIsChecked(false);

                }

                tabTask.setDeleteButtonVisibility(hasTasksToDelete());
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
                    updateTaskStatus(taskID, 0, taskDate, taskDesc, position);

                } else {
                    updateTaskStatus(taskID, 1, taskDate, taskDesc, position);

                }
            }
        });

        holder.taskTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tabTask.editTask(mTask);

                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void deleteCheckedTasks() {

        for(Iterator<Task> iterator = this.taskList.iterator(); iterator.hasNext(); ) {
            Task test = iterator.next();
            if(test.getIsChecked()) {
                taskRepo.deleteTask(test.getId(), test.getTask());
                iterator.remove();
            }

        }

        tabTask.setDeleteButtonVisibility(hasTasksToDelete());

        notifyDataSetChanged();
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
