package com.example.githubprofilesapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubprofilesapp.model.GHRepo
import com.example.githubprofilesapp.model.repository.GitProfileRepository
import com.example.githubprofilesapp.model.retrofit.RetrofitInstance
import com.example.githubprofilesapp.room.GHRepoDatabase
import com.example.githubprofilesapp.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GHProfileViewModel(application:Application) : AndroidViewModel(application) {
    private val repository: GitProfileRepository
    private val _repos = MutableStateFlow<ResultState<List<GHRepo>>>(ResultState.Loading)
    val repos: StateFlow<ResultState<List<GHRepo>>> = _repos
    init {
        val dao = GHRepoDatabase.getInstance(application).getDao()
        repository = GitProfileRepository(dao, RetrofitInstance.networkService)
    }
    fun getRepositories() {
        viewModelScope.launch {
            repository.getRepositories().collect {
                _repos.value = it
            }
        }

    }
}