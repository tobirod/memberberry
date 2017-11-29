package com.newton.tr.member.Fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.newton.tr.member.Adapters.TaskRecyclerViewAdapter;
import com.newton.tr.member.Database.TaskRepo;
import com.newton.tr.member.Models.Task;
import com.newton.tr.member.Models.ViewModel;
import com.newton.tr.member.R;
import com.newton.tr.member.databinding.FragmentTabTaskBinding;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabTask.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabTask extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel = new ViewModel();
    private TaskRepo taskRepo = new TaskRepo();
    private TaskRecyclerViewAdapter adapter;

    int taskPrioBuffer;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabTask.
     */
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
                final View taskView = getLayoutInflater().inflate(R.layout.alertdialog_task, null);

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

                        String taskDateAdded = String.valueOf(Calendar.getInstance().getTime());
                        String taskContent = taskString.getText().toString();

                        taskRepo.addTask(taskRepo.getAllTasks().size(),false, taskDateAdded, taskContent);

                        Toast.makeText(getContext(), "Task added successfully.", Toast.LENGTH_SHORT).show();

                        adapter.refreshRecyclerView(taskRepo.getAllTasks());

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
        adapter = new TaskRecyclerViewAdapter(taskRepo.getAllTasks(), TabTask.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    public void setDeleteButtonVisibility(int entriesToBeDeleted) {
        if (entriesToBeDeleted > 0) {
            viewModel.setTaskDeleteMode(true);
        } else {
            viewModel.setTaskDeleteMode(false);
        }
    }
}
