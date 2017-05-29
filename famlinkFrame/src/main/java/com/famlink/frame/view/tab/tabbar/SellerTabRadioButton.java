package com.famlink.frame.view.tab.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.famlink.frame.R;
import com.famlink.frame.util.Utils;


public class SellerTabRadioButton extends FrameLayout{

	private Context mContext;
	private RelativeLayout rb_container;
	private ImageView image;
	private TextView title;
	private boolean isChecked = false;
	// private TextView notify;

	private int img_normal;
	private int img_checked;
	private int text;
	private int text_color_normal;
	private int text_color_checked;
	private int bg_color_normal;
	private int bg_color_checked;

	public SellerTabRadioButton(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}
	
	public SellerTabRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
		try {
			img_normal = a.getResourceId(R.styleable.MyRadioButton_img_normal, R.mipmap.ic_launcher);
			img_checked = a.getResourceId(R.styleable.MyRadioButton_img_checked, R.mipmap.ic_launcher);
			text = a.getResourceId(R.styleable.MyRadioButton_text, R.string.app_name);
			text_color_normal = a.getResourceId(R.styleable.MyRadioButton_text_color_normal, R.color.white);
			text_color_checked = a.getResourceId(R.styleable.MyRadioButton_text_color_checked, R.color.white);
			bg_color_normal = a.getResourceId(R.styleable.MyRadioButton_bg_color_normal, R.color.white);
			bg_color_checked = a.getResourceId(R.styleable.MyRadioButton_bg_color_checked, R.color.white);
			// have_notify = a.getBoolean(R.styleable.MyRadioButton_have_notify, false);
		} finally {
			a.recycle();
		}
		initView(context);
	}
	private void initView(Context context) {
		View view = inflate(context, R.layout.tab_rb_layout, this);
		rb_container = (RelativeLayout) findViewById(R.id.rb_container);
		image = (ImageView) findViewById(R.id.image);
		title = (TextView) findViewById(R.id.title);
		// notify = (TextView) findViewById(R.id.notify);
//		rb_container.setOnClickListener(listener);
		Utils.ShowBadgeView(mContext,3,rb_container.findViewById(R.id.rl),5);
		this.setOnClickListener(listener);
		image.setImageResource(img_normal);
		title.setText(text);
	}

	public void check(boolean check) {
		if (check) {
//			rb_container.setBackgroundColor(mContext.getResources().getColor(bg_color_checked));
			image.setImageResource(img_checked);
			title.setTextColor(mContext.getResources().getColor(text_color_checked));
			isChecked = true;
		} else {
//			rb_container.setBackgroundColor(mContext.getResources().getColor(bg_color_normal));
			image.setImageResource(img_normal);
			title.setTextColor(mContext.getResources().getColor(text_color_normal));
			isChecked = false;
		}
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(!isChecked)
				((SellerNavigationBar) getParent()).setSelection(getId());
		}

	};

	public boolean isChecked() {
		return isChecked;
	}


	public void setmTitleState(int state) {
		if(title != null){
			if(state == View.GONE){
				title.setVisibility(View.GONE);
			}else if(state == View.VISIBLE){
				title.setVisibility(View.VISIBLE);
			}
		}

	}

	public void setmImageState(int state) {
		if(image != null){
			if(state == View.GONE){
				image.setVisibility(View.GONE);
			}else if(state == View.VISIBLE){
				image.setVisibility(View.VISIBLE);
			}
		}
	}
}
