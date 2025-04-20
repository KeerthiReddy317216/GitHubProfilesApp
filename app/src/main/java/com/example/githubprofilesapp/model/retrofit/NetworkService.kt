package com.example.githubprofilesapp.model.retrofit

import com.example.githubprofilesapp.model.GHProfiles
import retrofit2.Response

import retrofit2.http.GET

interface NetworkService {
    @GET("search/repositories?q=language:swift&sort=stars&order=desc")
    suspend fun getGHRepos(): Response<GHProfiles>
}