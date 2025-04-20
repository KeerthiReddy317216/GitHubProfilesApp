package com.example.githubprofilesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GHRepo(
    @PrimaryKey
    val id: Int,
    val name:String,
    val html_url: String,
)