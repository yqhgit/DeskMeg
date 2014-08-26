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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.Phones;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.webkit.WebChromeClient.CustomViewCallback;

import com.example.bean.AllContact;
import com.example.bean.Msg;
import com.example.db.DatabaseOpenHelper;
import com.example.deskmsg.R;

public class MsgDao {
    private Context context;
    public String SMS_CONTENT = "content://sms";
    public MsgDao(Context context) {
		// TODO Auto-generated constructor stub
    	this.context = context;
	}
    /**
     * ��ѯ����δ������,�����浯������ʾ
     * @return
     */
    public List<Msg> searchnewmsg(){
    	List<Msg> list = new ArrayList<Msg>();
    	Cursor cursor = null;
    	try {
    	    cursor = context.getContentResolver().query(Uri.parse(SMS_CONTENT), new String[]{"_id","address","date","read","body"}, 
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
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor != null)
                cursor.close();
        }
    	
    	return list;
    }
    /**
     *ɾ��ָ��id�Ķ���
     */
	public void delete(int id) {
		// TODO Auto-generated method stub
		context.getContentResolver().delete(Uri.parse(SMS_CONTENT), "_id = ?", new String[]{String.valueOf(id)});
	}
	/**
	 * ��δ����Ϣ��Ϊ�Ѷ�״̬
	 * @param id
	 */
	public void upgradeisread(int id) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("read", "1");
		context.getContentResolver().update(Uri.parse(SMS_CONTENT), values, "_id = ?", new String[]{String.valueOf(id)});
	}
	/**
	 * ���Ϊ���򷵻�phone
	 * ͨ�������ȡ��ϵ������
	 */
	public String getDisplayNameByphone(String phone){
	    Cursor cursor = null,cursor2 = null;
	    try {
	        int contact_id = -1;
	        cursor = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"raw_contact_id","data1"}, 
	                ContactsContract.Data.MIMETYPE+" = ?", new String[]{"5"}, null);
	        
	        while(cursor.moveToNext()){
	            if(PhoneNumberUtils.compare(phone, cursor.getString(cursor.getColumnIndex("data1")))){
	                contact_id = cursor.getInt(cursor.getColumnIndex("raw_contact_id"));
	            }
	        }
	        if(contact_id == -1){
	            return phone;
	            }
	        cursor2 = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"data1"}, 
	                   "raw_contact_id = ? and mimetype_id = ?", new String[]{String.valueOf(contact_id),"7"},null);
	        if(cursor.moveToFirst()){
	            String s = cursor2.getString(cursor2.getColumnIndex("data1"));
	            return s;
	        }else{
	            return phone;
	            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor != null){
                cursor.close();
            }
            if(cursor2 != null){
                cursor2.close();
            }
        }
		return "";
		
	}
	/**
	 *��ȡ������ϵ����Ϣ���б�
	 */
	public List<AllContact> getContactMsg(){
	    List<AllContact> list = new ArrayList<AllContact>();
	    //{"_id","date","snippet","recipient_ids"}
	    Cursor cursor = null;
	    try {
	        cursor = context.getContentResolver().query(Uri.parse(SMS_CONTENT), new String[]
	                {"* from threads--"}, null, null, "date desc");
	        while(cursor.moveToNext()){
	            String date = cursor.getString(cursor.getColumnIndex("date"));
	            String lastmsg = cursor.getString(cursor.getColumnIndex("snippet"));
	            int id = cursor.getInt(cursor.getColumnIndex("_id"));
	            
	            int recipient_ids = cursor.getInt(cursor.getColumnIndex("recipient_ids"));
	            String num = getNumByRecipient_id(recipient_ids);
	            Log.e("num", num+"");
	            String displayName = getDisplayNameByphone(num);
	            AllContact allContact = new AllContact(id, lastmsg, date, displayName,getPhotoByNum(num));
	            allContact.print();
	            list.add(allContact);
	        }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor!=null){
                cursor.close();
            }
        }
	    return list;
	}
	/**
	 * ��ȡ�绰����
	 * @param id
	 * @return
	 */
    private String getNumByRecipient_id(int id) {
        // TODO Auto-generated method stub  canonical_addresses
//        Cursor cursor = context.getContentResolver().query(Uri.parse(SMS_CONTENT), new String[]
//                {"address"}, "_id = ?", new String[]{String.valueOf(id)}, null);
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(Uri.parse("content://mms-sms/canonical-addresses"), new String[]
                    {"addresses"}, "_id = ?", new String[]{String.valueOf(id)}, null);
            if(cursor.moveToNext())
                return cursor.getString(cursor.getColumnIndex("address"));;
                
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor!=null)
                cursor.close();
        }
        
        return null;
    }
    /**
     * @param num
     * @return
     */
    public byte[] getPhotoByNum(String num){
        Cursor cursor = null, cursor2 = null;
        try {
            
            int contact_id = -1;
            cursor = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"raw_contact_id","data1"}, 
                    ContactsContract.Data.MIMETYPE+" = ?", new String[]{"5"}, null);
            
            while(cursor.moveToNext()){
                if(PhoneNumberUtils.compare(num, cursor.getString(cursor.getColumnIndex("data1")))){
                    contact_id = cursor.getInt(cursor.getColumnIndex("raw_contact_id"));
                }
            }
            if(contact_id == -1){
                return null;
            }
            byte[] photo = null;
            cursor2 = context.getContentResolver().query(Phone.CONTENT_URI, new String[]{"data15"}, 
                       "raw_contact_id = ? and mimetype_id = ?", new String[]{String.valueOf(contact_id),"10"},null);
            if(cursor.moveToFirst())
                photo = cursor2.getBlob(cursor2.getColumnIndex("data15"));
            return photo;
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor!=null)
                cursor.close();
            if(cursor2!=null){
                cursor2.close();
            }
        }
        return null;
        
    }
    public List<Msg> getMsgByThread_id(int thread_id){
        List<Msg> list = new ArrayList<Msg>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(Uri.parse(SMS_CONTENT), new String[]{"_id","date","body","type"}, 
                    "thread_id = ?", new String[]{String.valueOf(thread_id)}, "date desc");
            int idindex = cursor.getColumnIndex("_id");
            int typeindex = cursor.getColumnIndex("type");
            int dateindex = cursor.getColumnIndex("date");
            int bodyindex = cursor.getColumnIndex("body");
            while(cursor.moveToNext()){
                Msg msg = new Msg();
                msg.setId(cursor.getInt(idindex));
                int type = cursor.getInt(typeindex);
                //typeΪ1����  2����
                if(type == 1){
                    msg.setSendorreceive(false);
                }else
                    msg.setSendorreceive(true);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                msg.setDate(formatter.format(new Date(Long.parseLong(cursor.getString(dateindex)))));
                msg.setContent(cursor.getString(bodyindex));
                list.add(msg);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally{
            if(cursor!=null)
                cursor.close();
        }
        
        return list;
    }
}
