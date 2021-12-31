package com.daya.github_api.utils

import com.daya.github_api.data.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Resource<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters).let {
                    Resource.success(it)
                }
            }
        } catch (e: Exception) {
            Resource.error(e.message)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R
}