package com.famlink.frame.view.tab.tabfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.famlink.frame.R;
import com.famlink.frame.databinding.LayoutFragmentOneBinding;
import com.famlink.frame.util.ToastUtils;
import com.famlink.frame.view.fragment.BaseFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


/**
 * Created by Administrator on 2016/6/15.
 */
public class FragmentOne extends BaseFragment<LayoutFragmentOneBinding> implements View.OnClickListener {
//    @SuppressWarnings("unused")
//    @ViewInject(value = R.id.text1)
//    Button text1;
    private View view;
    private Button text1;

    @Override
    public void onBaseCreateView(View view, Bundle savedInstanceState) {
        this.view = view;
        text1 = (Button) view.findViewById(R.id.text1);
        text1.setOnClickListener(this);
        text1.setText("ssss");
        getBinding().setStr("DataBindingFragment测试");
    }
//    @Event(R.id.text1)
    @Override
    public void onClick(View view) {
        ToastUtils.showToast("ssssssss");
    }
    @Override
    public int setLayout() {
        return R.layout.layout_fragment_one;
    }

    @Override
    public void setInterfaceView() {


    }

    @Override
    public void setGenericNodataOrNonetwork() {

    }

    @Override
    protected void dataCallback(int result, Object obj) {

    }

}
