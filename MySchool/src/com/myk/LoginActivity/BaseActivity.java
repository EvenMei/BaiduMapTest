package com.myk.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		
	}
	
	
	
	
	
	@Override
	protected void onDestroy() {
		// TODO �Զ����ɵķ������
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
	
	
	
	
	

}
