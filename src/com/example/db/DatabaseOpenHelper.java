package com.example.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
    private static String name = "database";
    private static int version = 1;
    private Context context;
    public DatabaseOpenHelper(Context context) {
		// TODO Auto-generated constructor stub
    	super(context, name, null, version);
    	this.context = context;
    	
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//0id
		//1address
		//2date
		//3content
		//4sendorreceive
		//5isread
		String sql="create table msg(id integer primary key autoincrement,address text,date text,content text" +
				",sendorreceive integer,isread integer)";
		db.execSQL(sql);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
    
}
