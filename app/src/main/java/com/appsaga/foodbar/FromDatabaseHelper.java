package com.appsaga.foodbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class FromDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=" from.db";
    public static final String TABLE_NAME="FromTable";
    public static final String COL_1=" _id";
    public static final String COL_2=" fromis";
    public static final String COL_3=" user_id";
    public static final String COL_4=" name";

    public FromDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME+" (_id INTEGER, fromis TEXT PRIMARY KEY, user_id TEXT , name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  boolean insertData(String fromis,String user_id,String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,fromis);
        contentValues.put(COL_3,user_id);
        contentValues.put(COL_4,name);

        db.execSQL ("delete from "+TABLE_NAME);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }

    public int getTotalItems()
    {
        ArrayList<String> fromis = new ArrayList<>();
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
                if(!fromis.contains(i))
                {
                    fromis.add(i);
                }
            }while (res.moveToNext());
        }
        return fromis.size();
    }

    public String getFrom()
    {
        String fromis=null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        res.moveToFirst();
        fromis = res.getString(1);

        return fromis;
    }

    public String getUserID()
    {
        String user_id=null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        res.moveToFirst();
        user_id = res.getString(2);

        return user_id;
    }

    public String getName()
    {
        String name=null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        res.moveToFirst();
        name = res.getString(3);

        return name;
    }

    public void deleteAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from "+TABLE_NAME);
    }
}
