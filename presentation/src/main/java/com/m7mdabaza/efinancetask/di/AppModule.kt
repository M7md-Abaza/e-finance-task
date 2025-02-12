package com.m7mdabaza.efinancetask.di

import com.m7mdabaza.efinancetask.utils.Constants.BASE_URL
import com.m7mdabaza.data.datasource.PaymentRemoteDataSource
import com.m7mdabaza.data.datasource.PaymentServices
import com.m7mdabaza.data.repository.PaymentRepositoryImpl
import com.m7mdabaza.domain.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private const val TIMEOUT = 30L

    @Provides
    @Singleton
    fun providesDispatchersIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun providesOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().apply {
            /*if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }*/
            addInterceptor(httpLoggingInterceptor)
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    @Provides
    @Singleton
    fun provideMoviesServices(retrofit: Retrofit): PaymentServices {
        return retrofit.create(PaymentServices::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesRemoteDataSource: PaymentRemoteDataSource): PaymentRepository {
        return PaymentRepositoryImpl(moviesRemoteDataSource)
    }
}