package com.example.bean;

import java.io.Serializable;

import com.example.deskmsg.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * threads±í½á¹¹
 */
public class AllContact implements Serializable{
    private int thread_id;
    private String lastmsg;
    private String lastdate;
    private String displayName;
    private String num;
    private Bitmap photobitmap;
    private byte[] photobyte;
    
    
    public AllContact(int id,String msg,String date,String name,byte[] photo){
        this.thread_id = id;
        this.lastmsg = msg;
        this.lastdate = date;
        this.displayName = name;
        this.photobyte = photo;
    }
    
    
    public Bitmap getPhotobitmap() {
        if(photobitmap!=null)
            return photobitmap;
        if(photobyte!=null)
          return BitmapFactory.decodeByteArray(photobyte, 0, photobyte.length);
        return null;
    }

    public void setPhotobitmap(Bitmap photobitmap) {
        this.photobitmap = photobitmap;
    }

    public byte[] getPhotobyte() {
        return photobyte;
    }

    public void setPhotobyte(byte[] photobyte) {
        this.photobyte = photobyte;
    }

    
    
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public int getThread_id() {
        return thread_id;
    }
    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }
    public String getLastmsg() {
        return lastmsg;
    }
    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }
    public String getLastdate() {
        return lastdate;
    }
    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
    }

    public void print() {
        // TODO Auto-generated method stub
        System.out.println(getThread_id()+" "+getLastmsg()+" "+getLastdate()+" "+getNum());
    }
}
