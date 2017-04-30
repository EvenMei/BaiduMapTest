package com.example.myschool.View;

import com.example.myschool.R;

import android.R.color;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

public class VariousShapeImageView extends ImageView {
	
	private static final int TYPE_CIRCLE=0;
	private static final int TYPE_ROUND_RECT=1;
	private static final int TYPE_OVAL=2;
	private int type=TYPE_ROUND_RECT;
	
	
	
	private static final ScaleType SCALE_TYPE=ScaleType.CENTER_CROP;
	private static final Bitmap.Config BITMAP_CONFIG=Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION=1;
	
	private static final int DEFAULT_BORDER_WIDTH=0;
	private static final int DEFAULT_COLOR=color.black;
	
	private RectF mBorderRectF=new RectF(); 
	private RectF mDrawableRectf=new RectF();
	
	private Paint mBorderPaint=new Paint();
	private Paint mBitmapPaint=new Paint();
	
	private Matrix shaderMatrix= new Matrix();
	
	private int mBorderWidth=DEFAULT_BORDER_WIDTH;
	private int mBorderColor=DEFAULT_COLOR;
	
	
	private Bitmap mBitmap;
	private BitmapShader mShader;
	private int mBitmapWidth;
	private int mBitmapHeight;
	
	
	private float mBorderRadius;
	private float mDrawableRadius;
	
	private boolean mReady=true;
//	private boolean mSetUpPending;
	
	
	
	public VariousShapeImageView(Context context) {
		this(context,null);
		// TODO 自动生成的构造函数存根
	}


	public VariousShapeImageView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO 自动生成的构造函数存根
	}
	
	public VariousShapeImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.VariousShapeImageView, defStyle, 0);
		
		mBorderWidth=ta.getDimensionPixelSize(
				R.styleable.VariousShapeImageView_mborder_width, DEFAULT_BORDER_WIDTH);
		mBorderColor=ta.getColor(
				R.styleable.VariousShapeImageView_mborder_color, DEFAULT_COLOR);
		type=ta.getInt(R.styleable.VariousShapeImageView_shape,0);
		ta.recycle();
		
		init();
		// TODO 自动生成的构造函数存根
	}

	
	

	private void init() {
		super.setScaleType(SCALE_TYPE);
		mReady=true;

		// TODO 自动生成的方法存根
		
	}

	private void setUp() {
		if(!mReady){
	
			return ;
			
		}
		if(mBitmap==null){
			return ;
		}
		
		mShader=new BitmapShader(mBitmap,Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
		
		mBitmapPaint.setAntiAlias(true);
		mBitmapPaint.setShader(mShader);
		
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStyle(Style.STROKE);
		mBorderPaint.setStrokeWidth(mBorderWidth);
		
	
		
		mBitmapWidth=mBitmap.getWidth();
		mBitmapHeight=mBitmap.getHeight();
		
		
		//划圆形时的半径
		mBorderRectF.set(0,0,getWidth(), getHeight() );
		
		mBorderRadius=Math.min((mBorderRectF.width()-mBorderWidth)/2,
				(mBorderRectF.height()-mBorderWidth/2));
		
		
		mDrawableRectf.set(mBorderWidth, mBorderWidth,
				mBorderRectF.width()-2*mBorderWidth,mBorderRectF.height()-2*mBorderWidth );
		
		mDrawableRadius=Math.min((mDrawableRectf.width())/2,mDrawableRectf.height()/2 );
		
		updateMatrix();
		invalidate();
		
		
		
		// TODO 自动生成的方法存根
		
	}
	
	
	public void setScaleType(ScaleType type){
		if(type!=SCALE_TYPE){
			throw new IllegalArgumentException(String.format("不是正确的类型！"));
		}
		
	}
	public ScaleType getType(){
		 return SCALE_TYPE;
	}
	
	
	
	public void setMyType(int Type){
		if(Type==type){
			return ;
		}
		this.type=Type;
		requestLayout();
		setUp();
		invalidate();
	}
	
	public int getMYType(){
		return type;
	}
	
	public void setBorderWidth(int borderWidth){
		if(borderWidth==mBorderWidth){
			return ;
		}
		mBorderWidth=borderWidth;
		setUp();
	}
	
	public int getBorderWidth(){
		return mBorderWidth;
	}
	
	
	/*
	 * 覆盖原方法，设置image来源
	 */
	
	@Override
	public void setImageBitmap(Bitmap bm) {
		// TODO 自动生成的方法存根
		super.setImageBitmap(bm);
		mBitmap=bm;
		setUp();
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		// TODO 自动生成的方法存根
		super.setImageDrawable(drawable);
		mBitmap=getBitmapFromDrawable(drawable);
		setUp();
	}
	
	



	@Override
	public void setImageResource(int resId) {
		// TODO 自动生成的方法存根
		super.setImageResource(resId);
		mBitmap=getBitmapFromDrawable(getDrawable());
		setUp();
	}
	@Override
	public void setImageURI(Uri uri) {
		// TODO 自动生成的方法存根
		super.setImageURI(uri);
		mBitmap=getBitmapFromDrawable(getDrawable());
		setUp();
	}
	
	

	
	

	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		if(drawable==null){
			return null;
		}
		if(drawable instanceof BitmapDrawable){
			return ((BitmapDrawable) drawable).getBitmap();
		}
		
		try{
			Bitmap bitmap;
			if(drawable instanceof ColorDrawable){
				bitmap=Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
						COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
			}else{
				int w=drawable.getIntrinsicWidth();
				int h=drawable.getIntrinsicHeight();
				bitmap=Bitmap.createBitmap(w,h,BITMAP_CONFIG);
			}
			
			
			Canvas canvas=new Canvas(bitmap);
			drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());
			drawable.draw(canvas);
			return bitmap;	
		}catch(OutOfMemoryError e){
			return null;
		}
		// TODO 自动生成的方法存根
		
	}

	
	
	

	private void updateMatrix() {
		float scale;
		float dx=0;
		float dy=0;
		shaderMatrix.set(null);
		
		
		//需要填满整个区域，去最小值来放大
		
		if (mBitmapWidth * mDrawableRectf.height() > mBitmapHeight
				*mDrawableRectf.width()
				 ) {
			scale = mDrawableRectf.height() / (float) mBitmapHeight;  //   H/h=scale
			dx = (mDrawableRectf.width() - mBitmapWidth * scale) * 0.5f;//  dx=
		} else {
			scale = mDrawableRectf.width() / (float) mBitmapWidth;   
			dy = (mDrawableRectf.height() - mBitmapHeight * scale) * 0.5f;
		}
		
		
		
		shaderMatrix.setScale(scale, scale);
		shaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth,
				(int) (dy + 0.5f) + mBorderWidth);
		mShader.setLocalMatrix(shaderMatrix);
	
		// TODO 自动生成的方法存根
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO 自动生成的方法存根
		super.onSizeChanged(w, h, oldw, oldh);
		setUp();
	}
	
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (getDrawable() == null) {
			
			return;
		}
		if(type==TYPE_CIRCLE){
			canvas.drawCircle(getWidth()/2,getHeight()/2 , mBorderRadius, mBitmapPaint);
		}
		else if(type==TYPE_ROUND_RECT){
			canvas.drawRoundRect(mBorderRectF, mBorderRadius/2,mBorderRadius/2 ,mBitmapPaint);
		}
		else if(type==TYPE_OVAL){
			canvas.drawOval(mBorderRectF, mBitmapPaint);
		}
	}
	
	public void setBorderRadius(int borderRadius){
		int newbr=dp2px(borderRadius);
		this.mBorderRadius=newbr;
		
	}

	private int dp2px(int borderRadius) {
		
		
		// TODO 自动生成的方法存根
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				borderRadius, getResources().getDisplayMetrics());
	}

	
	

}
