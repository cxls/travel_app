package com.hjq.demo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.hjq.demo.BuildConfig;
import com.hjq.demo.R;
import com.hjq.demo.app.AppAdapter;
import com.hjq.demo.http.api.PersonalStatusAddApi;
import com.hjq.demo.http.api.PersonalStatusListApi;
import com.hjq.demo.http.api.UpdateImageApi;
import com.hjq.demo.http.glide.GlideApp;
import com.hjq.demo.http.model.HttpUplodaData;
import com.hjq.demo.ui.activity.ImageCropActivity;
import com.hjq.demo.ui.activity.ImageSelectActivity;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.model.FileContentResolver;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author flight
 * @date 2024/6/1
 */
public class ImagesAdapter extends AppAdapter<String> {
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_ADD = 1;
    private int maxImages = 9;

    private OnAddImageListener onAddImageListener;

    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public OnAddImageListener getOnAddImageListener() {
        return onAddImageListener;
    }

    public void setOnAddImageListener(OnAddImageListener onAddImageListener) {
        this.onAddImageListener = onAddImageListener;
    }

    public ImagesAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getData().size() && position < maxImages) {
            return TYPE_ADD;
        }
        return TYPE_IMAGE;
    }

    @Override
    public AppAdapter<?>.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_image, parent, false);
            return new AddImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public int getItemCount() {
        int count = getData().size();
        if (count < maxImages) {
            count += 1; // add extra one for add button
        }
        return count;
    }

    public final class ImageViewHolder extends AppAdapter<?>.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }

        @Override
        public void onBindView(int position) {
            GlideApp.with(imageView)
                    // 拼接图片地址
                    .load(BuildConfig.HOST_URL.substring(0, BuildConfig.HOST_URL.length() - 1) + getItem(position))
//                    .circleCrop()
                    .into(imageView);
        }
    }

    public final class AddImageViewHolder extends AppAdapter<?>.ViewHolder {
        public ImageView addImageView;
        public AddImageViewHolder(View view) {
            super(view);
            addImageView = view.findViewById(R.id.addImageView);

            addImageView.setOnClickListener(v -> {
                // 调用外部方法来处理图片选择
                if (onAddImageListener != null) {
                    onAddImageListener.onAddImage();
                }
            });
        }

        @Override
        public void onBindView(int position) {

        }
    }

    public interface OnAddImageListener {
        /**
         * 添加图片
         */
        void onAddImage();
    }
}
