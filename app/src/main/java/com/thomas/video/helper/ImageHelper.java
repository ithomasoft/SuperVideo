package com.thomas.video.helper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.thomas.video.R;

/**
 * @author Thomas
 * @date 2019/5/28
 * @updatelog
 */
public class ImageHelper {
    public static void displayImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);
        Glide.with(imageView.getContext())
                .load(url).apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void displayExtraImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .centerInside()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.NORMAL);
        Glide.with(imageView.getContext())
                .load(url).apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void displayContentImage(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.splash_logo)
                .priority(Priority.NORMAL);
        Glide.with(imageView.getContext())
                .load(url).apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
}
