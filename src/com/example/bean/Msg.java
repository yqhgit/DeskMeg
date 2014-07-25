package com.example.bean;

import java.io.Serializable;

import android.content.Context;

public class Msg implements Serializable{
	public boolean isread = false;
	public String date;
    public String address;
    public String content;
    public int id;
    public String displayName = null;
//    public int year;
//    public int month;
//    public int day;
//    public String time;
    
	public boolean sendorreceive;
    public static String MSG_TABLENAME = "msg";
    public static String MSG_ADDRESS = "address";
    public static String MSG_DATE = "date";
    public static String MSG_CONTENT = "content";
    public static String MSG_SENDORRECEIVE = "sendorreceive";
    public static String MSG_ISREAD = "isread";
    
    public Msg(String date,String from,String content,boolean sendorreceive){
    	this.date = date;
    	this.address = from;
    	this.content = content;
    	this.sendorreceive = sendorreceive;
    }
    public Msg(String date,String from,String content,boolean sendorreceive,boolean isread){
    	this.date = date;
    	this.address = from;
    	this.content = content;
    	this.sendorreceive = sendorreceive;
    	this.isread = isread;
    }
    //print
    public String print(){
    	if(date!=null && address!=null && content!=null){
    		return String.valueOf(id)+date+address+content;
    	}
    	else
    		return "null";
    }
    
    public Msg() {
		// TODO Auto-generated constructor stub
	}

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isSendorreceive() {
		return sendorreceive;
	}
	public void setSendorreceive(boolean sendorreceive) {
		this.sendorreceive = sendorreceive;
	}
	
	
    public boolean isIsread() {
		return isread;
	}
	public void setIsread(boolean isread) {
		this.isread = isread;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String from) {
		this.address = from;
	}
	
	
	public String getCotent() {
		return content;
	}
	public void setCotent(String cotent) {
		this.content = cotent;
	}
    public void setDisplayName(String name){
    	this.displayName = name;
    }
	public String getDisplayName(){
		return displayName;
		
	}
}