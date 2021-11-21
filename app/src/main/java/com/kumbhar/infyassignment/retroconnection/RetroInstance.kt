package com.kumbhar.infyassignment.retroconnection

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {

        private const val baseURL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

}