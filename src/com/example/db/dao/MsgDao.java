package com.example.db.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.Phones;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.webkit.WebChromeClient.CustomViewCallback;

import com.example.bean.Msg;
import com.example.db.DatabaseOpenHelper;

public class MsgDao {
    private Context context;
    public String SMS_CONTENT = "content://sms";
    public MsgDao(Context context) {
		// TODO Auto-generated constructor stub
    	this.context = context;
	}
    //²éÑ¯Î´¶Á¶ÌÐÅ
    public List<Msg> searchnewmsg(){
    	List<Msg> list = new ArrayList<Msg>();
    	
    	Cursor cursor = context.getContentResolver().query(Uri.parse(SMS_CONTENT), new String[]{"_id","address","date","read","body"}, 
                "read = ?", new String[]{"0"}, "date desc");
    	int idindex = cursor.getColumnIndex("_id");
    	int addressindex = cursor.getColumnIndex("address");
    	int dateindex = cursor.getColumnIndex("date");
    	int readindex = cursor.getColumnIndex("read");
    	int bodyindex = cursor.getColumnIndex("body");
    	while(cursor.moveToNext()){
    		Msg msg = new Msg();
    		msg.setId(cursor.getInt(idindex));
    		String phone = cursor.getString(addressindex);
    		if(phone.length() > 11){
    			phone = phone.substring(phone.length()-11, phone.length());
    		}
    		msg.setAddress(phone);
    		msg.setIsread(Boolean.parseBoolean(cursor.getString(readindex)));
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    		msg.setDate(formatter.format(new Date(Long.parseLong(cursor.getString(dateindex)))));
    		msg.setContent(cursor.getString(bodyindex));
    		msg.setDisplayName(getDisplayNameByphone(phone));
    		list.add(msg);
    	}
    	return list;
    }
	public void delete(int id) {
		// TODO Auto-generated method stub
		context.getContentResolver().delete(Uri.parse(SMS_CONTENT), "_id = ?", new String[]{String.valueOf(id)});
	}
	public void upgradeisread(int id) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("read", "1");
		context.getContentResolver().update(Uri.parse(SMS_CONTENT), values, "_id = ?", new String[]{String.valueOf(id)});
	}
	
	public String getDisplayNameByphone(String phone){
		int contact_id = -1;
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"raw_contact_id","data1"}, 
				ContactsContract.Data.MIMETYPE+" = ?", new String[]{"5"}, null);
		
		while(cursor.moveToNext()){
			if(PhoneNumberUtils.compare(phone, cursor.getString(cursor.getColumnIndex("data1")))){
				contact_id = cursor.getInt(cursor.getColumnIndex("raw_contact_id"));
			}
		}
		if(contact_id == -1)
			return null;
		Cursor cursor2 = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"data1"}, 
				   "raw_contact_id = ? and mimetype_id = ?", new String[]{String.valueOf(contact_id),"7"},null);
		if(cursor.moveToFirst()){
			return cursor2.getString(cursor2.getColumnIndex("data1"));
		}else
			return null;
	}
}
