package com.appsaga.foodbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class HomeFragment extends Fragment {

    View view;
    ViewPager viewPager;
    ScrollView scrollView;
    TabLayout tabLayout;
    MyPincodeDatabaseHelper myPincodeDatabaseHelper;
    final long ONE_MEGABYTE = 1024 * 1024;
    ImageView[] im = new ImageView[100];
    String totalImages;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

            final ProgressDialog dialog = ProgressDialog.show(getContext(), "Loading", "Please wait...", true);

            TextView searchText = view.findViewById(R.id.search_text);
            RelativeLayout searchBar = view.findViewById(R.id.search_item_bar);
            viewPager = getActivity().findViewById(R.id.viewpager);
            scrollView = view.findViewById(R.id.scroll);
            tabLayout = getActivity().findViewById(R.id.tabs);
            myPincodeDatabaseHelper = new MyPincodeDatabaseHelper(getContext());
            databaseReference= FirebaseDatabase.getInstance().getReference();

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

            im[1] = view.findViewById(R.id.im1);
            im[2] = view.findViewById(R.id.im2);
            im[3] = view.findViewById(R.id.im3);
            im[4] = view.findViewById(R.id.im4);
            im[5] = view.findViewById(R.id.im5);
            im[6] = view.findViewById(R.id.im6);
            im[7] = view.findViewById(R.id.im7);
            im[8] = view.findViewById(R.id.im8);

            ImageView im8_1 = view.findViewById(R.id.im8_1);
            ImageView im8_2 = view.findViewById(R.id.im8_2);
            ImageView im8_3 = view.findViewById(R.id.im8_3);
            ImageView im8_4 = view.findViewById(R.id.im8_4);
            ImageView im8_5 = view.findViewById(R.id.im8_5);
            ImageView im8_6 = view.findViewById(R.id.im8_6);
            ImageView im8_7 = view.findViewById(R.id.im8_7);
            ImageView im8_8 = view.findViewById(R.id.im8_8);

            im[9] = view.findViewById(R.id.im9);
            im[10] = view.findViewById(R.id.im10);
            im[11] = view.findViewById(R.id.im11);
            im[12] = view.findViewById(R.id.im12);
            im[13] = view.findViewById(R.id.im13);

            ImageView im11_1 = view.findViewById(R.id.im11_1);
            ImageView im11_2 = view.findViewById(R.id.im11_2);
            ImageView im11_3 = view.findViewById(R.id.im11_3);
            ImageView im11_4 = view.findViewById(R.id.im11_4);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference();
            final TextView placeName = getActivity().findViewById(R.id.placeName);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    totalImages=dataSnapshot.child("totalImages").getValue(String.class);

                    Log.d("Total...",totalImages+"");

                    for (int i = 1; i <= Integer.valueOf(totalImages); i++) {
                        final int finalI = i;
                        storageRef.child("Images/im" + i + ".jpg").getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {

                                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                im[finalI].setImageBitmap(bm);
                                if(finalI==3)
                                {
                                    dialog.dismiss();
                                }
                            }
                        });
                    }

                    for(int i=1;i<=Integer.valueOf(totalImages);i++) {
                        final int finalI = i;
                        im[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                databaseReference.child("imageClick").child(""+ finalI).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String clickValue1 = null;
                                        String clickValue2 = null;
                                        String clickValue3 = null;
                                        Intent intent = new Intent(getContext(),ShowItems.class);

                                        if(dataSnapshot.child("name").getValue(String.class)!=null)
                                        {
                                            clickValue1=dataSnapshot.child("name").getValue(String.class);
                                            intent.putExtra("name",clickValue1);
                                        }
                                        if(dataSnapshot.child("type").getValue(String.class)!=null)
                                        {
                                            clickValue2=dataSnapshot.child("type").getValue(String.class);
                                            intent.putExtra("type",clickValue2);
                                            Log.d("Value...",clickValue2);
                                        }
                                        if(dataSnapshot.child("category").getValue(String.class)!=null)
                                        {
                                            clickValue3=dataSnapshot.child("category").getValue(String.class);
                                            intent.putExtra("category",clickValue3);
                                        }

                                        if(clickValue1!=null || clickValue2!=null || clickValue3!=null)
                                        {
                                            intent.putExtra("pin", placeName.getText().toString());
                                        startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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

            im8_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im8_8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im11_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im11_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im11_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            im11_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
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