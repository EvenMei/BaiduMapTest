package com.example.mymap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Info implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4044689667889016228L;
	private double latitude;
	private double Longitude;
	private int imgId;
	private String name;
	private String distance;
	private int zan;
	
	
	public static List<Info>infos=new ArrayList<Info>();
	
	static{
		infos.add(new Info(34.242652,108.971171,R.drawable.king,"拯救地球的凯","拯救地球家族",1456));
		infos.add(new Info(34.242952,108.972171,R.drawable.king,"拯救地球的凯","拯救地球家族",456));
		infos.add(new Info(34.242852,108.973171,R.drawable.king,"拯救地球的凯","拯救地球家族",1456));
		infos.add(new Info(34.242152,108.971971,R.drawable.king,"拯救地球的凯","拯救地球家族",1456));
	}
	
	
	public Info(double latitude, double longitude, int imgId, String name,
			String distance, int zan) {
		super();
		this.latitude = latitude;
		Longitude = longitude;
		this.imgId = imgId;
		this.name = name;
		this.distance = distance;
		this.zan = zan;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public int getImgId() {
		return imgId;
	}
	public void setImgId(int imgId) {
		this.imgId = imgId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}
	

}
