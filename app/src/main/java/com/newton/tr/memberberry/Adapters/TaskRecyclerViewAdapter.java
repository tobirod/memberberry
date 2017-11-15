package com.newton.tr.memberberry.Adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.newton.tr.memberberry.Models.Task;
import com.newton.tr.memberberry.Models.ViewModel;
import com.newton.tr.memberberry.R;
import com.newton.tr.memberberry.databinding.FragmentTabTaskBinding;
import com.newton.tr.memberberry.databinding.RowBinding;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class TaskRecyclerViewAdapter extends RealmRecyclerViewAdapter<Task, TaskRecyclerViewAdapter.TaskViewHolder> {

    private ViewModel viewModel = new ViewModel();

    public TaskRecyclerViewAdapter(OrderedRealmCollection<Task> data) {
        super(data, true);
        setHasStableIds(true);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        Context context = parent.getContext();

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        RowBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row, parent, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        final Task obj = getItem(position);
        holder.data = obj;
        //noinspection ConstantConditions

        final int itemPosition = holder.getAdapterPosition();

        assert obj != null;

        if (holder.data.getStatus()) {
            holder.title.setText(obj.getTask());
            holder.title.setTextColor(Color.GRAY);
            holder.title.setTypeface(holder.title.getTypeface(), Typeface.ITALIC);
            holder.title.setPaintFlags(holder.title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.title.setText(obj.getTask());
            holder.title.setTextColor(Color.DKGRAY);
            holder.title.setTypeface(holder.title.getTypeface(), Typeface.BOLD);
            holder.title.setPaintFlags(0);

        }

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateStatus(itemPosition, holder.data.getUUID());
            }
        });

        holder.deletedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (viewModel.getTaskDeleteMode()) {
                    viewModel.setTaskDeleteMode(false);
                } else {
                    viewModel.setTaskDeleteMode(true);
                }
            }
        });
    }

    @Override
    public long getItemId(int index) {
        //noinspection ConstantConditions
        return getItem(index).getId();
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

    private void updateStatus(final int position, String taskUUID) {

        Realm taskRealm = Realm.getDefaultInstance();

        final Task updateTask = taskRealm.where(Task.class).equalTo("UUID", taskUUID).findFirst();

        final boolean updateTaskStatus = updateTask.getStatus();

        taskRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (updateTaskStatus) {
                    updateTask.setStatus(false);
                } else {
                    updateTask.setStatus(true);
                }
            }
        });

        notifyItemChanged(position);
    }
}
