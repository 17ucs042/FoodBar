package com.appsaga.foodbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Blob;
import java.util.ArrayList;


public class ItemDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME=" basket.db";
    public static final String TABLE_NAME="BasketTable";
    public static final String COL_1=" _id";
    public static final String COL_2=" name";
    public static final String COL_3=" quantity";
    public static final String COL_4=" price";
    public static final String COL_5=" itemNum";
    public static final String COL_6=" image";

    public  ItemDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME+" (_id INTEGER  , name TEXT ,quantity TEXT,price TEXT,itemNum INTEGER, image BLOB," +
                "PRIMARY KEY(name,quantity))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  boolean insertData(String name, String quantity, String price,int itemNum, byte[] image)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,quantity);
        contentValues.put(COL_4,price);
        contentValues.put(COL_5,itemNum);
        contentValues.put(COL_6,image);

        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }
    public boolean update(String name, String quantity, String price, int itemNum, byte[] image){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,quantity);
        contentValues.put(COL_4,price);
        contentValues.put(COL_5,itemNum);
        contentValues.put(COL_6,image);

        db.update(TABLE_NAME,contentValues,"name = ? and quantity = ?" ,new String[] {name,quantity});
        return  true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+ " WHERE  itemNum > ?",new String[]{"0"});
        return res;
    }

    public String getQuantity(String name)
    {
        String quantity=null;
        String i;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if(res.moveToFirst())
        {
            do
            {
                i=res.getString(0);
                if(i.equals(name))
                {
                    quantity = res.getString(1);
                    break;
                }
            }while (res.moveToNext());
        }
        return quantity;
    }

    public String getPrice(String name)
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
                if(i.equals(name))
                {
                    price = res.getString(2);
                    break;
                }
            }while (res.moveToNext());
        }
        return price;
    }

    public int getTotalItems()
    {
        ArrayList<String> items = new ArrayList<>();
        int totalItems=0;
        String i;
        String q;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+ " WHERE  itemNum > ?",new String[]{"0"});

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

    public void deleteData(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String itemId = String.valueOf(id);
        db.execSQL ("delete from "+TABLE_NAME+" WHERE _id = '"+itemId+"'");
    }

    public Boolean containsItem(String name,String quantity)
    {
        Boolean contains = Boolean.FALSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if(res.moveToFirst())
        {
            do {
                if(res.getString(1).equalsIgnoreCase(name) && res.getString(2).equalsIgnoreCase(quantity))
                {
                    contains = Boolean.TRUE;
                    break;
                }

            }while (res.moveToNext());
        }
        return contains;
    }

    public int getItemNum(String name,String quantity)
    {
        int itemNum;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);

        if(res.moveToFirst())
        {
            do {
                if(res.getString(1).equalsIgnoreCase(name) && res.getString(2).equalsIgnoreCase(quantity))
                {
                    itemNum=res.getInt(4);

                    return itemNum;
                }

            }while (res.moveToNext());
        }
        return 0;
    }

    public int getTotalPrice()
    {
        int totalPrice=0;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME+ " WHERE  itemNum > ?",new String[]{"0"});

        if(res.moveToFirst())
        {
            do {
                    String price = res.getString(3).replace("Rs.","").trim();
                    totalPrice = totalPrice + Integer.parseInt(price)*res.getInt(4);

            }while (res.moveToNext());
        }

        return totalPrice;
    }

    public void deleteAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from "+TABLE_NAME);
    }
}