package com.whereim.clearapps.view;

import com.whereim.clearapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ClearAnimView extends RelativeLayout{
	private RotateAnimation rcw,rccw;
	
	private ImageView rotateCwView,rotateCcwView;
	private int w,h;
	private void init(Context context) {
		rotateCwView=new ImageView(context);
		rotateCcwView=new ImageView(context);
		Bitmap th=BitmapFactory.decodeResource(getResources(), R.drawable.th);
		Bitmap shp=BitmapFactory.decodeResource(getResources(), R.drawable.shp);
		if(th.getWidth()>shp.getWidth()){
			w=th.getWidth();
		}else{
			w=shp.getWidth();
		}
		if(th.getHeight()>shp.getHeight()){
			h=th.getHeight();
		}else{
			h=shp.getHeight();
		}
		rotateCwView.setImageBitmap(th);
		rotateCcwView.setImageBitmap(shp);
		RelativeLayout.LayoutParams lp1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp1.addRule(RelativeLayout.CENTER_IN_PARENT);
		RelativeLayout.LayoutParams lp2=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp2.addRule(RelativeLayout.CENTER_IN_PARENT);
		rotateCwView.setLayoutParams(lp1);
		rotateCcwView.setLayoutParams(lp2);
		rcw=(RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.rotate_cw_anim);
		rccw=(RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.rotate_ccw_anim);
		addView(rotateCcwView);
		addView(rotateCwView);
	}
	 
	public void startAnim(){
		rotateCwView.startAnimation(rcw);
		rotateCcwView.startAnimation(rccw);
	}
	
	public void stopAnim() {
		rcw.setInterpolator(new AccelerateDecelerateInterpolator());
		rcw.setDuration(2000);
		rcw.setRepeatCount(0);
		rccw.setInterpolator(new AccelerateDecelerateInterpolator());
		rccw.setDuration(2000);
		rccw.setRepeatCount(0);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int wms=MeasureSpec.makeMeasureSpec(w, MeasureSpec.getMode(widthMeasureSpec));
		int hms=MeasureSpec.makeMeasureSpec(h, MeasureSpec.getMode(heightMeasureSpec));
		if(MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.EXACTLY){
			wms=widthMeasureSpec;
		}
		if(MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.EXACTLY){
			hms=heightMeasureSpec;
		}
		super.onMeasure(wms,hms);
	}
	
	public ClearAnimView(Context context) {
		super(context);
		init(context);
	}
	public ClearAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
}