package com.example.activity;

import java.util.List;

import com.example.adapter.MsgAdapter;
import com.example.bean.AllContact;
import com.example.db.dao.MsgDao;
import com.example.deskmsg.R;
import com.example.deskmsg.R.color;

import android.R.menu;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity {
    private ListView listview;
    private List<AllContact> list;
    private Context context;
    private Dialog menu_dialog;
    private View v;
    private TextView detail;
    private TextView batch_manage;
    private TextView putTop;
    private TextView delete;
    private TextView delete_all;
    private TextView desk_sure;
    LinearLayout bottomlayout;
    RelativeLayout actionbar;
    private ImageView cancle;
    private int index = -1;
    static public int flag = 0;
    private boolean selected[]; 
    private MsgAdapter adapter;
    private MsgDao msgdao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		context = this;
		initView();
		msgdao = new MsgDao(context);
		list = msgdao.getContactMsg();
		if(list.size()>0){
		    adapter = new MsgAdapter(list,context);
		    listview.setAdapter(adapter);
		    }
		selected = new boolean[list.size()];   //默认值为false
		initListener();
		startservice();
	}
	@SuppressLint("NewApi")
    private void initView() {
        // TODO Auto-generated method stub
	    listview = (ListView) findViewById(R.id.listmsg);
	    actionbar = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.header, null).findViewById(R.id.header);
	    bottomlayout = (LinearLayout) findViewById(R.id.bottomlayout);
	    delete_all = (TextView) findViewById(R.id.delete_sure);
	    desk_sure = (TextView) findViewById(R.id.desk_sure);
	    cancle = (ImageView) findViewById(R.id.cancel);
	    menu_dialog = new Dialog(context,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
	    v = LayoutInflater.from(context).inflate(R.layout.listlongclick_menu, null);
	    detail = (TextView) v.findViewById(R.id.detial);
	    delete = (TextView) v.findViewById(R.id.delete);
	    putTop = (TextView) v.findViewById(R.id.putTop);
	    batch_manage = (TextView) v.findViewById(R.id.batchManage);
	    menu_dialog.setContentView(v);
    }
    @SuppressLint("NewApi")
    void initListener(){
	    listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                //常规模式
                if(flag == 0){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("allcontact", list.get(position));
                    intent.putExtra("bundle", bundle);
                    intent.setClass(context, TalkToActivity.class);
                    context.startActivity(intent);
                }
                //批处理模式
                if(flag == 1){
                    adapter.changeState(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });
	    listview.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                index = position;
                menu_dialog.show();
                return false;
            }
        });
	    delete_all.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                deleteSelctedArray();
            }
        });
	    delete.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                delete(index);
                adapter.notifyDataSetChanged();
            }
        });
        batch_manage.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        changeview();
                    }
                });
        putTop.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        detail.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        cancle.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                changeview();
            }
        });
	}
    /**
     * 删除指定thread的短信组
     * @param position thread_id
     */
    protected void delete(int position) {
        // TODO Auto-generated method stub
        msgdao.deleteByThread(list.get(position).getThread_id());
        list.remove(position);
    }
    /**
     * 删除所有选中的短信组
     */
    protected void deleteSelctedArray() {
        // TODO Auto-generated method stub
        SparseBooleanArray array = adapter.getBooleanArray();
        if(array.size() == 0)
            return;
        for (int i = 0; i < array.size(); i++) {
            if(array.valueAt(i))
                delete(array.keyAt(i));
        }
        changeview();
    }
    
    /**
     * 进入或退出批处理时界面的改变
     */
    protected void changeview() {
        // TODO Auto-generated method stub
        if(flag == 0){
            menu_dialog.dismiss();
            bottomlayout.setVisibility(View.VISIBLE);
            cancle.setVisibility(View.VISIBLE);
            flag = 1;
            listview.setLongClickable(false);
            //listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }else{
            bottomlayout.setVisibility(View.GONE);
            cancle.setVisibility(View.GONE);
            adapter.removeAll();
            adapter.notifyDataSetChanged();
            listview.post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					flag = 0;
					adapter.notifyDataSetChanged();
				}
			});
            listview.setLongClickable(true);
        }
    }
    
    /**
     * 启动自动获取短信服务
     */
    void startservice(){
    	Intent intent = new Intent();
		intent.setClass(this, ReceiveMsg.class);
		startService(intent);
    }
}
