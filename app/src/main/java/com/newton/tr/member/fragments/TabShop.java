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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.newton.tr.member.R;
import com.newton.tr.member.adapters.ItemRecyclerViewAdapter;
import com.newton.tr.member.adapters.TaskRecyclerViewAdapter;
import com.newton.tr.member.database.ItemRepo;
import com.newton.tr.member.databinding.FragmentTabShopBinding;
import com.newton.tr.member.models.Item;
import com.newton.tr.member.models.ViewModel;

public class TabShop extends Fragment {

    private RecyclerView recyclerView;
    private ViewModel viewModel = new ViewModel();
    private ItemRepo itemRepo = new ItemRepo();
    private ItemRecyclerViewAdapter adapter;

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

        FragmentTabShopBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_shop, container, false);
        View view = binding.getRoot();
        binding.setViewModel(viewModel);

        recyclerView = view.findViewById(R.id.recyclerView_Item);
        Button newItemButton = view.findViewById(R.id.btnAdd_Item);
        final Button deleteItemsButton = view.findViewById(R.id.btnDelete_Items);

        setUpRecyclerView();

        deleteItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.deleteCheckedItems();
            }
        });

        newItemButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                final View itemView = getLayoutInflater().inflate(R.layout.alertdialog_newitem, null);

                final EditText itemString = itemView.findViewById(R.id.itemEditText);
                final Button cancelButton = itemView.findViewById(R.id.itemAlertDialogCancel);
                final Button doneButton = itemView.findViewById(R.id.itemAlertDialogDone);

                dialogBuilder.setView(itemView);
                final AlertDialog itemDialog = dialogBuilder.create();
                itemDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                itemString.setSelection(itemString.getText().toString().length());
                itemDialog.show();

                cancelButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        itemDialog.cancel();
                    }
                });

                doneButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String itemName = itemString.getText().toString();

                        itemRepo.addItem(itemRepo.getAllItems().size(),false, itemName);

                        customToast("New item added successfully.");

                        adapter.refreshRecyclerView();

                        itemDialog.dismiss();

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
        adapter = new ItemRecyclerViewAdapter(itemRepo.getAllItems(), TabShop.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    public void setDeleteButtonVisibility(boolean showButton) {
        if (showButton) {
            viewModel.setShopDeleteMode(true);
        } else {
            viewModel.setShopDeleteMode(false);
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

    public void editItem(final Item item) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final View itemView = getLayoutInflater().inflate(R.layout.alertdialog_edititem, null);

        final EditText itemEditText = itemView.findViewById(R.id.itemEditText);
        final Button cancelButton = itemView.findViewById(R.id.taskAlertDialogCancel);
        final Button doneButton = itemView.findViewById(R.id.taskAlertDialogDone);

        itemEditText.setText(item.getName());

        dialogBuilder.setView(itemView);
        final AlertDialog itemDialog = dialogBuilder.create();
        itemDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        itemEditText.setSelection(itemEditText.getText().toString().length());
        itemDialog.show();

        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                itemDialog.cancel();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemName = itemEditText.getText().toString();
                int itemStatus = 0;

                if (item.getStatus()) {
                    itemStatus = 1;
                }

                itemRepo.updateItem(item.getId(), itemStatus, itemName, item.getName());

                customToast("Item updated successfully!");

                adapter.refreshRecyclerView();

                itemDialog.dismiss();

            }
        });

    }

}
