package com.hjq.demo.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.demo.R;
import com.hjq.demo.action.StatusAction;
import com.hjq.demo.app.AppFragment;
import com.hjq.demo.app.TitleBarFragment;
import com.hjq.demo.http.api.PersonalStatusListApi;
import com.hjq.demo.http.model.HttpListData;
import com.hjq.demo.ui.activity.AddHoteActivity;
import com.hjq.demo.ui.activity.AddPersonStatusActivity;
import com.hjq.demo.ui.activity.HomeActivity;
import com.hjq.demo.ui.adapter.PostsAdapter;
import com.hjq.demo.widget.StatusLayout;
import com.hjq.http.EasyHttp;
import com.hjq.http.model.ResponseClass;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import cn.hutool.core.thread.ThreadUtil;

/**
 *  个人动态Fragment
 * @author flightz-
 */
public final class PersonStatusFragment extends TitleBarFragment<HomeActivity> implements OnRefreshLoadMoreListener, StatusAction {

    private RecyclerView recyclerView;
    private PostsAdapter adapter;
    private HttpListData<PersonalStatusListApi.Bean> postList;

    private SmartRefreshLayout mRefreshLayout;

    private int pageNumber = 1;

    private int pageSize = 10;

    private StatusLayout statusLayout;

    public static PersonStatusFragment newInstance() {
        return new PersonStatusFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.personal_status_fragment;
    }

    @Override
    public StatusLayout getStatusLayout() {
        return statusLayout;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statusLayout = findViewById(R.id.hl_personal_layout);

        adapter = new PostsAdapter(getContext());
        recyclerView.setAdapter(adapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        // 下拉刷新列表
        mRefreshLayout.autoRefresh();
    }

    private HttpListData<PersonalStatusListApi.Bean> getPostList() throws ExecutionException, InterruptedException {

        Callable<HttpListData<PersonalStatusListApi.Bean>> callable = () -> {
            // 执行网络请求
            return EasyHttp.get(PersonStatusFragment.this)
                    .api(new PersonalStatusListApi().setPageNumber(pageNumber)
                            .setPageSize(pageSize))
                    .execute(new ResponseClass<HttpListData<PersonalStatusListApi.Bean>>() {});
        };
        // 获取异步执行结果
        return ThreadUtil.execAsync(callable).get();
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
                postList = getPostList();
                if (postList.getRows().size() == 0){

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            adapter.addData(postList.getRows());
            mRefreshLayout.finishLoadMore();
            pageNumber ++;
            adapter.setLastPage(postList.isLastPage());
            mRefreshLayout.setNoMoreData(adapter.isLastPage());
        }, 1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            try {
                pageNumber = 1;
                postList = getPostList();
                if (postList.getRows().size() == 0){
                    showEmpty();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            adapter.clearData();
            adapter.setData(postList.getRows());
            mRefreshLayout.finishRefresh();
        }, 1000);
    }

    @Override
    public void onRightClick(View view) {
        this.startActivityForResult(AddPersonStatusActivity.class, (resultCode, data) -> {
            if (resultCode == AddPersonStatusActivity.RESULT_OK) {
                mRefreshLayout.autoRefresh();
            }
        });
    }
}