package com.daya.trawlbens_test_github_api.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubSearchResult(
    val items: List<User>,
)


@JsonClass(generateAdapter = true)
data class User(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val score: Double,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String,

    //added info
    @Transient
    var bio: String = "",
    @Transient
    var followers: Int = 0,
    @Transient
    var following: Int = 0,
    @Transient
    var email: String = "",
    @Transient
    var twitterUserName: String = "",
    @Transient
    var public_repo :Int = 0,
    @Transient
    var location :String = "",

    @Transient
    var isExpanded :Boolean= false

)