package com.example.githubprofilesapp.model.repository

import com.example.githubprofilesapp.model.GHRepo
import com.example.githubprofilesapp.model.retrofit.NetworkService
import com.example.githubprofilesapp.room.GHRepoDao
import com.example.githubprofilesapp.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GitProfileRepository(private val dao: GHRepoDao,
                               private val networkService: NetworkService
) {
    fun getRepositories(): Flow<ResultState<List<GHRepo>>> = flow {
        emit(ResultState.Loading)
        try {

                val storedList = withContext(Dispatchers.IO) {
                    dao.getAllGitRepos()
                }

                if (storedList.isNotEmpty()) {
                    emit(ResultState.Success(storedList))
                } else {
                    val response = networkService.getGHRepos()
                    if (response.isSuccessful && response.body()?.items != null) {
                        val repoList = response.body()!!.items
                        emit(ResultState.Success(repoList))

                        withContext(Dispatchers.IO) {
                            dao.insertAllGitRepos(repoList)
                        }
                    } else {
                        emit(ResultState.Error("Server error: ${response.code()}"))
                    }
                }

        } catch (e: Exception) {
            emit(ResultState.Error("Network error: ${e.localizedMessage ?: "Something went wrong"}"))
        }
    }
}
