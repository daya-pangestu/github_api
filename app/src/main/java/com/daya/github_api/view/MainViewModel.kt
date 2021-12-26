package com.daya.github_api.view

import androidx.lifecycle.*
import androidx.test.core.app.launchActivity
import com.daya.github_api.data.GithubRepository
import com.daya.github_api.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository
) : ViewModel() {

    val queryFlow = savedStateHandle.getLiveData<String>(KEY_QUERY)
        .switchMap {
            liveData {
                val incompleteData = repository.searchUser(it) //only contain name,avatar
                val completedData =  completingData(incompleteData)
                emit(completedData)
            }
        }

    fun setQuery(query: String) {
        savedStateHandle.set(KEY_QUERY,query)
    }

    suspend fun completingData(incompleteData: List<User>): List<User> {
        return repository.completingData(incompleteData)
    }

    companion object{
        private val KEY_QUERY = "search_key"
    }
}