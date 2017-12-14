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
import android.widget.TextView;
import android.widget.Toast;

import com.newton.tr.member.R;
import com.newton.tr.member.adapters.NoteRecyclerViewAdapter;
import com.newton.tr.member.database.NoteRepo;
import com.newton.tr.member.databinding.FragmentTabNoteBinding;
import com.newton.tr.member.models.Note;
import com.newton.tr.member.models.ViewModel;

public class TabNote extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel = new ViewModel();
    private NoteRepo noteRepo = new NoteRepo();
    private NoteRecyclerViewAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TabNote() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabNote.
     */
    // TODO: Rename and change types and number of parameters
    public static TabNote newInstance(String param1, String param2) {
        TabNote fragment = new TabNote();
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
        FragmentTabNoteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_note, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);

        recyclerView = view.findViewById(R.id.recyclerView_Note);
        Button newNoteButton = view.findViewById(R.id.btnAdd_Note);
        final Button deleteNotesButton = view.findViewById(R.id.btnDelete_Notes);

        setUpRecyclerView();

        deleteNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCheckedNotes();
            }
        });

        newNoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                final View noteView = getLayoutInflater().inflate(R.layout.alertdialog_newnote, null);

                final EditText noteTitleEditText = noteView.findViewById(R.id.noteTitleEditText);
                final EditText noteBodyEditText = noteView.findViewById(R.id.noteBodyEditText);
                final Button cancelButton = noteView.findViewById(R.id.noteAlertDialogCancel);
                final Button doneButton = noteView.findViewById(R.id.noteAlertDialogDone);

                setUpRecyclerView();

                dialogBuilder.setView(noteView);
                final AlertDialog noteDialog = dialogBuilder.create();
                noteDialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        noteDialog.cancel();
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String noteTitle = noteTitleEditText.getText().toString();
                        String noteBody = noteBodyEditText.getText().toString();

                        noteRepo.addNote(noteRepo.getAllNotes().size(), noteTitle, noteBody);

                        customToast("New note added successfully.");

                        adapter.refreshRecyclerView();

                        noteDialog.dismiss();

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
        adapter = new NoteRecyclerViewAdapter(noteRepo.getAllNotes(), TabNote.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    public void setDeleteButtonVisibility(boolean showButton) {
        if (showButton) {
            viewModel.setNoteDeleteMode(true);
        } else {
            viewModel.setNoteDeleteMode(false);
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
/*
    public void editTask(final Note note) {

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
    */
}
