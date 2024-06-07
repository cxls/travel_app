package com.hjq.demo.http.interceptor;

import static com.umeng.socialize.utils.ContextUtil.getContext;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.ui.activity.LoginActivity;
import com.hjq.gson.factory.GsonFactory;

import java.io.IOException;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author flight
 * @date 2024/5/26
 */
public class AuthInterceptor implements Interceptor {

    private static final String TAG = "AuthInterceptor";

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        // 检查响应码是否为401
        if (response.code() == 401) {
            // 这里处理跳转到登录界面的逻辑
            handleUnauthorized();
        }

        return response;
    }

    private void handleUnauthorized() {
        // 使用 Handler 在主线线程执行 UI 操作
        new Handler(Looper.getMainLooper()).post(() -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            getContext().startActivity(intent);
        });
    }
}
