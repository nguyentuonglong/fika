package vn.com.corp.fika.network

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import vn.com.corp.fika.model.UserProfile
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("thailemeetai/mobile-assignment/db")
    fun getUserProfile(): Single<UserProfile>

    companion object {

        private const val DEFAULT_URL = "https://my-json-server.typicode.com/"

        private var apiService: ApiService? = null

        fun getInstance(): ApiService {
            return apiService
                ?: create()
        }

        private fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(DEFAULT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
            apiService = retrofit.create(ApiService::class.java)
            return apiService!!
        }
    }
}