package com.example.myschool;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

public class MyHorizontalScrollView extends HorizontalScrollView {
	private boolean myState=true;

    // ��HorizontalScrollView�и�LinearLayout
    private LinearLayout linearLayout;
    // �˵�������ҳ
    private ViewGroup myMenu;
    private ViewGroup myContent;
    //�˵����
    private  int myMenuWidth;

    // ��Ļ���
    private int screenWidth;
    // �˵�����Ļ�Ҳ�ľ���(dp)
    private int myMenuPaddingRight = 50;

    // �����ε���onMeasure�ı�־
    private boolean once = false;
  
    private static Context mcontext;
    
    
    public interface onStateListener{
    	void sendstate(boolean state);
    }
    
    private onStateListener mstateListener;
    
    public void setOnStateChanged(onStateListener listener){
    	this.mstateListener=listener;
    }

    /**
     * �Զ���View��Ҫʵ�ִ���Context��AttributeSet��2�������Ĺ��췽��,�����Զ�����������
     * ��ʹ�����Զ�������ʱ������ô˹��췽��
     * 
     * @param context
     * @param attrs
     */
    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
       
        this.mcontext=context;
        // ��ȡ��Ļ���
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;// ��Ļ���
       
        // ��dpת��px
        myMenuPaddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources()
                        .getDisplayMetrics());

    }

    /**
     * ������View�Ŀ�ߣ���������View�Ŀ�ߣ�ÿ������������ô˷���
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!once) {//ʹ��ֻ����һ��
            // thisָ����HorizontalScrollView����ȡ����Ԫ��
            linearLayout = (LinearLayout) this.getChildAt(0);// ��һ����Ԫ��
            myMenu = (ViewGroup) linearLayout.getChildAt(0);// HorizontalScrollView��LinearLayout�ĵ�һ����Ԫ��
            myContent = (ViewGroup) linearLayout.getChildAt(1);// HorizontalScrollView��LinearLayout�ĵڶ�����Ԫ��

            // ������View�Ŀ�ߣ�������Ļһ��
            myMenuWidth=myMenu.getLayoutParams().width = screenWidth*3/4;// �˵��Ŀ��=��Ļ���-�ұ߾�
            myContent.getLayoutParams().width = screenWidth;// ���ݿ��=��Ļ���
            // ��������View�Ŀ�ߣ�������Ļһ��
            // ���������LinearLayout��ֻ������Menu��Content���ԾͲ���Ҫ�����ȥָ������Ŀ�
            once = true;
        }
    }

    //����View��λ�ã����ȣ��Ƚ�Menu���أ���eclipse��ScrollView�Ļ������ݣ��ǹ�������������ʾ�����ƣ������ƣ�
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //����������ʱ������Menu�˵�Ҳ����ScrollView���󻬶��˵�����Ĵ�С
        if(changed){
            this.scrollTo(myMenuWidth, 0);//���󻬶����൱�ڰ��ұߵ�����ҳ�ϵ������룬�˵�����     +��-��
            myState=true;
        }
    
    }
    
   
   
   
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action) {
        case MotionEvent.ACTION_UP:
            int scrollX=this.getScrollX();  //�����ľ���scrollTo�����Ҳ����onMeasure����������󻬶��ǲ���
           
            if(scrollX>=myMenuWidth/2){
                this.smoothScrollTo(myMenuWidth,0);  //���󻬶�չʾ����
                myState=true;
            }
            else{
                this.smoothScrollTo(0, 0);
                myState=false;
            }
   
            return true;
          
            
      
        }
        return super.onTouchEvent(ev);
    }
    
    
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.i("tuzi",l+"");
        float scale = l * 1.0f / myMenuWidth; // 1 ~ 0
        float rightScale = 0.7f + 0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1 - scale);

      
        // �������Զ���������TranslationX
//       ViewHelper.setTranslationX(myMenu, myMenuWidth * scale * 0.8f);  //����벿������ƽ��
//        
//        ViewHelper.setScaleX(myMenu, leftScale);
//        ViewHelper.setScaleY(myMenu, leftScale);
//        ViewHelper.setAlpha(myMenu, leftAlpha);
//        // �����������ŵ����ĵ�
//        ViewHelper.setPivotX(myContent, 0);
//        ViewHelper.setPivotY(myContent,myContent.getHeight()/2);
//        ViewHelper.setScaleX(myContent, rightScale);
//        ViewHelper.setScaleY(myContent, rightScale);
        
        if(mstateListener!=null){
        	mstateListener.sendstate(myState);
        }
    }
    
    public void Scrolltotarget(boolean state){
    	if(state){
    		this.smoothScrollTo(0,0);              //show menu
    		myState=false;
    	}
    	else{
    		this.smoothScrollTo(myMenuWidth, 0);       //show Content
    		myState=true;
    	}
    	 
    }
    
    

}