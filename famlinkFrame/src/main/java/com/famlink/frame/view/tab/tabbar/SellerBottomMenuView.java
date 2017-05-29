package com.famlink.frame.view.tab.tabbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.famlink.frame.R;


public class SellerBottomMenuView extends RelativeLayout implements View.OnClickListener{

	private ImageView live_head;
	private Context mContext;
	SellerTabRadioButton button1;
	SellerTabRadioButton button2;
	SellerTabRadioButton button3;
	SellerTabRadioButton button4;
	SellerTabRadioButton button5;

	public SellerBottomMenuView(Context context) {
		super(context);
		mContext = context;
	}

	public SellerBottomMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
//		live_head = (ImageView) findViewById(R.id.id_img_live_head);
//		live_head.setOnClickListener(this);
		button1 = (SellerTabRadioButton) findViewById(R.id.button_one);
		button2 = (SellerTabRadioButton)findViewById(R.id.button_two);
		button3 = (SellerTabRadioButton) findViewById(R.id.button_three);
		button4 = (SellerTabRadioButton) findViewById(R.id.button_four);
		button5 = (SellerTabRadioButton) findViewById(R.id.button_five);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.id_img_live_head:
//			Intent intent = new Intent();
//			intent.setClass(mContext, SendLivingActivity.class);
//			mContext.startActivity(intent);
//			break;
		default:
			break;
		}
	}

	/**
	 * 动态设置底部tab的个数
	 * @param fragmentCount
	 */
	public void setFragmentCount(int fragmentCount) {
		switch (fragmentCount){
			case 2:
				button3.setVisibility(View.GONE);
				button4.setVisibility(View.GONE);
				button5.setVisibility(View.GONE);
				break;
			case 3:
				button4.setVisibility(View.GONE);
				button5.setVisibility(View.GONE);
				break;
			case 4:
				button5.setVisibility(View.GONE);
				break;
		}

	}

	public void setmTitleState(int visible){
		if(visible == View.GONE){
			button1.setmTitleState(View.GONE);
			button2.setmTitleState(View.GONE);
			button3.setmTitleState(View.GONE);
			button4.setmTitleState(View.GONE);
			button5.setmTitleState(View.GONE);
		}else if(visible == View.VISIBLE){
			button1.setmTitleState(View.VISIBLE);
			button2.setmTitleState(View.VISIBLE);
			button3.setmTitleState(View.VISIBLE);
			button4.setmTitleState(View.VISIBLE);
			button5.setmTitleState(View.VISIBLE);

		}
	}

	public void setmImageState(int visible) {
		if(visible == View.GONE){
			button1.setmImageState(View.GONE);
			button2.setmImageState(View.GONE);
			button3.setmImageState(View.GONE);
			button4.setmImageState(View.GONE);
			button5.setmImageState(View.GONE);
		}else if(visible == View.VISIBLE){
			button1.setmImageState(View.VISIBLE);
			button2.setmImageState(View.VISIBLE);
			button3.setmImageState(View.VISIBLE);
			button4.setmImageState(View.VISIBLE);
			button5.setmImageState(View.VISIBLE);
		}

	}
}
