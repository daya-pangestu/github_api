package com.daya.github_api.di

import com.daya.github_api.data.GithubDetailUser
import com.daya.github_api.data.GithubSearchResult
import com.daya.github_api.utils.NullAbleStringFieldAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
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
    fun provideMoshi() : Moshi = Moshi
        .Builder()
        .add(NullAbleStringFieldAdapter)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,moshi: Moshi) : Retrofit =
        Retrofit
        .Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
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
        @Query("per_page")limit : Int = 5,
        @Query("page") page : Int = 1
    ): Call<GithubSearchResult>

    @GET("/users/{userLogin}")
    suspend fun detailUser(
        @Path("userLogin") userLogin : String
    ): GithubDetailUser
}