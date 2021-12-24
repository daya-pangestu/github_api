package com.daya.trawlbens_test_github_api.view

import androidx.lifecycle.*
import com.daya.trawlbens_test_github_api.data.GithubRepository
import com.daya.trawlbens_test_github_api.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
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
        .asFlow()
        .transform {
                val incompleteData = repository.searchUser(it) //only contain name,avatar
                emitAll(incompleteData)

                val completedData = incompleteData.map {
                    completingData(it)
                }
            emitAll(completedData)
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