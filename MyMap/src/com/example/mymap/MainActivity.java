package com.example.mymap;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;
import com.example.mymap.MyOrientationListener.OnOrientationListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.OrientationListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity<MylocationConfiguration> extends Activity implements OnClickListener{
	private MapView mapView;
	private BaiduMap mBaidumap;
	private Button wherebutton;
	private Button mockButton;
	private Button realButton;

	//定位相关
	
	private LocationClient mLocationClient;
	private MLocationListener mLocationListener;
	private boolean isFirstIn;
	private Context mcontext;
	private double mLatitude;
	private double mLongitude;
	private LatLng mDestLocationData;
	
	//自定义图标
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener mOrientationListener;
	private float mCurrentX;
	
	private LocationMode mLocationMode;
	
	
	//覆盖物相关
	private BitmapDescriptor mMarker;
	private RelativeLayout mMarkerLayout;
	
	//导航相关
	 private static final String APP_FOLDER_NAME = "BNSDKDemo-mBaidumap";
	 private String mSDCardPath = null;
	 private String authinfo = null;
	 private boolean hasInitSuccess = false;
	 public static List<Activity> activityList = new LinkedList<Activity>();
	 public static final String ROUTE_PLAN_NODE = "routePlanNode";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 SDKInitializer.initialize(getApplicationContext());  
	       setContentView(R.layout.activity_main);  
	       mockButton=(Button) findViewById(R.id.mock_button);
	       realButton=(Button) findViewById(R.id.real_button);
	      
	       wherebutton=(Button) findViewById(R.id.where_button);
	       
	       wherebutton.setOnClickListener(this);
	       mockButton.setOnClickListener(this);
	       realButton.setOnClickListener(this);
	    
	       
	       
		this.mcontext=this;
		initView();
		initLocation();
		initMarker();
		mBaidumap.setOnMarkerClickListener(new OnMarkerClickListener(){

			@Override
			public boolean onMarkerClick(Marker marker) {
			Bundle	extrainfo=marker.getExtraInfo();
			Info info=(Info) extrainfo.getSerializable("info");
			ImageView imgview=(ImageView) findViewById(R.id.info_img_view);
			TextView distance=(TextView) findViewById(R.id.text2);
			TextView name=(TextView) findViewById(R.id.text1);
			TextView quntity=(TextView) findViewById(R.id.zanshu_text);
			
			imgview.setImageResource(info.getImgId());
			distance.setText(info.getDistance());
			name.setText(info.getName());
			quntity.setText(String.valueOf(info.getZan()));
			InfoWindow infowindow;
			TextView tv=new TextView(mcontext);
			tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.communication));
			tv.setPadding(30, 20,30,50);
			
			tv.setText(info.getName());
			final LatLng latlng=marker.getPosition();
			Point p=mBaidumap.getProjection().toScreenLocation(latlng); //转换为屏幕上的点
			p.y-=47;   //设置偏移量 47
			LatLng ll=mBaidumap.getProjection().fromScreenLocation(p); //从屏幕上的点转换为经纬度
			infowindow=new InfoWindow(BitmapDescriptorFactory.fromView(tv), ll, 0, new OnInfoWindowClickListener(){

				@Override
				public void onInfoWindowClick() {
					mBaidumap.hideInfoWindow();
					// TODO 自动生成的方法存根
					
				}
				
			});
			
			
			mBaidumap.showInfoWindow(infowindow);
			mMarkerLayout.setVisibility(View.VISIBLE);
			return true;
			
			
				
				// TODO 自动生成的方法存根
				
			}
			
		});
		
		mBaidumap.setOnMapClickListener(new OnMapClickListener(){

			@Override
			public void onMapClick(LatLng arg0) {
				mMarkerLayout.setVisibility(View.GONE);
//				mBaidumap.hideInfoWindow();
				// TODO 自动生成的方法存根
				
			}

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				// TODO 自动生成的方法存根
				return false;
			}
			
		});
		
		//初始化导航相关
		
		if (initDirs()) {
            initNavi();
        }
	}
	 private boolean initDirs() {
	        mSDCardPath = getSdcardDir();
	        if (mSDCardPath == null) {
	            return false;
	        }
	        File f = new File(mSDCardPath, APP_FOLDER_NAME);
	        if (!f.exists()) {
	            try {
	                f.mkdir();
	            } catch (Exception e) {
	                e.printStackTrace();
	                return false;
	            }
	        }
	        return true;
	    }

	
	private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

     

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }
            

            public void initSuccess() {
                Toast.makeText(MainActivity.this,"百度导航引擎初始化成功!", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            public void initStart() {
                Toast.makeText(MainActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(MainActivity.this, "百度导航引擎初始化失败!", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, ttsPlayStateListener);

    }
	
	 private void initSetting() {
	        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
	        BNaviSettingManager
	                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
	        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
	        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
	        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
	    }
	private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            // showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            // showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };
	private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    // showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    // showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };
	
	
	
	private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }
	
	
	
	 private CoordinateType mCoordinateType = null;
	 
	private void routeplanToNavi(boolean mock) {
		
		CoordinateType coType=CoordinateType.GCJ02;
		 BNRoutePlanNode sNode = null;
	     BNRoutePlanNode eNode = null;
	     
        mCoordinateType = coType;
        if (!hasInitSuccess) {
            Toast.makeText(MainActivity.this, "初始化失败.....", Toast.LENGTH_SHORT).show();
        }
       
      
        BDLocation BDloc=new BDLocation();
        BDloc.setLongitude(mLongitude);
        BDloc.setLatitude(mLatitude);
        BDLocation ll=bd2gcj(BDloc);
        
        BDLocation DesBDll=new BDLocation();
        DesBDll.setLongitude(mDestLocationData.longitude);
        DesBDll.setLatitude(mDestLocationData.latitude);
        BDLocation dest=bd2gcj(DesBDll);
        
      //mLastLocationData
       //mDestLocationData
            sNode=new BNRoutePlanNode(ll.getLongitude(),ll.getLatitude(), "我的地点", null,coType);
            eNode=new BNRoutePlanNode(dest.getLongitude(),dest.getLatitude(), "目的地", null,coType);   
               
            
//      sNode = new BNRoutePlanNode(116.30142, 40.05087, "百度大厦", null, coType);
//      eNode = new BNRoutePlanNode(116.39750, 39.90882, "北京天安门", null, coType);
                            
              
                
                
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, mock, new DemoRoutePlanListener(sNode));
            
        }
    }
	
	/*
	 * BD09LL转GCJ
	 */
	
	 private BDLocation bd2gcj(BDLocation loc){
	    	
  	   return LocationClient.getBDLocationInCoorType(loc, BDLocation.BDLOCATION_BD09LL_TO_GCJ02);
     }
	 
	 
	 
	 
	 
	
	
	public class DemoRoutePlanListener implements RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 璁剧疆閫斿緞鐐逛互鍙妑esetEndNode浼氬洖璋冭鎺ュ彛
             */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(MainActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(MainActivity.this, "位置坐标错误", Toast.LENGTH_SHORT).show();
        }
    }

	
	
	
	
	private void initMarker() {
		mMarker=new BitmapDescriptorFactory().fromResource(R.drawable.weizhi);
		mMarkerLayout=(RelativeLayout) findViewById(R.id.marker_layout);
		
		// TODO 自动生成的方法存根
		
	}





	private void initLocation() {
		mLocationMode=LocationMode.NORMAL;
		mLocationClient =new LocationClient(this);
		mLocationListener=new MLocationListener();
		mLocationClient.registerLocationListener(mLocationListener);
		
		LocationClientOption option=new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setScanSpan(1000);
		mLocationClient.setLocOption(option);
		
		mIconLocation=BitmapDescriptorFactory.fromResource(R.drawable.finger);
		mOrientationListener=new MyOrientationListener(mcontext);
		mOrientationListener.setOnOrientationListener(new OnOrientationListener(){

			@Override
			public void onOrientationChange(float x) {
				mCurrentX=x;
				
				// TODO 自动生成的方法存根
				
			}
			
		});
		// TODO 自动生成的方法存根
		
	}





	private void initView() {
		mapView=(MapView) findViewById(R.id.bmapView);
		mBaidumap=mapView.getMap();
		MapStatusUpdate msu=MapStatusUpdateFactory.zoomTo(15.0f);
		mBaidumap.setMapStatus(msu);
		mBaidumap.setOnMapLongClickListener(new OnMapLongClickListener() {
			
			
			@Override
			public void onMapLongClick(LatLng ll) {
				Toast.makeText(mcontext, "设置目的地成功", Toast.LENGTH_SHORT).show();
				mDestLocationData=ll;
				addDestInfoOverlay(ll);
				// TODO 自动生成的方法存根
				
			}

			
		});
		// TODO 自动生成的方法存根
		
	}
	
	private void addDestInfoOverlay(LatLng ll) {
		mBaidumap.clear();
		OverlayOptions options=new MarkerOptions().position(ll)//
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark));
		mBaidumap.addOverlay(options);
		
		// TODO 自动生成的方法存根
		
	}




	@Override
	protected void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		mBaidumap.setMyLocationEnabled(true);
		if(!mLocationClient.isStarted()){
			mLocationClient.start();
		}
       mOrientationListener.start();
		
	}





	@Override
	protected void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();
		mBaidumap.setMyLocationEnabled(false);
		mLocationClient.stop();
		mOrientationListener.stop();
		
	}





	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		mapView.onResume();
	}




	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		mapView.onPause();
	}



	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		mapView.onDestroy();                                                                                                                                                                                                                                                            
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.id_map_common:
			mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;
		case R.id.id_map_satelite:
			mBaidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.id_map_traffic:
			if(mBaidumap.isTrafficEnabled()){
				mBaidumap.setTrafficEnabled(false);
				item.setTitle("实时交通(off)");
				
			}
			else{
				mBaidumap.setTrafficEnabled(true);
				item.setTitle("实时交通(on)");
			}
			break;
		case R.id.my_location:
			 centerToMyLocation();
			break;
			
		case R.id.map_meode_common:
			mLocationMode=LocationMode.NORMAL;
			break;
			
		case R.id.map_mode_follow:
			mLocationMode=LocationMode.FOLLOWING;
			break;
		
		case R.id.map_mode_compass:
			mLocationMode=LocationMode.COMPASS;
			break;
		case R.id.add_overlay:
			addOverlays(Info.infos);
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}





	private void addOverlays(List<Info> infos) {
		mBaidumap.clear();
		LatLng latlng=null;
		Marker marker=null;
		OverlayOptions options;
		for(Info info:infos){
			latlng=new LatLng(info.getLatitude(),info.getLongitude());
			options=new MarkerOptions().position(latlng).icon(mMarker).zIndex(5);
			marker=(Marker) mBaidumap.addOverlay(options);
			
			Bundle arg0=new Bundle();
			arg0.putSerializable("info", info);// 传递对象
			marker.setExtraInfo(arg0);// 传递对象值
			
		}
		
		
		MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latlng);
		mBaidumap.setMapStatus(msu);
		// TODO 自动生成的方法存根
		
	}





	private void centerToMyLocation() {
		Toast.makeText(mcontext, "正在定位...", Toast.LENGTH_SHORT).show();
		LatLng latlng=new LatLng(mLatitude,mLongitude);
		 MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latlng);
		mBaidumap.animateMapStatus(msu);
	}
	
	private class MLocationListener implements BDLocationListener{

		@Override
		public void onConnectHotSpotMessage(String arg0, int arg1) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			
			
			MyLocationData data=new MyLocationData.Builder()//
			.direction(mCurrentX)//
			.accuracy(location.getRadius())//
			.latitude(location.getLatitude())//
			.longitude(location.getLongitude())//
			.build();
			
			mBaidumap.setMyLocationData(data);
			
			MyLocationConfiguration config= new MyLocationConfiguration(mLocationMode,true,mIconLocation);
			mBaidumap.setMyLocationConfigeration(config); //模式和自定义图标
			
			mLatitude=location.getLatitude();
			mLongitude=location.getLongitude();
			
			 new LatLng(mLatitude,mLongitude);
             if(isFirstIn){
            	 LatLng latlng=new LatLng(location.getLatitude(),location.getLongitude());
            	 MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latlng);
				mBaidumap.animateMapStatus(msu);
				isFirstIn=false;
				Toast.makeText(mcontext,location.getAddrStr(), Toast.LENGTH_SHORT).show();
			}
			
			
			
			// TODO 自动生成的方法存根
			
		
		
		}
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.where_button:
			 centerToMyLocation();
			break;
		case R.id.mock_button:
			centerToNav(false);
			
			break;
		case R.id.real_button:
			centerToNav(true);
			break;
	
		
			
			default:
				break;
				
		
		}
		// TODO 自动生成的方法存根
		
	}
	
	private void centerToNav(boolean type) {
		if(mDestLocationData==null){
			Toast.makeText(mcontext, "长按地图设置目标地点！", Toast.LENGTH_SHORT).show();
		}
		else{
			routeplanToNavi(type);
		}
		
	}

	
	
	
	
	
	
	
	
}
