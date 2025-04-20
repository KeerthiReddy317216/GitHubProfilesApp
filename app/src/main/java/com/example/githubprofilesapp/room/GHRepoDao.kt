package com.example.githubprofilesapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubprofilesapp.model.GHRepo

@Dao
interface GHRepoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllGitRepos(list:List<GHRepo>)

    @Query("SELECT * FROM GHRepo")
    fun getAllGitRepos(): List<GHRepo>
}