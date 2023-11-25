package com.example.movo.di

import com.example.movo.data.remote.MovieApi
import com.example.movo.data.repo.MovieRepository
import com.example.movo.utils.Utils
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
    fun getMovieRepository(movieApi: MovieApi)  = MovieRepository(movieApi)

    @Singleton
    @Provides
//    @Named("RetrofitMovieApi")
    fun getRetrofit()
          = Retrofit.Builder()
            .baseUrl(Utils.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .build()



    @Singleton
    @Provides
//    @Named("MovieApi")
    fun getMovieApi(
//        @Named("RetrofitMovieApi")
                    retrofit: Retrofit)  = retrofit.create(MovieApi::class.java)


//    @Singleton
//    @Provides
//    @Named("RetrofitMovieStreamingApi")
//    fun getRetrofitStreaming()
//            = Retrofit.Builder()
//        .baseUrl(Utils.STREAMING_BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
//        .build()

//    @Singleton
//    @Provides
//    @Named("MovieStreamingApi")
//    fun getMovieStreamingApi(@Named("RetrofitMovieStreamingApi")retrofit: Retrofit)  = retrofit.create(MovieStreamingAPI::class.java)

}