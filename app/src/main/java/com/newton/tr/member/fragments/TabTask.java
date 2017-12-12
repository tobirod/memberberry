package com.newton.tr.member.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.newton.tr.member.adapters.TaskRecyclerViewAdapter;
import com.newton.tr.member.database.TaskRepo;
import com.newton.tr.member.models.Task;
import com.newton.tr.member.models.ViewModel;
import com.newton.tr.member.R;
import com.newton.tr.member.databinding.FragmentTabTaskBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TabTask extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel = new ViewModel();
    private TaskRepo taskRepo = new TaskRepo();
    private TaskRecyclerViewAdapter adapter;
    Date today = (Calendar.getInstance().getTime());
    DateFormat timeFormat = new SimpleDateFormat("YYYY-MM-d, HH:mm z", Locale.getDefault());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TabTask() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TabTask newInstance(String param1, String param2) {
        TabTask fragment = new TabTask();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentTabTaskBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_task, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);

        recyclerView = view.findViewById(R.id.recyclerView_Task);
        Button newTaskButton = view.findViewById(R.id.btnAdd_Task);
        final Button deleteTasksButton = view.findViewById(R.id.btnDelete_Tasks);

        deleteTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCheckedTasks();
            }
        });

        newTaskButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                final View taskView = getLayoutInflater().inflate(R.layout.alertdialog_newtask, null);

                final EditText taskString = taskView.findViewById(R.id.taskEditText);
                final Button cancelButton = taskView.findViewById(R.id.taskAlertDialogCancel);
                final Button doneButton = taskView.findViewById(R.id.taskAlertDialogDone);

                setUpRecyclerView();

                dialogBuilder.setView(taskView);
                final AlertDialog taskDialog = dialogBuilder.create();
                taskDialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        taskDialog.cancel();
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String taskDateAdded = timeFormat.format(today);
                        String taskContent = taskString.getText().toString();

                        taskRepo.addTask(taskRepo.getAllTasks().size(),false, taskDateAdded, taskContent);

                        customToast("Task added successfully.");

                        adapter.refreshRecyclerView();

                        taskDialog.dismiss();

                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        setUpRecyclerView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setUpRecyclerView() {
        adapter = new TaskRecyclerViewAdapter(taskRepo.getAllTasks(), TabTask.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    public void setDeleteButtonVisibility(boolean showButton) {
        if (showButton) {
            viewModel.setTaskDeleteMode(true);
        } else {
            viewModel.setTaskDeleteMode(false);
        }
    }

    public void customToast(String message) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView toast_text = layout.findViewById(R.id.toast_text);
        toast_text.setText(message);

        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }

    public void editTask(final Task task) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final View taskView = getLayoutInflater().inflate(R.layout.alertdialog_edittask, null);

        final EditText taskString = taskView.findViewById(R.id.taskEditText);
        final TextView taskDate = taskView.findViewById(R.id.currentTaskDate);
        final RadioButton newDate = taskView.findViewById(R.id.newDateRadioBtn);
        final Button cancelButton = taskView.findViewById(R.id.taskAlertDialogCancel);
        final Button doneButton = taskView.findViewById(R.id.taskAlertDialogDone);

        taskString.setText(task.getTask());
        taskDate.setText(task.getDateAdded());

        dialogBuilder.setView(taskView);
        final AlertDialog taskDialog = dialogBuilder.create();
        taskDialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                taskDialog.cancel();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskDateAdded;
                String taskContent = taskString.getText().toString();
                int taskStatus = 0;

                if (task.getStatus()) {
                    taskStatus = 1;
                }

                if (newDate.isChecked()) {

                    taskDateAdded = timeFormat.format(today);

                } else {
                    taskDateAdded = task.getDateAdded();
                }

                taskRepo.updateTask(task.getId(), taskStatus, taskDateAdded, taskContent, task.getTask());

                customToast("Task updated successfully!");

                adapter.refreshRecyclerView();

                taskDialog.dismiss();

            }
        });

    }
}
