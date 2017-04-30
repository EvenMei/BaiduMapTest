package com.myk.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		
	}
	
	
	
	
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
	
	
	
	

}
