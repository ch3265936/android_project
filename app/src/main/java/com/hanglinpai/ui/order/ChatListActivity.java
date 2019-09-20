package com.hanglinpai.ui.order;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hanglinpai.EventBus.UMPUSH;
import com.hanglinpai.R;
import com.hanglinpai.ui.bean.AddChatBean;
import com.hanglinpai.ui.bean.ChatDetail;
import com.hanglinpai.ui.bean.ChatListBean;
import com.hanglinpai.ui.bean.Expert;
import com.hanglinpai.ui.order.adapter.MyChatLogListAdapter;
import com.hanglinpai.ui.order.constract.ChatConstract;
import com.hanglinpai.ui.order.constract.SpecialConstract;
import com.hanglinpai.ui.order.model.ChatModel;
import com.hanglinpai.ui.order.model.SpecialModel;
import com.hanglinpai.ui.order.presenter.ChatPresenter;
import com.hanglinpai.ui.order.presenter.SpecialPresenter;
import com.hanglinpai.util.DensityUtil;
import com.hanglinpai.util.IEditTextChangeListener;
import com.hanglinpai.util.SoftInputUtil;
import com.hanglinpai.util.SystemUtils;
import com.hanglinpai.util.ThreadPool;
import com.hanglinpai.util.WorksSizeCheckUtil;
import com.hanglinpai.widget.Divider;
import com.hanglinpai.widget.FullyLinearLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutBottomItemAnimator;
import retrofit2.http.Body;
import www.meiyaoni.com.common.base.BaseActivity;

public class ChatListActivity extends BaseActivity<ChatPresenter, ChatModel> implements ChatConstract.View {
    @Bind(R.id.r_top)
    FrameLayout r_top;
    @Bind(R.id.title_title)
    TextView title_title;
    @Bind(R.id.title_back)
    TextView title_back;
    @Bind(R.id.change_content)
    Button change_content;
    @Bind(R.id.r_edit_content)
    RelativeLayout r_edit_content;
    @Bind(R.id.edit_content)
    EditText edit_content;
    @Bind(R.id.r_msg)
    RelativeLayout r_msg;
    @Bind(R.id.input_text)
    TextView input_text;
    @Bind(R.id.send)
    Button send;
    private MyChatLogListAdapter adapter;
    @Bind(R.id.recycleView)
    RecyclerView recycleView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.v_none)
    View v_none;
    private FullyLinearLayoutManager mLayoutManager;
    private String expertId = "";
    private List<ChatDetail> data = new ArrayList<>();
    private String type = "2";//下
    private String old_type = "1";//1
    private String order_id = "";
    private String chat_id_top = "";
    public String chat_id = "";
    private boolean onDestroy = false;//

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_list;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    private final Handler handler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<ChatListActivity> mActivity;

        public MyHandler(ChatListActivity activity) {
            mActivity = new WeakReference<ChatListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() == null) {
                return;
            }
            if (mActivity.get().data.size() > 0) {
                mActivity.get().chat_id = mActivity.get().data.get(mActivity.get().data.size() - 1).getId() + "";
            }
            mActivity.get().mPresenter.ChatList(mActivity.get().order_id, mActivity.get().type, mActivity.get().chat_id);//传入最新一条chat_id
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UMPUSH(UMPUSH umpush) {
        if (umpush.getMessage().equals("2")&&umpush.getOrder_id()!=null&&umpush.getOrder_id().equals(order_id)) {// 当前订单有新消息
            if (data.size() > 0) {//  获取最新的聊天数据
                chat_id = data.get(data.size() - 1).getId() + "";
                mPresenter.ChatList(order_id, type, chat_id);
            }else{//没有任何聊天记录
                mPresenter.ChatListOld(order_id, old_type, chat_id_top);//  获取当前存在的聊天数据
            }

        }
    }

    @Override
    public void initView() {
        if (getIntent().getStringExtra("orderId") != null) {
            order_id = getIntent().getStringExtra("orderId");
        }
        title_title.setText(R.string.online_chat);
        StatusBarTranslucent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            r_top.setPadding(0, DensityUtil.getStatusHeight(mActivity), 0, 0);
        }
        setupRecyclerView();
        changeButton();
    }

    private void changeButton() {
        //控制按鈕
        WorksSizeCheckUtil.textChangeListener textChangeListener = new WorksSizeCheckUtil.textChangeListener(send);
        textChangeListener.addAllEditText(input_text);
        WorksSizeCheckUtil.setChangeListener(new IEditTextChangeListener() {
            @Override
            public void textChange(boolean isHasContent) {
                if (isHasContent) {
                    send.setBackground(getResources().getDrawable(R.drawable.bg_send));
                    send.setTextColor(getResources().getColor(R.color.white));
                    send.setEnabled(true);
                } else {
                    send.setBackground(getResources().getDrawable(R.drawable.bg_send_2));
                    send.setTextColor(getResources().getColor(R.color.secondColor));
                    send.setEnabled(false);
                }
            }
        });
    }

    @OnClick({R.id.title_back, R.id.r_msg, R.id.change_content, R.id.send, R.id.v_none})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.v_none:
                r_edit_content.setVisibility(View.GONE);
                break;
            case R.id.title_back:
                finish();
                break;
            case R.id.r_msg:
                r_edit_content.setVisibility(View.VISIBLE);
//                edit_content.requestFocus();
//                edit_content.setFocusableInTouchMode(true);
//                edit_content.setFocusable(true);
                SoftInputUtil.showInput(edit_content);
                break;
            case R.id.change_content:
                r_edit_content.setVisibility(View.GONE);
                input_text.setText(edit_content.getText().toString().trim());
                break;
            case R.id.send:
                SoftInputUtil.hideInput(edit_content, mActivity);
                if (order_id == null) {
                    return;
                }
                if (data.size() > 0 && data.get(data.size() - 1).getId() > 0) {
                    chat_id = data.get(data.size() - 1).getId() + "";
                }
                if (input_text.getText().toString().trim() != null) {
                    mPresenter.addChat(order_id, chat_id, input_text.getText().toString().trim());
                } else {

                }
                break;
            default:
                break;
        }

    }

    //
    private void setupRecyclerView() {
        //添加Divider
        recycleView.addItemDecoration(new Divider(
                //分割线宽1dp
                DensityUtil.dp2px(0),
                //分割线颜色#DDDDDD
                Color.parseColor("#f6f7f6"),
                false,
                //分割线左侧留出20dp的空白，不绘制
                DensityUtil.dp2px(0), 0, 0, 0));
        mLayoutManager = new FullyLinearLayoutManager(mActivity);
        recycleView.setLayoutManager(mLayoutManager);
        adapter = new MyChatLogListAdapter(mActivity);
        adapter.setList(data);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recycleView);
        recycleView.setItemAnimator(new SlideInOutBottomItemAnimator(recycleView));
        recycleView.setAdapter(animatorAdapter);

        refreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                if (refreshLayout != null) {
                    refreshLayout.setLoadmoreFinished(false);
                    refreshLayout.finishRefresh();
                    if (data.size() > 0 && data.get(0) != null && data.get(0).getId() > 0) {//获取最上  chat_id
                        chat_id_top = data.get(0).getId() + "";

                    } else {
                        chat_id_top = "";
                    }
                    mPresenter.ChatListOld(order_id, old_type, chat_id_top);//  获取历史数据
                }
            }

            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {


            }
        });
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.autoRefresh();
        ThreadPool.getInstance().run(new Runnable() {
            @Override
            public void run() {
                while (!onDestroy) {
                    try {
                        handler.sendEmptyMessage(0);
                        Thread.sleep(10 * 1000);
                    } catch (Exception e) {

                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        onDestroy = true;
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }


    @Override
    public void returnAddChatSuccess(ChatListBean.ListBean bean) {
        if (bean != null && bean.getList() != null && bean.getList().size() > 0) {

            if (data != null && data.size() > 0) {//避免同一时间获取相同服务器数据重复聊天内容
                for (int i = 0; i < bean.getList().size(); i++) {
                    if (bean.getList().get(i).getId() <= data.get(data.size() - 1).getId()) {
                        bean.getList().remove(i);
                    } else {
                        break;
                    }
                }

            }
            if (bean.getList().size() > 0) {
                data.addAll(bean.getList());
                adapter.notifyDataSetChanged();
                if (adapter != null && adapter.getItemCount() > 0) {
                    recycleView.scrollToPosition(adapter.getItemCount() - 1);
                }
            }

        }
        input_text.setText("");
        edit_content.setText("");
    }

    @Override
    public void returnChatListSuccess(ChatListBean.ListBean bean) {
        if (bean.getList() != null && bean.getList().size() > 0) {
            if (data != null && data.size() > 0) {//避免同一时间获取相同服务器数据重复聊天内容
                for (int i = 0; i < bean.getList().size(); i++) {
                    if (bean.getList().get(i).getId() <= data.get(data.size() - 1).getId()) {
                        bean.getList().remove(i);
                    } else {
                        break;
                    }
                }

            }
            if (bean.getList().size() > 0) {
                data.addAll(bean.getList());
                adapter.notifyDataSetChanged();
                if (adapter != null && adapter.getItemCount() > 0) {
                    recycleView.scrollToPosition(adapter.getItemCount() - 1);
                }
            }
        }

    }


    private List<ChatDetail> list = new ArrayList<>();
    private boolean firstOld = true;

    @Override
    public void returnChatOldListSuccess(ChatListBean.ListBean bean) {
        if (bean.getList() != null && bean.getList().size() > 0) {
            list.clear();
            list.addAll(bean.getList());
            Collections.reverse(list);
            data.addAll(0, list);
            adapter.notifyDataSetChanged();

        }
        if (firstOld && adapter.getItemCount() > 0) {
            firstOld = false;
            recycleView.scrollToPosition(adapter.getItemCount() - 1);
        }


    }
}
