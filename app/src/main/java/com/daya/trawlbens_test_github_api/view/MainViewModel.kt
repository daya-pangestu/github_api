package com.daya.trawlbens_test_github_api.view

import androidx.lifecycle.*
import com.daya.trawlbens_test_github_api.data.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: GithubRepository
) : ViewModel() {

    val queryLiveData = savedStateHandle.getLiveData<String>(KEY_QUERY,)
        .switchMap {
            liveData {
                emit(repository.searchUser(it))
            }
        }

    fun setQuery(query: String) {
        savedStateHandle.set(KEY_QUERY,query)

    }

    companion object{
        private val KEY_QUERY = "search_key"
    }
}