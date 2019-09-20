package com.hanglinpai.ui;

import android.view.LayoutInflater;
import android.view.View;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.hanglinpai.R;

import butterknife.Bind;
import www.meiyaoni.com.common.base.BaseActivity;

public class TestActivity extends BaseActivity {
    @Bind(R.id.pullZoomView)
    PullToZoomScrollViewEx pullZoomView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_special_detail;
    }

    @Override
    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        View headView = LayoutInflater.from(mContext).inflate(R.layout.layout_zoom_heard, null);
        View zoomView = LayoutInflater.from(mContext).inflate(R.layout.layout_zoom_view, null);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_zoom_content, null);

        /**
         * 我们只要注意PullToZoomScrollViewEx其中的三个方法：
         setZoomView（View view）我们下拉的背景，
         setScrollContentView（View view）这个是我们填充的内容，
         setHeaderView（View view）这个是和下拉背景在一起的导航头，不过这个是没有动画效果的，我们可以把它做成登录，注册。
         * */

        pullZoomView.setHeaderView(headView);
        pullZoomView.setScrollContentView(contentView);
        pullZoomView.setZoomView(zoomView);


    }


}
