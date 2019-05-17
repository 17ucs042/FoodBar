package com.appsaga.foodbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.signature.ObjectKey;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class HomeFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home_fragment, container, false);

        ImageView im3_1 = view.findViewById(R.id.im3_1);
        ImageView im3_2 = view.findViewById(R.id.im3_2);
        ImageView im3_3 = view.findViewById(R.id.im3_3);
        ImageView im3_4 = view.findViewById(R.id.im3_4);
        ImageView im3_5 = view.findViewById(R.id.im3_5);
        ImageView im3_6 = view.findViewById(R.id.im3_6);

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

        GlideApp.with(HomeFragment.this).load(imagesRef1).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im1);
        GlideApp.with(HomeFragment.this).load(imagesRef2).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im2);
        GlideApp.with(HomeFragment.this).load(imagesRef3).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im3);
        GlideApp.with(HomeFragment.this).load(imagesRef4).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im4);
        GlideApp.with(HomeFragment.this).load(imagesRef5).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im5);
        GlideApp.with(HomeFragment.this).load(imagesRef7).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im7);
        GlideApp.with(HomeFragment.this).load(imagesRef8).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im8);
        GlideApp.with(HomeFragment.this).load(imagesRef9).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im9);
        GlideApp.with(HomeFragment.this).load(imagesRef10).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im10);
        GlideApp.with(HomeFragment.this).load(imagesRef11).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im11);
        GlideApp.with(HomeFragment.this).load(imagesRef12).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im12);
        GlideApp.with(HomeFragment.this).load(imagesRef13).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im13);
        GlideApp.with(HomeFragment.this).load(imagesRef14).signature(new ObjectKey(String.valueOf(System.currentTimeMillis()))).into(im14);

        im3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), com.appsaga.foodbar.Category1.class);
                startActivity(intent);
            }
        });

        im3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), com.appsaga.foodbar.Category2.class);
                startActivity(intent);
            }
        });

        im3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), com.appsaga.foodbar.Category3.class);
                startActivity(intent);
            }
        });

        im3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), com.appsaga.foodbar.Category4.class);
                startActivity(intent);
            }
        });

        im3_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), com.appsaga.foodbar.Category5.class);
                startActivity(intent);
            }
        });

        im3_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), com.appsaga.foodbar.Category6.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
