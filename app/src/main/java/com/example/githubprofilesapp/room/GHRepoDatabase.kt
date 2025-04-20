package com.example.githubprofilesapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubprofilesapp.model.GHRepo

@Database(entities = [GHRepo::class], version = 1)
abstract class GHRepoDatabase : RoomDatabase(){
    abstract fun getDao():GHRepoDao
    companion object{
        @Volatile  private var INSTANCE : GHRepoDatabase? = null
        @Synchronized
        fun getInstance(ctx: Context): GHRepoDatabase {
            if(INSTANCE == null)
                INSTANCE = Room.databaseBuilder(ctx.applicationContext, GHRepoDatabase::class.java,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build()
            return INSTANCE!!

        }

    }
}