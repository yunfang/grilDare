package com.famlink.frame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famlink.frame.R;

public class NavigateBar extends RelativeLayout{

	private Button leftButton;
	private TextView leftText;
	private TextView titleText;
	private Button rightButton;
	private TextView rightText;
	/**
	 * leftLinearLayout显示
	 */
	public final static int LEFT_LINEAR_VISIBILITY = 1; 
	/**
	 * leftLinearLayout不显示
	 */
	public final static int LEFT_LINEAR_INVISIBILITY = 2; 
	/**
	 * leftButton显示并且leftText不显示
	 */
	public final static int LEFT_BUTTON_VISIBILITY = 3;
	/**
	 * leftText显示并且leftButton不显示
	 */
	public final static int LEFT_TEXT_VISIBILITY = 4;  
	
	/**
	 * rightLinearLayout显示
	 */
	public final static int RIGHT_LINEAR_VISIBILITY = 5; 
	/**
	 * rightLinearLayout不显示
	 */
	public final static int RIGHT_LINEAR_INVISIBILITY = 6; 
	/**
	 * rightButton显示并且rightText不显示
	 */
	public final static int RIGHT_BUTTON_VISIBILITY = 7; 
	/**
	 * rightText显示并且rightButton不显示
	 */
	public final static int RIGHT_TEXT_VISIBILITY = 8; 
	
	
	private RelativeLayout  leftLinearLayout, leftLinearLayout_1;
	private RelativeLayout rightLinearLayout, rightLinearLayout_1;
	private int STANDARD_HEIGTH = 1080;// 标准宽的像素
	private int STANDARD_WITH = 1800;// 标准高的像素+
	
	private SceenMannage sm;
	TypedArray typeA;
//	Typeface tf;
	private static Context context;
	
	public Button getRightButton() {
		return rightButton;
	}
	public NavigateBar(Context context) {
		this(context, null, 0);
		// TODO Auto-generated constructor stub
		sm = new SceenMannage(context);
	}
	public NavigateBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		sm = new SceenMannage(context);
		// TODO Auto-generated constructor stub
	}
	public NavigateBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
//		this.setBackgroundResource(resid);   //设置背景颜色
		leftLinearLayout = new RelativeLayout(getContext());
		leftLinearLayout_1 = new RelativeLayout(getContext());
		rightLinearLayout = new RelativeLayout(getContext());
		rightLinearLayout_1 = new RelativeLayout(getContext());
		
		leftButton = new Button(context);
		leftText = new TextView(context);
		titleText = new TextView(context);
		rightButton = new Button(context);
		
		rightText = new TextView(context);
		sm = new SceenMannage(context);
		
//		tf = Typeface.createFromAsset(context.getAssets(), "fonts/yhcy.otf");
		//左边区域
		LayoutParams leftLinearLayoutParams = new  LayoutParams(setSceemManage(150), ViewGroup.LayoutParams.FILL_PARENT);
		leftLinearLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		
		LayoutParams leftLinearLayoutParams_1 = new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
		leftLinearLayoutParams_1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		LayoutParams leftLayoutParams = new  LayoutParams(setSceemManage(35), setSceemManage(35));
		leftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		leftButton.setId(1);
		leftLinearLayout_1.addView(leftButton, leftLayoutParams);
		
		LayoutParams leftTextLayoutParams = new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		leftTextLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		leftTextLayoutParams.addRule(RelativeLayout.RIGHT_OF, 1);
		leftText.setId(2); 
		leftLinearLayout_1.addView(leftText, leftTextLayoutParams);
		   
		leftLinearLayout.addView(leftLinearLayout_1, leftLinearLayoutParams_1);
		leftLinearLayout.setBackgroundColor(Color.TRANSPARENT);
		this.addView(leftLinearLayout, leftLinearLayoutParams);
		
		
		
		//中间标题
		LayoutParams titleLayoutParams = new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		this.addView(titleText, titleLayoutParams);
		
		
		
		
		
		  //右边区域
		LayoutParams rightLinearLayoutParams = new  LayoutParams(setSceemManage(150), ViewGroup.LayoutParams.FILL_PARENT);
		rightLinearLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		
		LayoutParams rightLinearLayoutParams_1 = new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT);
		rightLinearLayoutParams_1.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		LayoutParams rightLayoutParams = new  LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		//rightLayout.rightMargin = setSceemManage(10);
		rightText.setId(3);
//		rightLayoutParams.rightMargin = setSceemManage(20);
		rightLinearLayout_1.addView(rightText, rightLayoutParams);
		
		  
		LayoutParams rightTextLayoutParams = new  LayoutParams(setSceemManage(35), setSceemManage(35));
//		rightTextLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		rightTextLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		//rightLayout.rightMargin = setSceemManage(10);   
//		rightTextLayoutParams.rightMargin = setSceemManage(20);
		rightTextLayoutParams.addRule(RelativeLayout.RIGHT_OF, 3);
		rightButton.setId(4);
		rightLinearLayout_1.addView(rightButton, rightTextLayoutParams);
		  
		rightLinearLayout.addView(rightLinearLayout_1, rightLinearLayoutParams_1);
		
		rightLinearLayout.setBackgroundColor(Color.TRANSPARENT);
		this.addView(rightLinearLayout, rightLinearLayoutParams);
		  
	}
	/**
	 * 屏幕适配
	 * @param currentWidth
	 * @return
	 */
	public int setSceemManage(int currentWidth){
		DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

		int large = (metrics.widthPixels > metrics.heightPixels) ? metrics.widthPixels
				: metrics.heightPixels;
		return (int) ((large * 1f / STANDARD_WITH)*1.5*currentWidth);
	}

	/**
	 *
	 * 给左边的自定义按钮设置文字
	 * @param text     填充的文字
	 * @param textSize   给填充的文字设置字体大小
	 * @param textColor 给填充的文字设置字体颜色
	 */
	public void setLeftButtonTextMessage(CharSequence text, int textSize, int textColor)
	{
//		leftText.setTypeface(tf);
		leftText.setTextSize(sm.setTextSize(textSize));
		leftText.setTextColor(textColor);
		leftText.setText(text);
	}
	/**
	 * 给最左边的自定义按钮设置图片
	 * @param resid
	 */
	public void setLeftButtonBg(int resid)
	{
		leftButton.setBackgroundResource(resid);
	}
	
	/**
	 * 设置左侧按钮是否显示
	 * LEFT_LINEAR_VISIBILITY = 1;  //leftLinearLayout显示
	 * LEFT_LINEAR_INVISIBILITY = 2; //leftLinearLayout不显示
	 *	LEFT_BUTTON_VISIBILITY = 3; //leftButton显示并且leftText不显示
	 *	LEFT_TEXT_VISIBILITY = 4;  //leftText显示并且leftButton不显示
	 * @param isVisibility
	 */
	public void setLeftButtonVisibly(int isVisibility)
	{
		if(isVisibility == LEFT_LINEAR_VISIBILITY){  
			leftLinearLayout.setVisibility(View.VISIBLE);
		}else if(isVisibility == LEFT_LINEAR_INVISIBILITY){
			leftLinearLayout.setVisibility(View.INVISIBLE);
		}else if(isVisibility == LEFT_BUTTON_VISIBILITY){
			leftButton.setVisibility(View.VISIBLE);
			leftText.setVisibility(View.INVISIBLE);
		}else if(isVisibility == LEFT_TEXT_VISIBILITY){
			leftButton.setVisibility(View.GONE);
			leftText.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 给左边的按钮设置监听事件
	 * @param l
	 */
	public void setOnLeftButtonClickListener(OnClickListener l)
	{
		leftButton.setOnClickListener(l);
		leftText.setOnClickListener(l);
		leftLinearLayout.setOnClickListener(l);
		setButtonFocusChanged(leftButton);
	}
	
	/**
	 * 给Title设置标题
	 * @param text
	 */
	public void setTitleTextMessage(CharSequence text, int size, int color)
	{
//		titleText.setTypeface(tf);
		titleText.setTextSize(sm.setTextSize(size));
		titleText.setTextColor(color);
		titleText.setText(text);
	}
	/**
	 * 给Title设置标题
	 * @param text
	 */
	public void setTitleTextMessage(int resId)
	{
//		titleText.setTypeface(tf);
		titleText.setBackgroundResource(resId);
	}
	/**
	 * 给中间的按钮设置监听事件
	 * @param l
	 */
	public void setOnTitleButtonClickListener(OnClickListener l)
	{
		titleText.setOnClickListener(l);
	}
	
	/**
	 *
	 * 给右边的自定义按钮设置文字
	 * @param text     填充的文字
	 * @param textSize   给填充的文字设置字体大小
	 * @param textColor 给填充的文字设置字体颜色
	 */
	public void setRightButtonTextMessage(CharSequence text, int textSize, int textColor)
	{
//		rightText.setTypeface(tf);
		rightText.setTextSize(sm.setTextSize(textSize));
		rightText.setTextColor(textColor);
		rightText.setText(text);
	}
	/**
	 * 给最右边的自定义按钮设置图片
	 * @param resid
	 */
	public void setRightButtonBg(int resid)
	{
		rightButton.setBackgroundResource(resid);
	}
	/**
	 * 设置右侧按钮是否显示
	 * RIGHT_LINEAR_VISIBILITY = 5;              //rightLinearLayout显示
	 * RIGHT_LINEAR_INVISIBILITY = 6;         //rightLinearLayout不显示
	 *	RIGHT_BUTTON_VISIBILITY = 7;          //rightButton显示并且leftText不显示
	 *	RIGHT_TEXT_VISIBILITY = 8;                 //rightText显示并且leftButton不显示
	 * @param isVisibility
	 */
	public void setRightButtonVisibly(int isVisibility)
	{
		if(isVisibility == RIGHT_LINEAR_VISIBILITY){  
			rightLinearLayout.setVisibility(View.VISIBLE);
		}else if(isVisibility == RIGHT_LINEAR_INVISIBILITY){
			rightLinearLayout.setVisibility(View.INVISIBLE);
		}else if(isVisibility == RIGHT_BUTTON_VISIBILITY){
			rightButton.setVisibility(View.VISIBLE);
			rightText.setVisibility(View.INVISIBLE);
		}else if(isVisibility == RIGHT_TEXT_VISIBILITY){
			rightButton.setVisibility(View.GONE);
			rightText.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * 给右边的按钮设置监听事件
	 * @param l
	 */
	public void setOnRightButtonClickListener(OnClickListener l)
	{
		rightButton.setOnClickListener(l);
		rightText.setOnClickListener(l);
		rightLinearLayout.setOnClickListener(l);
		setButtonFocusChanged(rightButton);
	}  
	
	
	/**  
	   * 按下这个按钮进行的颜色过滤  
	   */  
	  public final static float[] BT_SELECTED=new float[] {    
	      1, 0, 0, 0, 100,    
	      0, 1, 0, 0, 100,    
	      0, 0, 1, 0, 0,    
	      0, 0, 0, 1, 0 };   
	     
	  /**  
	   * 按钮恢复原状的颜色过滤  
	   */  
	  public final static float[] BT_NOT_SELECTED=new float[] {    
	      1, 0, 0, 0, 0,    
	      0, 1, 0, 0, 0,    
	      0, 0, 1, 0, 0,    
	      0, 0, 0, 1, 0 };   
	     
	  /**  
	   * 按钮焦点改变  
	   */  
	  public final static OnFocusChangeListener buttonOnFocusChangeListener=new OnFocusChangeListener() {   
	     
	  @Override  
	  public void onFocusChange(View v, boolean hasFocus) {   
		  if(v != null){
			  if (hasFocus) {   
//				  v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));   
//				  v.getBackground().setColorFilter(colors.all_rel_back_or, PorterDuff.Mode.MULTIPLY);
				  v.getBackground().setColorFilter(context.getResources().getColor(R.color.all_rel_back_or),PorterDuff.Mode.MULTIPLY);
				  v.setBackgroundDrawable(v.getBackground());   
			  }   
			  else  
			  {   
//				  v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));   
				  v.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
				  v.setBackgroundDrawable(v.getBackground());   
			  }   
		  }
	  	}   
	 };   
	    
	  /**  
	   * 按钮触碰按下效果  
	   */  
	 public final static OnTouchListener buttonOnTouchListener=new OnTouchListener() {   
	  @Override      
	  public boolean onTouch(View v, MotionEvent event) {   
		  if(v != null){
			  if(event.getAction() == MotionEvent.ACTION_DOWN){   
//				  v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));   
//				  v.getBackground().setColorFilter(colors.all_rel_back_or, PorterDuff.Mode.MULTIPLY);
				  v.getBackground().setColorFilter(context.getResources().getColor(R.color.all_rel_back_or),PorterDuff.Mode.MULTIPLY);
				  v.setBackgroundDrawable(v.getBackground());   
			  }   
			  else if(event.getAction() == MotionEvent.ACTION_UP){   
//				  v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));   
				  v.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
				  v.setBackgroundDrawable(v.getBackground());   
			  }   
		  }
		   return false;   
	  	}   
	 };   
	    
	 /**  
	  * 设置图片按钮获取焦点改变状态  
	  * @param inImageButton  
	  */  
	 public final static void setButtonFocusChanged(View inView)   
	 {   
	  inView.setOnTouchListener(buttonOnTouchListener);   
	  inView.setOnFocusChangeListener(buttonOnFocusChangeListener);   
	 }  
	 
}
