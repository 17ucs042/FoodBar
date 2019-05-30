package com.appsaga.foodbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.drm.DrmStore;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {

    View view;
    AutoCompleteTextView searchText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    ArrayList<Item> items;
    ArrayList<String> itemsName;

    ImageView searchIcon;

    ArrayList<Item> searchedItemsArray;
    ListView searchedItems;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search_fragment, container, false);

            searchText = view.findViewById(R.id.search_text);

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            items = new ArrayList<>();
            itemsName = new ArrayList<>();

            searchIcon = view.findViewById(R.id.search_icon);
            searchedItemsArray = new ArrayList<>();
            searchedItems = view.findViewById(R.id.searchedItems);

            DatabaseReference myRef = databaseReference.child("Categories");

            final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);

            dialog.show();

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : ds.getChildren()) {
                            items.add(dataSnapshot1.getValue(Item.class));
                            itemsName.add(dataSnapshot1.getValue(Item.class).getName());
                        }
                    }

                    Log.d("Checkup", items.get(1).getName());

                    ArrayAdapter<String> itemArrayAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_list_item_1, itemsName);

                    searchText.setAdapter(itemArrayAdapter);

                    dialog.dismiss();

                    searchText.requestFocus();
                    InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imgr.showSoftInput(searchText, 0);
                    imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            searchIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);

                    searchedItemsArray.clear();
                    if (!searchText.getText().toString().equals("")) {
                        for (Item item : items) {

                            if (item.getName().equalsIgnoreCase(searchText.getText().toString()) || item.getName().toLowerCase().contains(searchText.getText().toString().toLowerCase()) ||
                                    searchText.getText().toString().toLowerCase().contains(item.getName().toLowerCase()) || searchText.getText().toString().toLowerCase().contains(item.getType().toLowerCase())
                                    || item.getType().equalsIgnoreCase(searchText.getText().toString()) || item.getType().toLowerCase().contains(searchText.getText().toString().toLowerCase())) {

                                searchedItemsArray.add(item);
                            }
                        }

                        ItemAdapter itemAdapter = new ItemAdapter(getContext(), searchedItemsArray);
                        searchedItems.setAdapter(null);
                        searchedItems.setAdapter(itemAdapter);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialog.dismiss();
                            }
                        },5000);
                    }
                }
            });

            searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {

                        searchIcon.performClick();
                        handled = true;
                    }
                    return handled;
                }
            });
        }
        else
        {
            searchText.requestFocus();
            InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imgr.showSoftInput(searchText, 0);
            imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

       // dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(dialog!=null)
        dialog.dismiss();
    }
}
