package com.sourish.openstockapp.views.ipo_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourish.openstockapp.domain.repo.IPORepository
import com.sourish.openstockapp.utils.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IPOListingsViewModel @Inject constructor(
    private val repo: IPORepository
): ViewModel(){
    var state by mutableStateOf(IPOListingsState())

    private var searchJob: Job? = null
    fun onEvent(event: IPOListingsEvent){
        when(event){
            is IPOListingsEvent.Refresh -> {
                getIPOListings(fetchRemote = true)
            }
            is IPOListingsEvent.OnSearchQueryResult -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getIPOListings()
                }
            }
        }
    }

    private fun getIPOListings(
        query: String = state.searchQuery.lowercase(),
        fetchRemote: Boolean = false
    ){
        viewModelScope.launch {
            repo
                .getIPOListings(query,fetchRemote)
                .collect { result ->
                    when(result) {
                        is ResultType.Success -> {
                            result.data?. let { listings ->
                                state = state.copy(
                                    IPOList = listings
                                )
                            }
                        }
                        is ResultType.Error -> Unit
                        is ResultType.Loading -> {
                            state=state.copy(isLoading = result.isLoading)
                        }
                    }

                }
        }
    }
}