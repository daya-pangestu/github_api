package com.daya.trawlbens_test_github_api.data

import com.daya.trawlbens_test_github_api.di.GithubUserApiService
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@Singleton
class GithubRepository
@Inject
constructor(
    private val apiService: GithubUserApiService
){

    suspend fun searchUser(query: String) = suspendCancellableCoroutine<GithubSearchResult> { continuation ->
       val client =  apiService.searchUsers(query)
        client.enqueue(object : Callback<GithubSearchResult> {
            override fun onResponse(
                call: Call<GithubSearchResult>,
                response: Response<GithubSearchResult>
            ) {
                val body = response.body() ?: return continuation.resumeWithException(
                    NoSuchElementException("${response.errorBody()?.string()}")
                )
                continuation.resume(body)
            }

            override fun onFailure(call: Call<GithubSearchResult>, t: Throwable)=
                continuation.resumeWithException(t)
        })
        continuation.invokeOnCancellation {
            client.cancel()
        }
    }

}
