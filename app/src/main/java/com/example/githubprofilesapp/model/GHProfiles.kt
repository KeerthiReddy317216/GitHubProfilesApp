package com.example.githubprofilesapp.model

data class GHProfiles(
    val incomplete_results: Boolean,
    val items: List<GHRepo>,
    val total_count: Int
)