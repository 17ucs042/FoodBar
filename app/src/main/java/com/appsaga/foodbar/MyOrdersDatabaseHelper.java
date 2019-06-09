package com.appsaga.foodbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOrdersDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=" my_orders.db";
    public static final String TABLE_NAME=" MyOrdersTable";
    public static final String COL_1=" _id";
    public static final String COL_2=" name";
    public static final String COL_3=" quantity";
    public static final String COL_4=" price";
    public static final String COL_5=" itemNum";
    public static final String COL_6=" image";
    public static final String COL_7=" type";

    public  MyOrdersDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME+" (_id INTEGER  , name TEXT ,quantity TEXT,price TEXT,itemNum INTEGER, image BLOB,type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertData(Cursor res)
    {
        if(res.moveToFirst())
        {
            do
            {
                insert(res.getString(1),res.getString(2),res.getString(3),res.getInt(4),
                        res.getBlob(5),res.getString(6));

            }while (res.moveToNext());
        }
    }

    public  boolean insert(String name, String quantity, String price,int itemNum, byte[] image, String type)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,quantity);
        contentValues.put(COL_4,price);
        contentValues.put(COL_5,itemNum);
        contentValues.put(COL_6,image);
        contentValues.put(COL_7,type);

        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+ " WHERE  itemNum > ?",new String[]{"0"});
        return res;
    }

    public void deleteAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from "+TABLE_NAME);
    }
}
