package com.example.myschool;

import java.util.ArrayList;
import java.util.List;

import com.example.myschool.MyHorizontalScrollView.onStateListener;
import com.example.myschool.View.Indicator;
import com.myk.Activies.MyCourse;
import com.myk.Fragment.FirstFragment;
import com.myk.Fragment.SecondFragment;
import com.myk.Fragment.ThirdFragment;
import com.myk.LoginActivity.ActivityCollector;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	
	
	private FirstFragment fg;
	private SecondFragment sg;
	private ThirdFragment tg;
	private ViewPager viewpager;
	private List<Fragment> list=new ArrayList<Fragment>();
	private int currentpage;
	private FragmentPagerAdapter adapter;
	//底部TextView
	private TextView firstText;
	private TextView secondText;
	private TextView thirdText;
	private TextView title;
	private TextView courseTV;
	
	private ImageView topBar;
	private Bitmap off;
	private Bitmap on;
	private boolean offorOn=true; //默认关闭
	//Indicator的调用
	private Indicator indicator;
	
	//MyHorizontalScrollView
	private MyHorizontalScrollView hsview;
	
	//
	private TextView settingTV;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
		firstText.setOnClickListener(this);
		secondText.setOnClickListener(this);
		thirdText.setOnClickListener(this);
		settingTV.setOnClickListener(this);
		courseTV.setOnClickListener(this);
		hsview.setOnStateChanged(new onStateListener() {
			
			@Override
			public void sendstate(boolean state) {
				offorOn=state;
				if(offorOn){
					topBar.setImageBitmap(on);
				}
				else{
					topBar.setImageBitmap(off);
				}
				// TODO 自动生成的方法存根
				
			}
		});
		
	}


	
	
	
	private void init() {
		
		hsview=(MyHorizontalScrollView) findViewById(R.id.my_scroll_view);
		firstText=(TextView) findViewById(R.id.Text_01);
		secondText=(TextView) findViewById(R.id.Text_02);
		thirdText=(TextView) findViewById(R.id.Text_03);
		topBar=(ImageView) findViewById(R.id.topButton);
		title=(TextView) findViewById(R.id.title);
		indicator=(Indicator) findViewById(R.id.my_indicator);
		topBar.setOnClickListener(this);
		off=BitmapFactory.decodeResource(getResources(),R.drawable.vertical_bar);
		on=BitmapFactory.decodeResource(getResources(), R.drawable.ic_top_bar_category);
		settingTV=(TextView) findViewById(R.id.tvMySettings);
		courseTV=(TextView) findViewById(R.id.my_course_textview);
		
		
		
		
		fg=new FirstFragment();
		sg=new SecondFragment();
		tg=new ThirdFragment();
		list.add(fg);
		list.add(sg);
		list.add(tg);
		viewpager=(ViewPager) findViewById(R.id.view_pager);
		
		adapter=new FragmentPagerAdapter(getSupportFragmentManager()){

			@Override
			public Fragment getItem(int position) {
				// TODO 自动生成的方法存根
				return list.get(position);
			}

			@Override
			public int getCount() {
				// TODO 自动生成的方法存根
				return list.size();
			}
			
		};
		viewpager.setAdapter(adapter);
		viewpager.setCurrentItem(0);
		
		viewpager.setOnPageChangeListener(new OnPageChangeListener(){

			@Override
			public void onPageScrollStateChanged(int position) {
			
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPix) {
				//indicator.Scroll(position);
				
			}

			@Override
			public void onPageSelected(int position) {
				currentpage=position;	
			}
		});
		// TODO 自动生成的方法存根
		
	}
	
	


	private void setCurrentPageto(int index){
		viewpager.setCurrentItem(index);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}



	@Override
	public void onClick(View v) {
	
		switch(v.getId()){
		case R.id.Text_01:
			setCurrentPageto(0);
			indicator.Scroll(0);
			title.setText("首页");
			indicator.restTextColor(0);
		
			break;
			
		case R.id.Text_02:
			setCurrentPageto(1);
			indicator.Scroll(1);
			title.setText("第二页");
			indicator.restTextColor(1);
			break;
		case R.id.Text_03:
			setCurrentPageto(2);
			indicator.Scroll(2);
			title.setText("第三页");
			indicator.restTextColor(2);
    	
			break;
			
		case R.id.topButton:
			if(offorOn){                    //toOpen
				topBar.setImageBitmap(on);
				hsview.Scrolltotarget(true);
				offorOn=!offorOn;
			}else{                             //toClose
				topBar.setImageBitmap(off);
				hsview.Scrolltotarget(false);	
				offorOn=!offorOn;
			}
			break;
			
		case R.id.tvMySettings:
			Intent intent=new Intent("offline");
			sendBroadcast(intent);
			break;
			
		case R.id.my_course_textview:
			Intent courseIntent=new Intent(MainActivity.this,MyCourse.class);
			startActivity(courseIntent);
			
			default:
				break;
		}
		
		
		
			
		// TODO 自动生成的方法存根
		
	}





}
