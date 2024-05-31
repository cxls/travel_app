package com.hjq.demo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hjq.demo.BuildConfig;
import com.hjq.demo.R;
import com.hjq.demo.app.AppAdapter;
import com.hjq.demo.http.api.PersonalStatusListApi;
import com.hjq.demo.http.glide.GlideApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  个人动态适配器
 * @author flightz-
 */
public final class PostsAdapter extends AppAdapter<PersonalStatusListApi.Bean> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_EMPTY = 1;

    public PostsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return getData() == null || getData().isEmpty() ? 1 : getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getData() == null || getData().isEmpty()) {
            return TYPE_EMPTY;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public AppAdapter<?>.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_EMPTY) {
            View view = inflater.inflate(R.layout.empty_view_layout, parent, false);
            return new PostsAdapter.EmptyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.personal_status_item, parent, false);
            return new PostsAdapter.ViewHolder(view);
        }
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        public TextView username, time, content;
        public RecyclerView imagesRecyclerView;
        public ImagesAdapter imagesAdapter;

        private ViewHolder(View view) {
            super(view);
            username = view.findViewById(R.id.username);
            time = view.findViewById(R.id.postTime);
            content = view.findViewById(R.id.postContent);
            imagesRecyclerView = view.findViewById(R.id.postImagesRecyclerView);
        }

        @Override
        public void onBindView(int position) {
            PersonalStatusListApi.Bean data = getItem(position);
            if (data == null){
                return;
            }


            username.setText(data.getUserId().toString());
            time.setText(data.getCreatedAt());
            content.setText(data.getContent());

            // 设置为网格布局，每行显示3列
            int numberOfColumns = 3;
            imagesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
            imagesAdapter = new ImagesAdapter(new ArrayList<>());
            imagesRecyclerView.setAdapter(imagesAdapter);
            imagesAdapter.setData(Arrays.asList(data.getImageUrls().split(",")));
        }
    }

    private static class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {
        private List<String> images;

        public ImagesAdapter(List<String> images) {
            this.images = images;
        }

        // 设置数据
        public void setData(List<String> images) {
            this.images = images;
            notifyDataSetChanged();
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.posts_image_item, parent, false);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
//            holder.imageView.setImageResource(images.get(position));
            GlideApp.with(holder.imageView)
                    // 拼接图片地址
                    .load(BuildConfig.HOST_URL.substring(0, BuildConfig.HOST_URL.length() - 1) + images.get(position))
//                    .circleCrop()
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        public static class ImageViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public ImageViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.postImage);
            }
        }

        public void setImages(List<String> images) {
            this.images = images;
            notifyDataSetChanged();
        }
    }

    private final class EmptyViewHolder extends AppAdapter<?>.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
            // 确保这个 ViewHolder 占满所有列
            StaggeredGridLayoutManager.LayoutParams layoutParams =
                    new StaggeredGridLayoutManager.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true); // 设置占满所有的跨度
            itemView.setLayoutParams(layoutParams);
        }
        @Override
        public void onBindView(int position) {

        }
    }
}