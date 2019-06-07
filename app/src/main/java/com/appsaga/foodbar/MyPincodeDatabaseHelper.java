package com.appsaga.foodbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyPincodeDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=" pincode.db";
    public static final String TABLE_NAME="PincodeTable";
    public static final String COL_1=" _id";
    public static final String COL_2=" pincode";

    public MyPincodeDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME+" (_id INTEGER, pincode TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  boolean insertData(String pincode)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,pincode);

        db.execSQL ("delete from "+TABLE_NAME);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }

    public int getTotalItems()
    {
        ArrayList<String> pincodes = new ArrayList<>();
        int totalItems=0;
        String i;
        int q;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if (res.moveToFirst())
        {
            do
            {
                i=res.getString(1);
                if(!pincodes.contains(i))
                {
                    pincodes.add(i);
                }
            }while (res.moveToNext());
        }
        return pincodes.size();
    }

    public String getPincode()
    {
        String pincode=null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        res.moveToFirst();
        pincode = res.getString(1);

        return pincode;
    }

    public void deleteData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL ("delete from "+TABLE_NAME);
    }
}
