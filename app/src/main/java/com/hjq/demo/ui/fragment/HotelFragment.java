package com.hjq.demo.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.base.BaseActivity;
import com.hjq.demo.R;
import com.hjq.demo.app.AppFragment;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.http.api.HotelListApi;
import com.hjq.demo.http.api.TravelGuideListApi;
import com.hjq.demo.http.model.HttpListData;
import com.hjq.demo.ui.activity.AddHoteActivity;
import com.hjq.demo.ui.activity.CopyActivity;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.activity.LoginActivity;
import com.hjq.demo.ui.adapter.HotelRecyclerViewAdapter;
import com.hjq.demo.ui.adapter.RecyclerViewAdapter;
import com.hjq.http.EasyHttp;
import com.hjq.http.model.ResponseClass;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.concurrent.Callable;

import cn.hutool.core.thread.ThreadUtil;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class HotelFragment extends TitleBarFragment<HomeActivity> implements OnRefreshLoadMoreListener {

    private RecyclerView recyclerView;
    private HotelRecyclerViewAdapter recyclerViewAdapter;

    private SmartRefreshLayout mRefreshLayout;

    private HttpListData<HotelListApi.Bean> itemList;

    private int pageNumber = 1;

    private int pageSize = 10;


    public static HotelFragment newInstance() {
        return new HotelFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.hotel_fragment;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recycler_view);

        mRefreshLayout = findViewById(R.id.rl_data_refresh);

        // 设置布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // 设置适配器
        recyclerViewAdapter = new HotelRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            try {
                itemList = getHotelList();
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

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            try {
                pageNumber = 1;
                itemList = getHotelList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            recyclerViewAdapter.clearData();
            recyclerViewAdapter.setData(itemList.getRows());
            mRefreshLayout.finishRefresh();
        }, 1000);
    }

    private HttpListData<HotelListApi.Bean> getHotelList() throws Exception {
        Callable<HttpListData<HotelListApi.Bean>> callable = new Callable<HttpListData<HotelListApi.Bean>>() {
            @Override
            public HttpListData<HotelListApi.Bean> call() throws Exception {
                // 执行网络请求
                return EasyHttp.get(HotelFragment.this)
                        .api(new HotelListApi()
                                .setPageNumber(pageNumber)
                                .setPageSize(pageSize))
                        .execute(new ResponseClass<HttpListData<HotelListApi.Bean>>() {});
            }
        };
        // 获取异步执行结果
        return ThreadUtil.execAsync(callable).get();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRightClick(View view) {
        this.startActivityForResult(AddHoteActivity.class, (resultCode, data) -> {
            if (resultCode == AddHoteActivity.RESULT_OK) {
                mRefreshLayout.autoRefresh();
            }
        });
    }
}