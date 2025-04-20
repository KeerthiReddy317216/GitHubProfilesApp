package com.example.githubprofilesapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubprofilesapp.model.GHRepo
import com.example.githubprofilesapp.model.retrofit.RetrofitInstance
import com.example.githubprofilesapp.room.GHRepoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GHProfileViewModel : ViewModel() {
    private var storedList : List<GHRepo> = emptyList()
    fun getRepositories(mContext: Context): Flow<List<GHRepo>> = flow {
        val dao = GHRepoDatabase.getInstance(mContext).getDao()
        //Getting the stored repositories data if the list is empty then getting from the network call

        storedList = withContext(Dispatchers.IO){
            dao.getAllGitRepos()
        }
        if(storedList.isNotEmpty()){
            emit(storedList)
        }
        else {
            val response = RetrofitInstance.networkService.getGHRepos()

            if (response.isSuccessful && response.body()?.items != null) {
                emit(response.body()!!.items)
                viewModelScope.launch(Dispatchers.IO) {
                    dao.insertAllGitRepos(response.body()!!.items)
                }
            } else {
                emit(emptyList())
            }
        }
    }
}