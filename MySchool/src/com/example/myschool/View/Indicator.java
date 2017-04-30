package com.example.myschool.View;

import com.example.myschool.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Indicator extends LinearLayout {
	private int mTriangleWidth;
	private int mTriangleHeight;
	private int mInitTranslationX;
	private int mTranslationX;
	
	public static final int RADIO_OF_SCREEN=1/6;
	private static final int COLOR_TEXT_NORMAL=0x77FFFFFF;
	private Paint mPaint;
	private Path mPath;
	
	
	
	
	public Indicator(Context context) {
		this(context,null);
		// TODO �Զ����ɵĹ��캯�����
	}
	
	
	public Indicator(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO �Զ����ɵĹ��캯�����
	}

	
	public Indicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		TypedArray ta=context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
		ta.recycle();
		
		//���黭��
		initPaint();
		
	}


	private void initPaint() {
		mPaint=new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.WHITE);
		mPaint.setStyle(Style.FILL);
		mPaint.setPathEffect(new CornerPathEffect(3.0f));
		// TODO �Զ����ɵķ������
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO �Զ����ɵķ������
		super.onSizeChanged(w, h, oldw, oldh);
		mTriangleWidth=w/12;
		mTriangleHeight=mTriangleWidth/2;
		mInitTranslationX=w/3/2-mTriangleWidth/2;
		
		initTriangle();  //����������
	}


	private void initTriangle() {
		mPath=new Path();
		mPath.moveTo(0, 0);
		mPath.lineTo(mTriangleWidth, 0);
		mPath.lineTo(mTriangleWidth/2, mTriangleHeight);
		mPath.close();
		// TODO �Զ����ɵķ������
		
	}
	
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		canvas.save();
		canvas.translate(mInitTranslationX+mTranslationX, -5);
		canvas.drawPath(mPath, mPaint);
		canvas.restore();
		
	}
	
	public void Scroll(int pos){
		int tabWidth=getWidth()/3;
		mTranslationX=(int) (tabWidth*(pos));
		invalidate();
		
		
	}
	
	public void restTextColor(int mCurrentPos){
		int count=getChildCount();
		TextView txv;
		for(int i=0;i<count;i++){
			txv=(TextView) getChildAt(i);
			txv.setTextColor(COLOR_TEXT_NORMAL);
			if(i==mCurrentPos){
				txv.setTextColor(Color.WHITE);
			}
		}
		 invalidate();
	}

	

	

	
	
	

}
