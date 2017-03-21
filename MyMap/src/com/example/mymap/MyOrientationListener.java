package com.example.mymap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener {
	private SensorManager manager;
	private Context mcontext;
	private Sensor msensor;
	private float lastX;

	public MyOrientationListener(Context context) {
		this.mcontext=context;
		
		// TODO 自动生成的构造函数存根
	}
	
	public void start(){
		manager=(SensorManager) mcontext.getSystemService(Context.SENSOR_SERVICE);
		if(manager!=null){
			msensor=manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);	
		}
		
		if(msensor!=null){
			manager.registerListener(this, msensor,SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		
		
	}
	public void stop(){
		manager.unregisterListener(this);
		
		
	}
	
	
	
	
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = 0;
		if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
			 x =event.values[SensorManager.DATA_X];
		}
		if(Math.abs(x-lastX)>1.0){
			if(mOnOrientationListener!=null){
				mOnOrientationListener.onOrientationChange(x);
			}
			
		}
		lastX=x;
		
		// TODO 自动生成的方法存根

	}
	
	
	
	
	
	private OnOrientationListener mOnOrientationListener;
	



	public void setOnOrientationListener(
			OnOrientationListener mOnOrientationListener) {
		this.mOnOrientationListener = mOnOrientationListener;
	}


	public interface OnOrientationListener{
		void onOrientationChange(float x);
	}
	

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO 自动生成的方法存根

	}

}
