package com.bogsnebes.listfilms

import android.app.Application
import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val okHttpClient = OkHttpClient
        .Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .addInterceptor(baseInterceptor)
        .build()

    override fun onCreate() {
        super.onCreate()
        application = this

        retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext

        lateinit var retrofit: Retrofit

        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

        private val baseInterceptor: Interceptor = Interceptor { chain ->
            val newUrl = chain
                .request()
                .url
                .newBuilder()
                .build()

            val request = chain
                .request()
                .newBuilder()
                .addHeader("X-API-KEY", API_KEY)
                .url(newUrl)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }
}