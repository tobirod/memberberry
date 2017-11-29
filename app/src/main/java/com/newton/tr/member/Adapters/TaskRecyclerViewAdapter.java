package com.newton.tr.member.Adapters;

import android.content.Context;
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

import com.newton.tr.member.Database.TaskRepo;
import com.newton.tr.member.Fragments.TabTask;
import com.newton.tr.member.Models.Task;
import com.newton.tr.member.R;

import java.util.ArrayList;

import static com.newton.tr.member.R.color.charcoal;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Task> taskList;
    private ArrayList<Task> tasksToBeDeleted = new ArrayList<>();
    private TaskRepo taskRepo = new TaskRepo();
    private TabTask tabTask;
    public static int taskDescTextColor = R.color.charcoal;
    public static int taskDateTextColor = R.color.darkGray;

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
    }

    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        final Task mTask = taskList.get(position);
        holder.taskDesc.setText(mTask.getTask());
        holder.taskDate.setText(mTask.getDateAdded());

        final int pos = holder.getAdapterPosition();

        if (holder.checkBox.isChecked()) {
            holder.checkBox.setChecked(false);
        }

        if (mTask.getStatus()) {
            holder.taskDesc.setPaintFlags(0);
            holder.taskDesc.setTextColor(holder.taskDesc.getResources().getColor(taskDescTextColor));

            holder.taskDate.setPaintFlags(0);
            holder.taskDate.setTextColor(holder.taskDate.getResources().getColor(taskDateTextColor));

        } else if (!mTask.getStatus()) {
            holder.taskDesc.setPaintFlags(holder.taskDesc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDesc.setTextColor(Color.LTGRAY);

            holder.taskDate.setPaintFlags(holder.taskDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.taskDate.setTextColor(Color.LTGRAY);

        }

        holder.taskTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int taskPos = pos;

                int taskID = mTask.getId();
                boolean taskStatus = mTask.getStatus();
                String taskDate = mTask.getDateAdded();
                String taskDesc = mTask.getTask();

                if (taskStatus) {
                    updateTaskStatus(taskID, false, taskDate, taskDesc, taskPos);
                } else {
                    updateTaskStatus(taskID, true, taskDate, taskDesc, taskPos);
                }

            }
        });

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    tasksToBeDeleted.add(mTask);
                } else {
                    tasksToBeDeleted.remove(mTask);
                }

                tabTask.setDeleteButtonVisibility(tasksToBeDeleted.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void refreshRecyclerView(ArrayList<Task> newTasks)
    {
        if(newTasks == null || newTasks.size()==0)
            return;
        if (taskList != null && taskList.size()>0)
            taskList.clear();
        taskList.addAll(newTasks);
        notifyDataSetChanged();

    }

    public void deleteCheckedTasks() {

        for (int i = 0; i < tasksToBeDeleted.size(); i++) {

            int taskID = tasksToBeDeleted.get(i).getId();
            String task = tasksToBeDeleted.get(i).getTask();


            taskRepo.deleteTask(taskID, task);
        }

        tasksToBeDeleted.clear();

        tabTask.setDeleteButtonVisibility(tasksToBeDeleted.size());

        taskList = taskRepo.getAllTasks();

        notifyDataSetChanged();
    }

    public void updateTaskStatus(int ID, boolean status, String dateAdded, String task, int pos) {
        taskRepo.updateTask(ID, status, dateAdded, task, task);

        taskList = taskRepo.getAllTasks();

        notifyDataSetChanged();
    }
}
