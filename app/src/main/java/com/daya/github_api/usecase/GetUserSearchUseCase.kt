package com.daya.github_api.usecase

import com.daya.github_api.data.GithubRepository
import com.daya.github_api.data.User
import com.daya.github_api.di.coroutine.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserSearchUseCase
@Inject
constructor(
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher,
    private val repo : GithubRepository
) : UseCase<String,List<User>>(coroutineDispatcher){

    override suspend fun execute(parameters: String): List<User> {
        val incompleteData = repo.searchUser(parameters) //only contain name,avatar
        return  repo.completingData(incompleteData)
    }
}