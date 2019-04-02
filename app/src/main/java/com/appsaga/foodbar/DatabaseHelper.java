package com.appsaga.foodbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=" cart.db";
    public static final String TABLE_NAME="OrderTable";
    public static final String COL_1=" item";
    public static final String COL_2=" quantity";
    public static final String COL_3=" price";
    public static final String COL_4=" totalprice";

    public  DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    DatabaseHelper mDatabaseHelper ;

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME+" (item TEXT,quantity INTEGER,price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public  boolean insertData(String item, int quantity, String price)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,item);
        contentValues.put(COL_2,quantity);
        contentValues.put(COL_3,price);
     //   contentValues.put(COL_4,totalprice);
        //  contentValues.put(COL_6, String.valueOf(image));
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }
    public boolean update(String item, int quantity, String price){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,item);
        contentValues.put(COL_2,quantity);
        contentValues.put(COL_3,price);
       // contentValues.put(COL_4,totalprice);

        db.update(TABLE_NAME,contentValues,"item = ?",new String[] {item});
        return  true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public int getQuantity(String item)
    {
        int quantity=0;
        String i;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if(res.moveToFirst())
        {
            do
            {
                i=res.getString(0);
                if(i.equals(item))
                {
                    quantity = res.getInt(1);
                    break;
                }
            }while (res.moveToNext());
        }
        return quantity;
    }

    public String getPrice(String item)
    {
        String price="";
        String i;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if(res.moveToFirst())
        {
            do
            {
                i=res.getString(0);
                if(i.equals(item))
                {
                    price = res.getString(2);
                    break;
                }
            }while (res.moveToNext());
        }
        return price;
    }
}