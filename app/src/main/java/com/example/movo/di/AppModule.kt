package com.example.movo.di

import com.example.movo.data.remote.MovieApi
import com.example.movo.utils.Utils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun getRetrofit()
          = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



    @Singleton
    @Provides
    fun getMovieApi(retrofit: Retrofit)  = retrofit.create(MovieApi::class.java)


}