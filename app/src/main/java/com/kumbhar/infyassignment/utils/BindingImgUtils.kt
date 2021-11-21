package com.kumbhar.infyassignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.kumbhar.infyassignment.R

@BindingAdapter("image")
fun loadImage(imageView: ImageView, imgUrl: String?) {
    if (imgUrl != null) {
        Glide.with(imageView).load(imgUrl).error(R.mipmap.newsicon).placeholder(R.mipmap.newsicon)
            .into(imageView)
    } else {
        imageView.setImageResource(R.mipmap.newsicon)
    }
}