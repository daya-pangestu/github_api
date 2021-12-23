package com.daya.trawlbens_test_github_api.di

import com.daya.trawlbens_test_github_api.data.GithubSearchResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "token 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")
                    .build()
                chain.proceed(request)
            }
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit
        .Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : GithubUserApiService =
         retrofit.create(GithubUserApiService::class.java)

}

interface GithubUserApiService {

    @GET("/search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("per_page")limit : Int = 10,
        @Query("page") page : Int = 1
    ): Call<GithubSearchResult>
    
}