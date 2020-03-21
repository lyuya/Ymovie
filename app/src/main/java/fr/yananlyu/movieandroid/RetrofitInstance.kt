package fr.yananlyu.movieandroid

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import okhttp3.HttpUrl


class RetrofitInstance {
    companion object {

        private val BASE_URL = "https://api.themoviedb.org/3/"
        private lateinit var retrofit: Retrofit

        fun getInstance(): Retrofit {
            /*   val interceptor = HttpLoggingInterceptor()
               interceptor.level = HttpLoggingInterceptor.Level.BODY
               val httpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

               retrofit = Retrofit.Builder()
                   .baseUrl(BASE_URL)
                   .client(httpClient)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()

               return retrofit
           }*/

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient().newBuilder().addInterceptor(Interceptor { chain ->
                var request = chain.request()
                val url = request.url().newBuilder().addQueryParameter("api_key", "ca571d61521790487fdb62e791d40f66").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit
    }
}
}