package com.myk.Receiver;

import com.myk.LoginActivity.ActivityCollector;
import com.myk.LoginActivity.LoginActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.WindowManager;

public class ForceOffLineReceiver extends BroadcastReceiver {
	private SharedPreferences.Editor editor;
	 

	@Override
	public void onReceive(final Context context, Intent intent) {
		editor=(Editor) PreferenceManager.getDefaultSharedPreferences(context).edit();
		AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
		alertDialog.setTitle("退出掌上校园");
		alertDialog.setMessage("确定要退出？");
		alertDialog.setCancelable(false);
		alertDialog.setNegativeButton("取消",null);
		
		
		alertDialog.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				editor.putBoolean("is_From", true);
				editor.commit();
				ActivityCollector.finishAll();			
				
				Intent intent=new Intent(context,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(intent);
				// TODO 自动生成的方法存根
				
			}
		});
		
		
		
		AlertDialog dialog=alertDialog.create();
		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.show();
		
		
		

	}

}
