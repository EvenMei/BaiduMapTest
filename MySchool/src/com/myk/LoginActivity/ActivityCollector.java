package com.myk.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
	private static List<Activity>activies=new ArrayList<Activity>();
	
	public  static void addActivity(Activity activity){
		if(!activies.contains(activity)){
			activies.add(activity);
		}
		
	}
	
	
	public static void removeActivity(Activity activity){
		if(activies.contains(activity)){
			activies.remove(activity);
		}
	}
	
	public static void finishAll(){
		for(Activity activity:activies){
			if(!activity.isFinishing()){
				activity.finish();
			}
			
		}
	}

}
