package com.myk.LoginActivity;

import com.example.myschool.MainActivity;
import com.example.myschool.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private boolean misChecked;
	private String idinfo;
	private String passinfo;
	private String yzminfo;
	
	//登录相关控件
	private EditText idEdit;
	private EditText passEdit;
	private EditText yzmEdit;
	private ImageView yzmImg;
	private ImageView refresh;
	private CheckBox box;
	private Button loginButton;
	private Button exitButton;
	
	//SharedPreferences相关
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	//常量
	private boolean isfromFOL;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		initViews();
		refresh.setOnClickListener(this);
		loginButton.setOnClickListener(this);
		exitButton.setOnClickListener(this);
		checkStatus();
		
		
	}

	
	
	
	
	private void checkStatus() {
		if(pref.getBoolean("isChecked", false)){
			isfromFOL=pref.getBoolean("is_From", false);
			idinfo=pref.getString("id_info", "");
			passinfo=pref.getString("pass_info", "");
			idEdit.setText(idinfo);
			passEdit.setText(passinfo);
			box.setChecked(true);
			if(!isfromFOL){
				SingIn();
			}
			
		
		}
	
		
	}





	private void SingIn() {
		
		 idinfo=idEdit.getText().toString();
		 passinfo=passEdit.getText().toString();
		 yzminfo=yzmEdit.getText().toString();
		
		if(box.isChecked()){
			editor.clear();
			editor.putString("id_info", idinfo);
			editor.putString("pass_info", passinfo);
			editor.putBoolean("isChecked", true);
		}
		else{
			editor.clear();
		}
		editor.commit();
		
		 if(idinfo.equals("15260221") && passinfo.equals("meiyukai")){
			
			Toast.makeText(LoginActivity.this, "登录成功 !", Toast.LENGTH_SHORT).show();
			
			editor.commit();
		    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		
		else if(TextUtils.isEmpty(idinfo) ||  TextUtils.isEmpty(passinfo)){
			Toast.makeText(LoginActivity.this, "登录名或密码不能为空 !", Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(LoginActivity.this, "学号或密码错误 !", Toast.LENGTH_SHORT).show();
		}
				
	}





	private void initViews() {
		
		idEdit=(EditText) findViewById(R.id.student_id_editText);
		passEdit=(EditText) findViewById(R.id.password_edit_text);
		yzmEdit=(EditText) findViewById(R.id.yzm_edit_text);
		yzmImg=(ImageView) findViewById(R.id.longin_imgview);
		refresh=(ImageView) findViewById(R.id.longin_refresh);
		box=(CheckBox) findViewById(R.id.check_box);
		loginButton=(Button) findViewById(R.id.sin_in_button);
		exitButton=(Button) findViewById(R.id.exit_button);
		pref=PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
		editor=pref.edit();
		
		
		
	}





	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.longin_refresh:
		
			break;
			
		case R.id.sin_in_button:
			SingIn();
			break;
			
		case R.id.exit_button:
			finish();
			break;
			
			default:
				
				break;
				
		}
		// TODO 自动生成的方法存根
		
	}
	

}
