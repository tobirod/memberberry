package com.newton.tr.member.Fragments;

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

import com.newton.tr.member.Adapters.ItemRecyclerViewAdapter;
import com.newton.tr.member.Database.ItemRepo;
import com.newton.tr.member.Database.TaskRepo;
import com.newton.tr.member.Models.Task;
import com.newton.tr.member.Models.ViewModel;
import com.newton.tr.member.R;
import com.newton.tr.member.databinding.FragmentTabTaskBinding;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TabShop extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel = new ViewModel();
    private ItemRepo itemRepo = new ItemRepo();
    private ItemRecyclerViewAdapter adapter;
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

    public TabShop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabShop.
     */
    // TODO: Rename and change types and number of parameters
    public static TabShop newInstance(String param1, String param2) {
        TabShop fragment = new TabShop();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentTabItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_shop, container, false);
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

                        itemRepo.addTask(itemRepo.getAllTasks().size(),false, taskDateAdded, taskContent);

                        customToast("Task added successfully.");

                        adapter.refreshRecyclerView();

                        taskDialog.dismiss();

                    }
                });
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void setUpRecyclerView() {
        adapter = new ItemRecyclerViewAdapter(taskRepo.getAllTasks(), TabShop.this);
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

        taskString.setText(task.getTaskContent());
        taskDate.setText(task.getTaskDateAdded());

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

                if (task.getTaskStatus()) {
                    taskStatus = 1;
                }

                if (newDate.isChecked()) {

                    taskDateAdded = timeFormat.format(today);

                } else {
                    taskDateAdded = task.getTaskDateAdded();
                }

                taskRepo.updateTask(task.getTaskId(), taskStatus, taskDateAdded, taskContent, task.getTaskContent());

                customToast("Task updated successfully!");

                adapter.refreshRecyclerView();

                taskDialog.dismiss();

            }
        });

    }
}
