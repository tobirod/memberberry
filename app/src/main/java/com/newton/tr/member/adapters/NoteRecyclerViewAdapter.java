package com.newton.tr.member.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.newton.tr.member.R;
import com.newton.tr.member.database.NoteRepo;
import com.newton.tr.member.fragments.TabNote;
import com.newton.tr.member.models.Note;

import java.util.ArrayList;
import java.util.Iterator;

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Note> noteList;
    private TabNote tabNote;
    private NoteRepo noteRepo;
    public int noteColorFullViewOn = R.color.silver;
    public int noteColorFullViewOff = R.color.pastelCream;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView noteTitle;
        public TextView noteBody;
        public CheckBox checkBox;
        public RelativeLayout noteLayout;
        public TableLayout noteTable;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            noteLayout = v.findViewById(R.id.noteLayout);
            noteTable = v.findViewById(R.id.noteTable);
            noteTitle = v.findViewById(R.id.noteTitle);
            noteBody = v.findViewById(R.id.noteBody);
            checkBox = v.findViewById(R.id.checkBox);

        }
    }

    public NoteRecyclerViewAdapter(ArrayList<Note> noteData, TabNote tabNote) {
        this.noteList = noteData;
        this.tabNote = tabNote;
        this.noteRepo = new NoteRepo();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout_note, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NoteRecyclerViewAdapter.ViewHolder holder, final int position) {
        final Note mNote = noteList.get(holder.getAdapterPosition());

        holder.noteTitle.setText(mNote.getTitle());
        holder.noteBody.setText(mNote.getBody());
        mNote.setFullView(false);

        holder.checkBox.setChecked(mNote.getIsChecked());
        tabNote.setDeleteButtonVisibility(hasNotesToDelete());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    mNote.setIsChecked(true);

                } else {
                    mNote.setIsChecked(false);

                }

                tabNote.setDeleteButtonVisibility(hasNotesToDelete());
            }
        });

        holder.noteTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean fullView = mNote.getFullView();

                if (fullView) {
                    mNote.setFullView(false);
                    holder.noteBody.setSingleLine(true);
                    holder.noteLayout.setBackgroundColor(tabNote.getResources().getColor(noteColorFullViewOff));
                    holder.noteTitle.setPadding(0, 0, 0, 0);
                    holder.noteBody.setPadding(0, 0, 0, 0);
                } else if (!fullView) {
                    mNote.setFullView(true);
                    holder.noteBody.setSingleLine(false);
                    holder.noteLayout.setBackgroundColor(tabNote.getResources().getColor(noteColorFullViewOn));
                    holder.noteTitle.setPadding(0, 50, 0, 0);
                    holder.noteBody.setPadding(0, 0, 0, 50);
                }

            }
        });

        /*
        holder.noteTable.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tabNote.editTask(mNote);

                return true;
            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void deleteCheckedNotes() {

        for(Iterator<Note> iterator = this.noteList.iterator(); iterator.hasNext(); ) {
            Note note = iterator.next();
            if(note.getIsChecked()) {
                noteRepo.deleteNote(note.getId(), note.getTitle());
                iterator.remove();
            }

        }

        tabNote.setDeleteButtonVisibility(hasNotesToDelete());

        notifyDataSetChanged();
    }

    public void refreshRecyclerView() {
        noteList = noteRepo.getAllNotes();

        notifyDataSetChanged();
    }

    private boolean hasNotesToDelete() {

        for (int i = 0; i < noteList.size(); i++) {

            if (noteList.get(i).getIsChecked()) {
                return true;
            }
        }

        return false;
    }
}
