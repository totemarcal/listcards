package com.example.listcards.helper.network

import com.example.listcards.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = BuildConfig.URL_RESTFUL

class RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient
                .Builder()
                .addInterceptor(logInterceptor())
                .connectTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    fun <API> createService(apiClass: Class<API>): API {
        return retrofit.create(apiClass)
    }

    fun logInterceptor() =
         HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}