package com.newton.tr.member.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.newton.tr.member.Database.TaskRepo;
import com.newton.tr.member.Fragments.TabTask;
import com.newton.tr.member.Models.Task;
import com.newton.tr.member.Models.ViewModel;
import com.newton.tr.member.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Task> taskList;
    private ArrayList<TaskPackage> tasksToBeDeleted = new ArrayList<>();
    private TaskRepo taskRepo = new TaskRepo();
    private TabTask tabTask;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView taskDesc;
        public TextView taskDate;
        public CheckBox checkBox;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
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

    public class TaskPackage {
        Task task;
        int position;

        public TaskPackage(Task taskToDelete, int positionToDelete) {
            task = taskToDelete;
            position = positionToDelete;
        }
    }

    @Override
    public TaskRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskRecyclerViewAdapter.ViewHolder holder, int position) {
        final Task mTask = taskList.get(position);
        holder.taskDesc.setText(mTask.getTask());
        holder.taskDate.setText(mTask.getDateAdded());

        final int taskPosition = holder.getAdapterPosition();

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                TaskPackage taskPackage = new TaskPackage(mTask, taskPosition);

                if (isChecked) {
                    tasksToBeDeleted.add(taskPackage);
                } else {
                    tasksToBeDeleted.remove(taskPackage);
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

            TaskPackage taskPackageBuffer = tasksToBeDeleted.get(i);

            Task taskBuffer = taskPackageBuffer.task;
            int positionBuffer = taskPackageBuffer.position;

            remove(positionBuffer, taskBuffer);
        }

        tasksToBeDeleted.clear();

        tabTask.setDeleteButtonVisibility(tasksToBeDeleted.size());

        notifyDataSetChanged();
    }
}
