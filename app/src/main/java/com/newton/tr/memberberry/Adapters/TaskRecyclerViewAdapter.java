package com.newton.tr.memberberry.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.newton.tr.memberberry.Models.Task;
import com.newton.tr.memberberry.R;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

class TaskRecyclerViewAdapter extends RealmRecyclerViewAdapter<Task, TaskRecyclerViewAdapter.TaskViewHolder> {

    TaskRecyclerViewAdapter(OrderedRealmCollection<Task> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        final Task obj = getItem(position);
        holder.data = obj;
        //noinspection ConstantConditions
        holder.title.setText(obj.getTask());
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getIdTest();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox deletedCheckBox;
        public Task data;
        TaskViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.textview);
            deletedCheckBox = view.findViewById(R.id.checkBox);
        }
    }
}
