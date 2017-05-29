package com.famlink.frame.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SceenMannage {
	private int STANDARD_WITH = 1080;// 标准宽的像素
	private int STANDARD_HEIGTH = 1920;// 标准高的像素
	private float WIDTH_RATE; // 适配宽比
	private float HEIGHT_RATE; // 适配高比
	private float CHANGE_RATE = 1.5f; // dip转px转换比例
	private float fontScale;
	
	private Context context;
	
	/**
	 * 基准屏幕密度
	 */
	private double STANDARD_DENSITY = 240;

	/**
	 * 当前屏幕密度
	 */
	public double CURRENT_DENSITY;
	

	/**
	 * 屏幕密度比例
	 */
	private double DENSITY_RATIO;
	
	
	public SceenMannage(Context context) {
		init(context);
	}
	
	
	public SceenMannage(Context context, int standard_with, int standard_heigth, int standard_density) {
		STANDARD_WITH = standard_with;
		STANDARD_HEIGTH = standard_heigth;
		STANDARD_DENSITY=standard_density;
		init(context);
	}


	private void init(Context context) {
		
		this.context = context;
		
		fontScale =context.getResources().getDisplayMetrics().scaledDensity;  
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();

		int large = (metrics.widthPixels > metrics.heightPixels) ? metrics.widthPixels
				: metrics.heightPixels;
		int small = (metrics.widthPixels < metrics.heightPixels) ? metrics.widthPixels
				: metrics.heightPixels;
		CURRENT_DENSITY = metrics.densityDpi;
		WIDTH_RATE = small * 1f / STANDARD_WITH;
		HEIGHT_RATE = large * 1f / STANDARD_HEIGTH;
		DENSITY_RATIO = STANDARD_DENSITY / CURRENT_DENSITY;
		
		CHANGE_RATE=(float) (STANDARD_DENSITY/160);
		
		
		/*
		 * float density = metrics.density; Toast.makeText(context,
		 * "density="+density, Toast.LENGTH_SHORT).show();
		 */
	}

	/**
	 * 关于相对布局适配 不用适配传值为“0”
	 * 
	 * @param view
	 *            适配的对象
	 * @param width
	 *            宽 dip
	 * @param hight
	 *            高 dip
	 * @param topMargin
	 *            顶距离 dip
	 * @param liftMargin
	 *            左距离 dip
	 * @param rightMargin
	 *            右距离 dip
	 * @param buttomMargin
	 *            底距离 dip
	 */
	public void LinearLayoutParams(View view, float width, float hight,
			float topMargin, float liftMargin, float rightMargin,
			float buttomMargin) {
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
				.getLayoutParams();
		if (width != 0) {
			params.width = (int) (width * CHANGE_RATE * WIDTH_RATE);
		}
		if (hight != 0) {
			params.height = (int) (hight * CHANGE_RATE * HEIGHT_RATE);
		}
		if (topMargin != 0) {
			params.topMargin = (int) (topMargin * CHANGE_RATE * HEIGHT_RATE);
		}
		if (buttomMargin != 0) {
			params.bottomMargin = (int) (buttomMargin * CHANGE_RATE * HEIGHT_RATE);
		}
		if (liftMargin != 0) {
			params.leftMargin = (int) (liftMargin * CHANGE_RATE * WIDTH_RATE);
		}
		if (rightMargin != 0) {
			params.rightMargin = (int) (rightMargin * CHANGE_RATE * WIDTH_RATE);
		}
	}

	/**
	 * 关于相对布局适配 不用适配传值为“0”
	 * 
	 * @param view
	 *            适配的对象
	 * @param width
	 *            宽 dip
	 * @param hight
	 *            高 dip
	 * @param topMargin
	 *            顶距离 dip
	 * @param liftMargin
	 *            左距离 dip
	 * @param rightMargin
	 *            右距离 dip
	 * @param buttomMargin
	 *            底距离 dip
	 */
	public void RelativeLayoutParams(View view, float wight, float hight,
			float topMargin, float liftMargin, float rightMargin,
			float buttomMargin) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view
				.getLayoutParams();
		if (wight != 0) {
			params.width = (int) (wight * CHANGE_RATE * WIDTH_RATE);
		}
		if (hight != 0) {
			params.height = (int) (hight * CHANGE_RATE * HEIGHT_RATE);
		}
		if (topMargin != 0) {
			params.topMargin = (int) (topMargin * CHANGE_RATE * HEIGHT_RATE);
		}
		if (buttomMargin != 0) {
			params.bottomMargin = (int) (buttomMargin * CHANGE_RATE * HEIGHT_RATE);
		}
		if (liftMargin != 0) {
			params.leftMargin = (int) (liftMargin * CHANGE_RATE * WIDTH_RATE);
		}
		if (rightMargin != 0) {
			params.rightMargin = (int) (rightMargin * CHANGE_RATE * WIDTH_RATE);
		}
	}

	public void FragmentLayoutParams(View view, float wight, float hight,
			float topMargin, float liftMargin, float rightMargin,
			float buttomMargin) {
		FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view
				.getLayoutParams();
		if (wight != 0) {
			params.width = (int) (wight * CHANGE_RATE * WIDTH_RATE);
		}
		if (hight != 0) {
			params.height = (int) (hight * CHANGE_RATE * HEIGHT_RATE);
		}
		if (topMargin != 0) {
			params.topMargin = (int) (topMargin * CHANGE_RATE * HEIGHT_RATE);
		}
		if (buttomMargin != 0) {
			params.bottomMargin = (int) (buttomMargin * CHANGE_RATE * HEIGHT_RATE);
		}
		if (liftMargin != 0) {
			params.leftMargin = (int) (liftMargin * CHANGE_RATE * WIDTH_RATE);
		}
		if (rightMargin != 0) {
			params.rightMargin = (int) (rightMargin * CHANGE_RATE * WIDTH_RATE);
		}
	}
	
	/**
	 *  
	 * @param width px
	 * @return
	 */
	public float changeWidth(int width) {
		return (float) (width*WIDTH_RATE);
	}
	
	/**
	 * 
	 * @param hight
	 * @return
	 */
	public float changeHight(float hight) {
		return  (hight*CHANGE_RATE + 200);
	}
	/**
	 *
	 * @param hight
	 * @return
	 */
	public int changeHight(int hight) {
		return  (int)(hight*HEIGHT_RATE);
	}

	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public  int px2dip(float pxValue) {  
       // final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / CHANGE_RATE + 0.5f);  
    }  
	
	
	/**
	 * 设置字体大小
	 * 
	 * @param standardSize
	 *            原始大小
	 * @return int
	 */
	public int setTextSize(int standardSize) {
		int currentSize;

		currentSize = (int) (standardSize * WIDTH_RATE * DENSITY_RATIO);

		return currentSize;
	}
	
	
	/** 
     * 将px值转换为sp值，保证文字大小不变 
     *  
     * @param pxValue 
     *            （DisplayMetrics类中属性scaledDensity）
     * @return 
     */  
    public int px2sp(float pxValue) {  
        return (int) (pxValue / fontScale + 0.5f);  
    }  
    /**
     * 将sp值转换为px值，保证文字大小不变
     * 
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */ 
    public int sp2px(float spValue) { 
        return (int) (spValue * fontScale + 0.5f); 
    } 
	/**
	 *  px 转为 dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
}
