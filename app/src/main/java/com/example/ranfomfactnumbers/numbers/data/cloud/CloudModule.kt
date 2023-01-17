package com.example.ranfomfactnumbers.numbers.data.cloud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface CloudModule {

    fun <T> service(clasz: Class<T>): T

    abstract class Abstract(
        private val url: String,
        private val loggingLevel: HttpLoggingInterceptor.Level,
    ) : CloudModule {

        override fun <T> service(clasz: Class<T>): T {
            val interceptor = HttpLoggingInterceptor().apply { setLevel(loggingLevel) }

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(clasz)
        }

    }

    class Debug : Abstract(BASE_URL_TEST, HttpLoggingInterceptor.Level.BODY)

    class Release : Abstract(BASE_URL_RELEASE, HttpLoggingInterceptor.Level.BODY)


    companion object {
        private const val BASE_URL_RELEASE = "http://numbersapi.com/"
        private const val BASE_URL_TEST = "http://numbersapi.com/"
    }

}