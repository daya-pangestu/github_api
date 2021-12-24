package com.daya.trawlbens_test_github_api.data

import com.daya.trawlbens_test_github_api.di.GithubUserApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchUser(query: String) = callbackFlow<List<User>> {
       val client =  apiService.searchUsers(query)
        client.enqueue(object : Callback<GithubSearchResult> {
            override fun onResponse(
                call: Call<GithubSearchResult>,
                response: Response<GithubSearchResult>
            ) {
                val body = response.body()
                if (body == null) {
                    this@callbackFlow.close(NoSuchElementException("something happening in the succesfull response body"))
                    return
                }
                this@callbackFlow.trySend(body.items)

            }

            override fun onFailure(call: Call<GithubSearchResult>, t: Throwable) {
                this@callbackFlow.close(t)
            }
        })
        awaitClose {
            client.cancel()
        }
    }

    suspend fun completingData(incompleteData: List<User>): List<User> {
        return incompleteData
            .map {
                val detail = apiService.detailUser(it.login)
                it.bio = detail.bio
                it.followers = detail.followers
                it.following = detail.following
                it.email = detail.email
                it.twitterUserName = detail.twitter_username
                it.public_repo = detail.public_repos
                it.location = detail.location
                it
            }

    }
}
