package com.famlink.frame.view.tab.tabbar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.famlink.frame.R;


/**
 * @author zhen.Li/2015-10-13
 */
public class SellerNavigationBar extends LinearLayout {

	private NavigationBarClickListener mNavigationBarClickListener;
	private SellerTabRadioButton[] rbs;
	private int mIndex = 0;

	public SellerNavigationBar(Context context) {
		super(context);
	}

	public SellerNavigationBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		initView();
		super.onFinishInflate();
	}

	private void initView() {
		rbs = new SellerTabRadioButton[5];
		rbs[0] = (SellerTabRadioButton) findViewById(R.id.button_one);
		rbs[1] = (SellerTabRadioButton) findViewById(R.id.button_two);
		rbs[2] = (SellerTabRadioButton) findViewById(R.id.button_three);
		rbs[3] = (SellerTabRadioButton) findViewById(R.id.button_four);
		rbs[4] = (SellerTabRadioButton) findViewById(R.id.button_five);

	}

	public void selectFristTab() {
		changeTab(rbs[0].getId());
		setSelection(rbs[0].getId());
	}


	public void setSelection(int id) {
		if (id != rbs[mIndex].getId()) {
			changeTab(id);
		}
		if (mNavigationBarClickListener != null)
			mNavigationBarClickListener.onNavigationBarClickListener(mIndex);
	}

	public void setNavigationBarClickListener(NavigationBarClickListener navigationBarClickListener) {
		this.mNavigationBarClickListener = navigationBarClickListener;

	}

	public interface NavigationBarClickListener {
		public void onNavigationBarClickListener(int itemId);
	}

	public void changeTab(int tabId) {
		for (int idx = 0; idx < rbs.length; idx++) {
			if (tabId == rbs[idx].getId()) {
				rbs[idx].check(true);
				mIndex = idx;
			} else {
				rbs[idx].check(false);
			}
		}
	}

}
