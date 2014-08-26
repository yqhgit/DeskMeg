package com.example.activity;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends Activity{

    @Override
    public void startActivity(Intent intent) {
        // TODO Auto-generated method stub
        super.startActivity(intent);
        overridePendingTransition(com.example.deskmsg.R.anim.new_in, com.example.deskmsg.R.anim.new_out);
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        overridePendingTransition( com.example.deskmsg.R.anim.back_in,com.example.deskmsg.R.anim.back_out);
        
    }
    public void setWindowBg(){
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        window.setBackgroundDrawableResource(com.example.deskmsg.R.drawable.ic_launcher);
    }
}
