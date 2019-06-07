package com.appsaga.foodbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class HomeFragment extends Fragment {

    View view;
    ViewPager viewPager;
    ScrollView scrollView;
    TabLayout tabLayout;
    MyPincodeDatabaseHelper myPincodeDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

            final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);
            dialog.show();

            TextView searchText = view.findViewById(R.id.search_text);
            RelativeLayout searchBar = view.findViewById(R.id.search_item_bar);
            viewPager = getActivity().findViewById(R.id.viewpager);
            scrollView = view.findViewById(R.id.scroll);
            tabLayout = getActivity().findViewById(R.id.tabs);
            myPincodeDatabaseHelper = new MyPincodeDatabaseHelper(getContext());

            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY - oldScrollY > 3 || scrollY == scrollView.getHeight()) {
                        tabLayout.setVisibility(View.GONE);
                    } else if (scrollY + 1 < oldScrollY || scrollY == 0) {
                        tabLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            ImageView im3_1 = view.findViewById(R.id.im3_1);
            ImageView im3_2 = view.findViewById(R.id.im3_2);
            ImageView im3_3 = view.findViewById(R.id.im3_3);
            ImageView im3_4 = view.findViewById(R.id.im3_4);
            ImageView im3_5 = view.findViewById(R.id.im3_5);
            ImageView im3_6 = view.findViewById(R.id.im3_6);
            ImageView im3_7 = view.findViewById(R.id.im3_7);
            ImageView im3_8 = view.findViewById(R.id.im3_8);
            ImageView im3_9 = view.findViewById(R.id.im3_9);

            final ImageView im1 = view.findViewById(R.id.im1);
            final ImageView im2 = view.findViewById(R.id.im2);
            final ImageView im3 = view.findViewById(R.id.im3);
            final ImageView im4 = view.findViewById(R.id.im4);
            final ImageView im5 = view.findViewById(R.id.im5);
            final ImageView im7 = view.findViewById(R.id.im7);
            final ImageView im8 = view.findViewById(R.id.im8);
            final ImageView im9 = view.findViewById(R.id.im9);
            final ImageView im10 = view.findViewById(R.id.im10);
            final ImageView im11 = view.findViewById(R.id.im11);
            final ImageView im12 = view.findViewById(R.id.im12);
            final ImageView im13 = view.findViewById(R.id.im13);
            final ImageView im14 = view.findViewById(R.id.im14);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            final StorageReference imagesRef1 = storageRef.child("Images/im1.jpg");
            final StorageReference imagesRef2 = storageRef.child("Images/im2.jpg");
            final StorageReference imagesRef3 = storageRef.child("Images/im3.jpg");
            final StorageReference imagesRef4 = storageRef.child("Images/im4.jpg");
            final StorageReference imagesRef5 = storageRef.child("Images/im5.jpg");
            final StorageReference imagesRef7 = storageRef.child("Images/im7.jpg");
            final StorageReference imagesRef8 = storageRef.child("Images/im8.jpg");
            final StorageReference imagesRef9 = storageRef.child("Images/im9.jpg");
            final StorageReference imagesRef10 = storageRef.child("Images/im10.jpg");
            final StorageReference imagesRef11 = storageRef.child("Images/im11.jpg");
            final StorageReference imagesRef12 = storageRef.child("Images/im12.jpg");
            final StorageReference imagesRef13 = storageRef.child("Images/im13.jpg");
            final StorageReference imagesRef14 = storageRef.child("Images/im14.jpg");

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    GlideApp.with(HomeFragment.this).load(imagesRef1).into(im1);
                    GlideApp.with(HomeFragment.this).load(imagesRef2).into(im2);
                    GlideApp.with(HomeFragment.this).load(imagesRef3).into(im3);
                    GlideApp.with(HomeFragment.this).load(imagesRef4).into(im4);
                    GlideApp.with(HomeFragment.this).load(imagesRef5).into(im5);
                    GlideApp.with(HomeFragment.this).load(imagesRef7).into(im7);
                    GlideApp.with(HomeFragment.this).load(imagesRef8).into(im8);
                    GlideApp.with(HomeFragment.this).load(imagesRef9).into(im9);
                    GlideApp.with(HomeFragment.this).load(imagesRef10).into(im10);
                    GlideApp.with(HomeFragment.this).load(imagesRef11).into(im11);
                    GlideApp.with(HomeFragment.this).load(imagesRef12).into(im12);
                    GlideApp.with(HomeFragment.this).load(imagesRef13).into(im13);
                    GlideApp.with(HomeFragment.this).load(imagesRef14).into(im14);
                }
            }, 3000);

            Handler handler1 = new Handler();

            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    dialog.dismiss();
                }
            }, 5000);

            final TextView placeName = getActivity().findViewById(R.id.placeName);
            final Dialog customDialog = new Dialog(getContext());
            customDialog.setContentView(R.layout.custom_dialog);

            final EditText dialogPincode = customDialog.findViewById(R.id.dialog_pincode);
            final Button go = customDialog.findViewById(R.id.button_go);

            im3_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Fruits & Vegetables");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);


                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Fruits & Vegetables");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Foodgrains, Oils & Masalas");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Foodgrains, Oils & Masalas");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                                Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                intent.putExtra("value", "Bakery, Cakes & Dairy");
                                intent.putExtra("pin", placeName.getText().toString());
                                startActivity(intent);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Bakery, Cakes & Dairy");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Beverages & Snacks");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);
                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Beverages & Snacks");
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Medicine");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Medicine");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Stationary");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Stationary");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Cleaning, Kitchen & Baby Care");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Cleaning, Kitchen & Baby Care");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Pet Supplies");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);
                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Pet Supplies");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            im3_9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (placeName.getText().length() == 6) {

                        Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                        intent.putExtra("value", "Eggs, Meat, Fish");
                        intent.putExtra("pin", placeName.getText().toString());
                        startActivity(intent);

                    } else {
                        customDialog.show();
                        go.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dialogPincode.getText().toString().trim().length() == 6) {
                                    Intent intent = new Intent(getContext(), com.appsaga.foodbar.ShowItems.class);
                                    intent.putExtra("value", "Eggs, Meat, Fish");
                                    intent.putExtra("pin", dialogPincode.getText().toString());
                                    placeName.setText(dialogPincode.getText().toString());
                                    myPincodeDatabaseHelper.insertData(dialogPincode.getText().toString());
                                    startActivity(intent);
                                    customDialog.dismiss();
                                } else {
                                    dialogPincode.setError("Invalid Pincode");
                                }
                            }
                        });
                    }
                }
            });

            searchBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                    viewPager.setCurrentItem(2);
                }
            });

            searchText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ViewPager viewPager = getActivity().findViewById(R.id.viewpager);
                    viewPager.setCurrentItem(2);
                }
            });

        }
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");

                if (result.equalsIgnoreCase("Home")) {
                    viewPager.setCurrentItem(0);
                } else if (result.equalsIgnoreCase("Categories")) {
                    Log.d("test....", "Yese");
                    viewPager.setCurrentItem(1);
                } else if (result.equalsIgnoreCase("Search")) {
                    viewPager.setCurrentItem(2);
                } else if (result.equalsIgnoreCase("Offers")) {
                    viewPager.setCurrentItem(3);
                } else {
                    viewPager.setCurrentItem(4);
                }
            }
        }
    }
}