package com.example.activity;

import java.util.List;

import com.example.bean.AllContact;
import com.example.bean.Msg;
import com.example.db.dao.MsgDao;
import com.example.deskmsg.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TalkToActivity extends Activity{
    private Context context;
    private LinearLayout contentlayout;
    private ImageView backimg;
    private List<Msg> list;
    private int listindex ;
    private AllContact allcontact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        context = this;
        listindex = 0;
        setContentView(R.layout.talktolayout);
        backimg = (ImageView) findViewById(R.id.back);
        backimg.setVisibility(View.VISIBLE);
        contentlayout = (LinearLayout) findViewById(R.id.talktocontent);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        allcontact = (AllContact) bundle.getSerializable("allcontact");
        Log.e("threadid","" + allcontact.getThread_id()+"");
        list = new MsgDao(context).getMsgByThread_id(allcontact.getThread_id());
        Log.e("listlength","" + list.size());
        for (Msg msg : list) {
            if(msg.sendorreceive)
                addmyselfcontent(msg.getContent());
            else
                addoppsitecontent(msg.getContent());
        }
        backimg.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.e("imgin","click");
//                Intent intent = new Intent();
//                intent.setClass(TalkToActivity.this, MainActivity.class);
                TalkToActivity.this.finish();
            }
        });
    }
    void addmyselfcontent(String msgtext){
        View view = LayoutInflater.from(context).inflate(R.layout.myselfcontent, null);
        TextView text = (TextView) view.findViewById(R.id.myselftext);
        ImageView img = (ImageView) view.findViewById(R.id.myselfphoto);
        img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        text.setText(msgtext);
        //img.setBackgroundDrawable(msg.ge);
        
        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                
                return false;
            }
        });
        contentlayout.addView(view);
    }
    void addoppsitecontent(String msgtext){
        View view = LayoutInflater.from(context).inflate(R.layout.oppsitecontent, null);
        TextView text = (TextView) view.findViewById(R.id.oppsitetext);
        ImageView img = (ImageView) view.findViewById(R.id.oppsitephoto);
        if(allcontact.getPhotobitmap() == null)
            img.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher));
        else
            img.setBackgroundDrawable(new BitmapDrawable(allcontact.getPhotobitmap()));
        text.setText(msgtext);
        //img.setBackgroundDrawable(msg.ge);
        view.setOnLongClickListener(new OnLongClickListener() {
            
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                
                return false;
            }
        });
        contentlayout.addView(view);
    }
}
