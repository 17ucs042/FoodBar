package com.appsaga.foodbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CustomerDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=" address.db";
    public static final String TABLE_NAME="AddressTable";
    public static final String COL_1=" _id";
    public static final String COL_2=" firstName";
    public static final String COL_3=" lastName";
    public static final String COL_4=" phoneNum";
    public static final String COL_5=" houseNo";
    public static final String COL_6=" apartmentName";
    public static final String COL_7=" street";
    public static final String COL_8=" landmark";
    public static final String COL_9=" area";
    public static final String COL_10=" pincode";
    public static final String COL_11=" nickname";

    public  CustomerDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME+" (_id INTEGER  , firstName TEXT ,lastName TEXT, phoneNum TEXT," +
                "houseNo TEXT, apartmentName TEXT, street TEXT, landmark TEXT, area TEXT," +
                "pincode TEXT,nickname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  boolean insertData(String firstName, String lastName, String phoneNum, String houseNo,String apartmentName,
                               String street, String landmark, String area, String pincode, String nickname)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,firstName);
        contentValues.put(COL_3,lastName);
        contentValues.put(COL_4,phoneNum);
        contentValues.put(COL_5,houseNo);
        contentValues.put(COL_6,apartmentName);
        contentValues.put(COL_7,street);
        contentValues.put(COL_8,landmark);
        contentValues.put(COL_9,area);
        contentValues.put(COL_10,pincode);
        contentValues.put(COL_11,nickname);

        db.execSQL("Delete from "+TABLE_NAME);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }

    /*public boolean update(String firstName, String lastName, String phoneNum, String houseNo,String apartmentName,
                          String street, String landmark, String area, String pincode, String nickname){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,firstName);
        contentValues.put(COL_3,lastName);
        contentValues.put(COL_4,phoneNum);
        contentValues.put(COL_5,houseNo);
        contentValues.put(COL_6,apartmentName);
        contentValues.put(COL_7,street);
        contentValues.put(COL_8,landmark);
        contentValues.put(COL_9,area);
        contentValues.put(COL_10,pincode);
        contentValues.put(COL_11,nickname);

        db.update(TABLE_NAME,contentValues,"name = ? and house = ?" ,new String[] {name,house});
        return  true;
    }*/

    public Cursor getAllData(){

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public int getTotalItems()
    {
        ArrayList<String> items = new ArrayList<>();
        int totalItems=0;
        String i;
        String q;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if (res.moveToFirst())
        {
            do
            {
                i=res.getString(1);
                if(!items.contains(i))
                {
                    items.add(i);
                }
            }while (res.moveToNext());
        }
        return items.size();
    }

    public void deleteAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from "+TABLE_NAME);
    }
}
