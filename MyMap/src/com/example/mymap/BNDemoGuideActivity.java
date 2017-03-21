package com.example.mymap;

import java.util.ArrayList;
import java.util.List;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRouteGuideManager.CustomizedLayerItem;
import com.baidu.navisdk.adapter.BNRouteGuideManager.OnNavigationListener;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 璇卞鐣岄潰
 * 
 * @author sunhao04
 *
 */
public class BNDemoGuideActivity extends Activity {

    private final String TAG = BNDemoGuideActivity.class.getName();
    private BNRoutePlanNode mBNRoutePlanNode = null;
    private BaiduNaviCommonModule mBaiduNaviCommonModule = null;

    /*
     * 瀵逛簬瀵艰埅妯″潡鏈変袱绉嶆柟寮忔潵瀹炵幇鍙戣捣瀵艰埅銆�1锛氫娇鐢ㄩ�鐢ㄦ帴鍙ｆ潵瀹炵幇 2锛氫娇鐢ㄤ紶缁熸帴鍙ｆ潵瀹炵幇
     * 
     */
    // 鏄惁浣跨敤閫氱敤鎺ュ彛
    private boolean useCommonInterface = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.activityList.add(this);
        createHandler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        }
        View view = null;
        if (useCommonInterface) {
            //浣跨敤閫氱敤鎺ュ彛
            mBaiduNaviCommonModule = NaviModuleFactory.getNaviModuleManager().getNaviCommonModule(
                    NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE, this,
                    BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE, mOnNavigationListener);
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onCreate();
                view = mBaiduNaviCommonModule.getView();
            }
            
        } else {
            //浣跨敤浼犵粺鎺ュ彛
            view = BNRouteGuideManager.getInstance().onCreate(this,mOnNavigationListener);
        }
        

        if (view != null) {
            setContentView(view);
        }

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable(MainActivity.ROUTE_PLAN_NODE);
            }
        }
        //鏄剧ず鑷畾涔夊浘鏍�
        if (hd != null) {
            hd.sendEmptyMessageAtTime(MSG_SHOW, 5000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onResume();
            }
        } else {
            BNRouteGuideManager.getInstance().onResume();
        }
        
      
     
    }

    protected void onPause() {
        super.onPause();
        
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onPause();
            }
        } else {
            BNRouteGuideManager.getInstance().onPause();
        }
      
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onDestroy();
            }
        } else {
            BNRouteGuideManager.getInstance().onDestroy();
        }
        MainActivity.activityList.remove(this);
      
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onStop();
            }
        } else {
            BNRouteGuideManager.getInstance().onStop();
        }
       
    }

    @Override
    public void onBackPressed() {
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onBackPressed(false);
            }
        } else {
            BNRouteGuideManager.getInstance().onBackPressed(false);
        }
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onConfigurationChanged(newConfig);
            }
        } else {
            BNRouteGuideManager.getInstance().onConfigurationChanged(newConfig);
        }

    };
    
    
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                Bundle mBundle = new Bundle();
                mBundle.putInt(RouteGuideModuleConstants.KEY_TYPE_KEYCODE, keyCode);
                mBundle.putParcelable(RouteGuideModuleConstants.KEY_TYPE_EVENT, event);
                mBaiduNaviCommonModule.setModuleParams(RouteGuideModuleConstants.METHOD_TYPE_ON_KEY_DOWN, mBundle);
                try {
                    Boolean ret = (Boolean)mBundle.get(RET_COMMON_MODULE);
                    if(ret) {
                        return true;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        } 
        return super.onKeyDown(keyCode, event);  
    }
    @Override
    protected void onStart() {
        super.onStart();
        // TODO Auto-generated method stub
        if(useCommonInterface) {
            if(mBaiduNaviCommonModule != null) {
                mBaiduNaviCommonModule.onStart();
            }
        } else {
            BNRouteGuideManager.getInstance().onStart();
        }
    }
    private void addCustomizedLayerItems() {
        List<CustomizedLayerItem> items = new ArrayList<CustomizedLayerItem>();
        CustomizedLayerItem item1 = null;
        if (mBNRoutePlanNode != null) {
            item1 = new CustomizedLayerItem(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(),
                    mBNRoutePlanNode.getCoordinateType(), getResources().getDrawable(com.example.mymap.R.drawable.ic_launcher),
                    CustomizedLayerItem.ALIGN_CENTER);
            items.add(item1);

            BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        }
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }

    private static final int MSG_SHOW = 1;
    private static final int MSG_HIDE = 2;
    private static final int MSG_RESET_NODE = 3;
    private Handler hd = null;

    private void createHandler() {
        if (hd == null) {
            hd = new Handler(getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MSG_SHOW) {
                        addCustomizedLayerItems();
                    } else if (msg.what == MSG_HIDE) {
                        BNRouteGuideManager.getInstance().showCustomizedLayer(false);
                    } else if (msg.what == MSG_RESET_NODE) {
                        BNRouteGuideManager.getInstance().resetEndNodeInNavi(
                                new BNRoutePlanNode(116.21142, 40.85087, "BD09ll", null, CoordinateType.GCJ02));
                    }
                };
            };
        }
    }

    private OnNavigationListener mOnNavigationListener = new OnNavigationListener() {

        @Override
        public void onNaviGuideEnd() {
            //閫�嚭瀵艰埅
            finish();
        }

        @Override
        public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {
            
            if (actionType == 0) {
                //瀵艰埅鍒拌揪鐩殑鍦�鑷姩閫�嚭
                Log.i(TAG, "notifyOtherAction actionType = " + actionType + ",瀵艰埅鍒拌揪鐩殑鍦帮紒");
            }

            Log.i(TAG, "actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
        }

    };
    
    private final static String RET_COMMON_MODULE = "module.ret";
    
    private interface RouteGuideModuleConstants {
        final static int METHOD_TYPE_ON_KEY_DOWN = 0x01;
        final static String KEY_TYPE_KEYCODE = "keyCode";
        final static String KEY_TYPE_EVENT = "event";
    }
}
