package com.daya.github_api.view

import androidx.lifecycle.*
import androidx.test.core.app.launchActivity
import com.daya.github_api.data.GithubRepository
import com.daya.github_api.data.Resource
import com.daya.github_api.data.User
import com.daya.github_api.usecase.GetUserSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserSearchUseCase: GetUserSearchUseCase
) : ViewModel() {

    val queryFlow = savedStateHandle.getLiveData<String>(KEY_QUERY)
        .switchMap {
            liveData {
                emit(Resource.loading())
                val data = getUserSearchUseCase(it)
                emit(data)
            }
        }

    fun setQuery(query: String) {
        savedStateHandle.set(KEY_QUERY,query)
    }

    companion object{
        private val KEY_QUERY = "search_key"
    }
}