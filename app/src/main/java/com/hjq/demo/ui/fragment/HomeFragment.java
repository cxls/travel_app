package com.hjq.demo.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.demo.R;
import com.hjq.demo.app.AppFragment;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.http.api.TravelGuideListApi;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.http.model.HttpListData;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.adapter.RecyclerViewAdapter;
import com.hjq.demo.ui.adapter.TabAdapter;
import com.hjq.demo.widget.XCollapsingToolbarLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.listener.OnHttpListener;
import com.hjq.http.model.ResponseClass;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import cn.hutool.core.thread.AsyncUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadUtil;
import okhttp3.Call;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 首页 Fragment
 */
public final class HomeFragment extends TitleBarFragment<HomeActivity>
        implements TabAdapter.OnTabListener, OnRefreshLoadMoreListener,
        XCollapsingToolbarLayout.OnScrimsListener {

    private XCollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;

    private TextView mAddressView;
    private TextView mHintView;
    private AppCompatImageView mSearchView;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private SmartRefreshLayout mRefreshLayout;

    private HttpListData<TravelGuideListApi.Bean> itemList;

    private int pageNumber = 1;

    private int pageSize = 10;

//    private RecyclerView mTabView;
//    private ViewPager mViewPager;

//    private TabAdapter mTabAdapter;
//    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        mCollapsingToolbarLayout = findViewById(R.id.ctl_home_bar);
        mToolbar = findViewById(R.id.tb_home_title);

        mAddressView = findViewById(R.id.tv_home_address);
        mHintView = findViewById(R.id.tv_home_hint);
        mSearchView = findViewById(R.id.iv_home_search);
        recyclerView = findViewById(R.id.recycler_view);

        mRefreshLayout = findViewById(R.id.rl_status_refresh);

        // 设置布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 设置适配器
        recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);

//        mTabView = findViewById(R.id.rv_home_tab);
//        mViewPager = findViewById(R.id.vp_home_pager);

//        mPagerAdapter = new FragmentPagerAdapter<>(this);
//        mPagerAdapter.addFragment(StatusFragment.newInstance(), "列表演示");
//        mPagerAdapter.addFragment(BrowserFragment.newInstance("https://github.com/getActivity"), "网页演示");
//        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.addOnPageChangeListener(this);

//        mTabAdapter = new TabAdapter(getAttachActivity());
//        mTabView.setAdapter(mTabAdapter);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(getAttachActivity(), mToolbar);

        //设置渐变监听
        mCollapsingToolbarLayout.setOnScrimsListener(this);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
//        mTabAdapter.addItem("列表演示");
//        mTabAdapter.addItem("网页演示");
//        mTabAdapter.setOnTabListener(this);
        mRefreshLayout.autoRefresh();

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public boolean isStatusBarDarkFont() {
        return mCollapsingToolbarLayout.isScrimsShown();
    }

    /**
     * {@link TabAdapter.OnTabListener}
     */

    @Override
    public boolean onTabSelected(RecyclerView recyclerView, int position) {
//        mViewPager.setCurrentItem(position);
        return true;
    }

    /**
     * CollapsingToolbarLayout 渐变回调
     *
     * {@link XCollapsingToolbarLayout.OnScrimsListener}
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void onScrimsStateChange(XCollapsingToolbarLayout layout, boolean shown) {
        getStatusBarConfig().statusBarDarkFont(shown).init();
        mAddressView.setTextColor(ContextCompat.getColor(getAttachActivity(), shown ? R.color.black : R.color.white));
        mHintView.setBackgroundResource(shown ? R.drawable.home_search_bar_gray_bg : R.drawable.home_search_bar_transparent_bg);
        mHintView.setTextColor(ContextCompat.getColor(getAttachActivity(), shown ? R.color.black60 : R.color.white60));
        mSearchView.setSupportImageTintList(ColorStateList.valueOf(getColor(shown ? R.color.common_icon_color : R.color.white)));
    }

    /**
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        postDelayed(() -> {
            try {
                itemList = getTravelGuideList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            recyclerViewAdapter.addData(itemList.getRows());
            mRefreshLayout.finishLoadMore();
            pageNumber ++;
            recyclerViewAdapter.setLastPage(itemList.isLastPage());
            mRefreshLayout.setNoMoreData(recyclerViewAdapter.isLastPage());
        }, 1000);

    }

    /**
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        postDelayed(() -> {
            try {
                pageNumber = 1;
                itemList = getTravelGuideList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            recyclerViewAdapter.clearData();
            recyclerViewAdapter.setData(itemList.getRows());
            mRefreshLayout.finishRefresh();
        }, 1000);
    }

    // 使用easyHttp 加载攻略数据（分页）
    private HttpListData<TravelGuideListApi.Bean> getTravelGuideList() throws Exception {
        Callable<HttpListData<TravelGuideListApi.Bean>> callable = new Callable<HttpListData<TravelGuideListApi.Bean>>() {
            @Override
            public HttpListData<TravelGuideListApi.Bean> call() throws Exception {
                // 执行网络请求
                return EasyHttp.get(HomeFragment.this)
                        .api(new TravelGuideListApi()
                                .setPageNumber(pageNumber)
                                .setPageSize(pageSize))
                        .execute(new ResponseClass<HttpListData<TravelGuideListApi.Bean>>() {});
            }
        };
        // 获取异步执行结果
        return ThreadUtil.execAsync(callable).get();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mViewPager.setAdapter(null);
//        mViewPager.removeOnPageChangeListener(this);
//        mTabAdapter.setOnTabListener(null);
    }
}