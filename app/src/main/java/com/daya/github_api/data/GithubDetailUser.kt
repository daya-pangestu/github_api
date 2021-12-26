package com.daya.github_api.data

import com.daya.github_api.NullableStringField
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GithubDetailUser(
    val avatar_url: String,
    @NullableStringField
    val bio: String,
    val blog: String,
    @NullableStringField
    val company: String,
    @NullableStringField
    val email: String,
    val followers: Int,
    val following: Int,
    val gravatar_id: String,
    val id: Int,
    @NullableStringField
    val location: String,
    val login: String,
    @NullableStringField
    val name: String,
    val public_repos: Int,
    val repos_url: String,
    @NullableStringField
    val twitter_username: String,
)