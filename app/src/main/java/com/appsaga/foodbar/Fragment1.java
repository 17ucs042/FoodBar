package com.appsaga.foodbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Fragment1 extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_fragment1, container, false);

        ImageView im3_1 = view.findViewById(R.id.im3_1);
        ImageView im3_2 = view.findViewById(R.id.im3_2);
        ImageView im3_3 = view.findViewById(R.id.im3_3);
        ImageView im3_4 = view.findViewById(R.id.im3_4);
        ImageView im3_5 = view.findViewById(R.id.im3_5);
        ImageView im3_6 = view.findViewById(R.id.im3_6);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference imagesRef1 = storageRef.child("Images/im1.jpg");
        StorageReference imagesRef2 = storageRef.child("Images/im2.jpg");
        StorageReference imagesRef3 = storageRef.child("Images/im3.jpg");
        StorageReference imagesRef4 = storageRef.child("Images/im4.jpg");
        StorageReference imagesRef5 = storageRef.child("Images/im5.jpg");
        StorageReference imagesRef7 = storageRef.child("Images/im7.jpg");
        StorageReference imagesRef8 = storageRef.child("Images/im8.jpg");
        StorageReference imagesRef9 = storageRef.child("Images/im9.jpg");
        StorageReference imagesRef10 = storageRef.child("Images/im10.jpg");
        StorageReference imagesRef11 = storageRef.child("Images/im11.jpg");
        StorageReference imagesRef12 = storageRef.child("Images/im12.jpg");
        StorageReference imagesRef13 = storageRef.child("Images/im13.jpg");
        StorageReference imagesRef14 = storageRef.child("Images/im14.jpg");

        ImageView im1 = view.findViewById(R.id.im1);
        ImageView im2 = view.findViewById(R.id.im2);
        ImageView im3 = view.findViewById(R.id.im3);
        ImageView im4 = view.findViewById(R.id.im4);
        ImageView im5 = view.findViewById(R.id.im5);
        ImageView im7 = view.findViewById(R.id.im7);
        ImageView im8 = view.findViewById(R.id.im8);
        ImageView im9 = view.findViewById(R.id.im9);
        ImageView im10 = view.findViewById(R.id.im10);
        ImageView im11 = view.findViewById(R.id.im11);
        ImageView im12 = view.findViewById(R.id.im12);
        ImageView im13 = view.findViewById(R.id.im13);
        ImageView im14 = view.findViewById(R.id.im14);

        GlideApp.with(this).load(imagesRef1).into(im1);
        GlideApp.with(this).load(imagesRef2).into(im2);
        GlideApp.with(this).load(imagesRef3).into(im3);
        GlideApp.with(this).load(imagesRef4).into(im4);
        GlideApp.with(this).load(imagesRef5).into(im5);
        GlideApp.with(this).load(imagesRef7).into(im7);
        GlideApp.with(this).load(imagesRef8).into(im8);
        GlideApp.with(this).load(imagesRef9).into(im9);
        GlideApp.with(this).load(imagesRef10).into(im10);
        GlideApp.with(this).load(imagesRef11).into(im11);
        GlideApp.with(this).load(imagesRef12).into(im12);
        GlideApp.with(this).load(imagesRef13).into(im13);
        GlideApp.with(this).load(imagesRef14).into(im14);

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
