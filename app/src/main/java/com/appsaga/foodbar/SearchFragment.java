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
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SearchFragment extends Fragment {

    View view;
    AutoCompleteTextView searchText;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference myRef;

    ArrayList<Item> items;
    ArrayList<String> itemsName;

    ImageView searchIcon;
    ImageView backNavigate;

    ArrayList<Item> searchedItemsArray;
    ListView searchedItems;
    ProgressDialog dialog;
    TextView placeName;

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
            backNavigate = view.findViewById(R.id.back_navigate);

            placeName = getActivity().findViewById(R.id.placeName);

            if(placeName.getText().toString().trim().length()==6) {

                final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);
                dialog.show();

                DatabaseReference delivers = databaseReference.child("Delivers");

                delivers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        HashMap<String,String> delivers;
                        final ArrayList<String> delivery_pincode = new ArrayList<>();

                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            delivers = (HashMap<String,String>)ds.getValue();

                            for(Map.Entry<String,String> entry : delivers.entrySet())
                            {
                                if(entry.getValue().equalsIgnoreCase(placeName.getText().toString().trim()))
                                {
                                    if(!delivery_pincode.contains(entry.getKey()))
                                    {
                                        delivery_pincode.add(entry.getKey());
                                    }
                                }
                            }
                        }

                        for(int i=0;i<delivery_pincode.size();i++) {
                            myRef = databaseReference.child("Categories").child(delivery_pincode.get(i));

                            myRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        for (DataSnapshot dataSnapshot1 : ds.getChildren()) {

                                            if(!itemsName.contains(dataSnapshot1.getValue(Item.class).getName()))
                                            {
                                                itemsName.add(Objects.requireNonNull(dataSnapshot1.getValue(Item.class)).getName());
                                                items.add(dataSnapshot1.getValue(Item.class));
                                            }
                                        }
                                    }

                                    ArrayAdapter<String> itemArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                            android.R.layout.simple_list_item_1, itemsName);

                                    searchText.setAdapter(itemArrayAdapter);

                                    dialog.dismiss();

                                    searchText.requestFocus();
                                    InputMethodManager imgr = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imgr.showSoftInput(searchText, 0);
                                    imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else
            {
                Toast.makeText(getContext(),"Location not set",Toast.LENGTH_LONG).show();
            }

            backNavigate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Objects.requireNonNull(getActivity()).onBackPressed();
                }
            });

            searchIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(placeName.getText().toString().trim().length()==6) {
                        final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);

                        Log.d("Checkup", items.size()+"");
                        Log.d("Checkup", searchedItemsArray.size()+"");

                        searchedItemsArray.clear();
                        if (!searchText.getText().toString().equals("")) {
                            for (Item item : items) {

                                if (item.getName().equalsIgnoreCase(searchText.getText().toString()) || item.getName().toLowerCase().contains(searchText.getText().toString().toLowerCase()) ||
                                        searchText.getText().toString().toLowerCase().contains(item.getName().toLowerCase()) || searchText.getText().toString().toLowerCase().contains(item.getType().toLowerCase())
                                        || item.getType().equalsIgnoreCase(searchText.getText().toString()) || item.getType().toLowerCase().contains(searchText.getText().toString().toLowerCase())) {

                                    if(!searchedItemsArray.contains(item))
                                    {
                                        searchedItemsArray.add(item);
                                    }
                                }
                            }

                            if (searchedItemsArray.size() != 0) {
                                ItemAdapter itemAdapter = new ItemAdapter(getContext(), searchedItemsArray);
                                searchedItems.setAdapter(null);
                                searchedItems.setAdapter(itemAdapter);
                            } else {
                                Toast.makeText(getContext(), "No items found", Toast.LENGTH_LONG).show();
                            }
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    dialog.dismiss();
                                }
                            }, 5000);
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Please set location",Toast.LENGTH_LONG).show();
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
            //searchText.requestFocus();
            //InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            //imgr.showSoftInput(searchText, 0);
            //imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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

        if(placeName.getText().toString().trim().length()==6) {

            final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);
            dialog.show();

            DatabaseReference delivers = databaseReference.child("Delivers");

            delivers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    HashMap<String,String> delivers;
                    ArrayList<String> delivery_pincode = new ArrayList<>();

                    for(DataSnapshot ds: dataSnapshot.getChildren())
                    {
                        delivers = (HashMap<String,String>)ds.getValue();

                        for(Map.Entry<String,String> entry : delivers.entrySet())
                        {
                            if(entry.getValue().equalsIgnoreCase(placeName.getText().toString().trim()))
                            {
                                if(!delivery_pincode.contains(entry.getKey()))
                                {
                                    delivery_pincode.add(entry.getKey());
                                }
                            }
                        }
                    }

                    for(int i=0;i<delivery_pincode.size();i++) {
                        myRef = databaseReference.child("Categories").child(delivery_pincode.get(i));

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    for (DataSnapshot dataSnapshot1 : ds.getChildren()) {

                                        if(!itemsName.contains(dataSnapshot1.getValue(Item.class).getName()))
                                        {
                                            itemsName.add(Objects.requireNonNull(dataSnapshot1.getValue(Item.class)).getName());
                                            items.add(dataSnapshot1.getValue(Item.class));
                                        }
                                    }
                                }

                                Log.d("Checkup", items.get(0).getName());

                                ArrayAdapter<String> itemArrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                                        android.R.layout.simple_list_item_1, itemsName);

                                searchText.setAdapter(itemArrayAdapter);

                                dialog.dismiss();

                                searchText.requestFocus();
                                InputMethodManager imgr = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
                                imgr.showSoftInput(searchText, 0);
                                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
            Toast.makeText(getContext(),"Location not set",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if(isVisibleToUser==Boolean.TRUE) {
            if (searchText != null) {

                searchText.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.showSoftInput(searchText, 0);
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }
}
