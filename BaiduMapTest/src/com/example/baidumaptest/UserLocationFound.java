package com.example.baidumaptest;
import android.content.Context;  
import android.location.Criteria;  
import android.location.Location;  
import android.location.LocationListener;  
import android.location.LocationManager;  
import android.os.Bundle;  
import android.widget.Toast;  
  
public class UserLocationFound{  
      
    private int latitude = 0;  
    private int longitude = 0;  
    private Context context;  
      
    private LocationManager locationManager;  
    private String provider;  
    private Location location;  
      
    public UserLocationFound(Context context){  
        this.context= context;  
        setLatitudeAndLongitude();    
    }  
      
    public void setLatitude(int latitude) {  
        this.latitude = latitude;  
    }  
  
    public int getLatitude() {  
        return latitude;  
    }  
  
    public void setLongitude(int longitude) {  
        this.longitude = longitude;  
    }  
  
    public int getLongitude() {  
        return longitude;  
    }  
  
    public void setLatitudeAndLongitude() {  
        // TODO Auto-generated method stub  
        // 获取 LocationManager 服务  
        locationManager = (LocationManager) (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  
//      locationManager.setTestProviderEnabled("gps", true);  
        // 获取 Location Provider  
        getProvider();  
        // 如果未设置位置源，打开 GPS 设置界面  
        openGPS();  
        // 获取位置  
        location = locationManager.getLastKnownLocation(provider);  
        // 显示位置信息到文字标签  
        updateWithNewLocation(location);  
        // 注册监听器 locationListener ，第 2 、 3 个参数可以控制接收 gps 消息的频度以节省电力。第 2 个参数为毫秒，  
        // 表示调用 listener 的周期，第 3 个参数为米 , 表示位置移动指定距离后就调用 listener  
          
    }  
      
    // Gps 消息监听器  
    private final LocationListener locationListener = new LocationListener() {  
          
        // 位置发生改变后调用  
        public void onLocationChanged(Location location) {  
            updateWithNewLocation(location);  
        }  
        // provider 被用户关闭后调用  
        public void onProviderDisabled(String provider) {  
            updateWithNewLocation(null);  
        }  
  
        // provider 被用户开启后调用  
        public void onProviderEnabled(String provider) {          
              
        }  
  
        // provider 状态变化时调用  
        public void onStatusChanged(String provider, int status,Bundle extras) {  
        }  
    };  
  
    private void updateWithNewLocation(Location location2) {  
        // TODO Auto-generated method stub  
        while(location == null){  
            locationManager.requestLocationUpdates(provider, 2000, (float) 0.1, locationListener);  
        }  
        if (location != null) {  
            latitude = ((int)(location.getLatitude()*100000));  
            longitude = (int)(location.getLongitude()*100000);  
//          changeFormat(latitude,longitude);  
        } else {  
            latitude = 3995076;  
            longitude = 11619675;  
        }  
    }  
  
    private void openGPS() {  
        // TODO Auto-generated method stub  
          
        if (locationManager  
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)  
                || locationManager  
                        .isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)  
        ){  
            Toast.makeText(context, " 位置源已设置！ ", Toast.LENGTH_SHORT).show();  
            return;  
        }   
        Toast.makeText(context, " 位置源未设置！", Toast.LENGTH_SHORT).show();  
    }  
  
    private void getProvider() {  
        // TODO Auto-generated method stub  
        // 构建位置查询条件  
        Criteria criteria = new Criteria();  
        // 查询精度：高  
        criteria.setAccuracy(Criteria.ACCURACY_FINE);  
        // 是否查询海拨：否  
        criteria.setAltitudeRequired(false);  
        // 是否查询方位角 : 否  
        criteria.setBearingRequired(false);  
        // 是否允许付费：是  
        criteria.setCostAllowed(true);  
        // 电量要求：低  
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
        // 返回最合适的符合条件的 provider ，第 2 个参数为 true 说明 , 如果只有一个 provider 是有效的 , 则返回当前  
        // provider  
        provider = locationManager.getBestProvider(criteria, true);  
    }  
}  
