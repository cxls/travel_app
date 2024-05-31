package com.hjq.demo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.hjq.demo.BuildConfig;
import com.hjq.demo.R;
import com.hjq.demo.app.AppAdapter;
import com.hjq.demo.http.api.HotelListApi;
import com.hjq.demo.http.api.TravelGuideListApi;
import com.hjq.demo.http.glide.GlideApp;

/**
 * @author flight
 * @date 2024/5/25
 */
public class HotelRecyclerViewAdapter extends AppAdapter<HotelListApi.Bean> {
    public HotelRecyclerViewAdapter(Context context) {
        super(context);
    }


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_EMPTY = 1;

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
            return new EmptyViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.hotel_item_layout, parent, false);
            return new ViewHolder(view);
        }
    }

    private final class ViewHolder extends AppAdapter<?>.ViewHolder {

        ImageView imageView;
        TextView titleView;
        TextView priceView;

        TextView locationView;
        TextView descriptionView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            titleView = itemView.findViewById(R.id.item_title);
            locationView = itemView.findViewById(R.id.item_location);
            priceView = itemView.findViewById(R.id.item_price);
            descriptionView = itemView.findViewById(R.id.item_content);
        }

        @Override
        public void onBindView(int position) {
            if (getData() == null || getData().size() == 0){
                return;
            }
            HotelListApi.Bean item = getItem(position);
            if (item == null){
                return;
            }
//        holder.imageView.(item.getImageUrl());
            GlideApp.with(imageView)
                    // 拼接图片地址
                    .load(BuildConfig.HOST_URL.substring(0, BuildConfig.HOST_URL.length() - 1) + item.getImageUrl())
//                    .circleCrop()
                    .into(imageView);
            titleView.setText(item.getName());
            locationView.setText(item.getRegion());
            priceView.setText("￥ " + item.getPrice().toString());
            descriptionView.setText(item.getDescription());
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
