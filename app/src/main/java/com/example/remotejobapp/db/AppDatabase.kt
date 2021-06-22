package com.example.remotejobapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remotejobapp.model.FavouriteJob

@Database(entities = [FavouriteJob::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getFavouriteJobDao(): FavouriteJobDao

    companion object {
        @Volatile
        private var instance: AppDatabase?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?:  createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "fav_job_db"
        ).build()
    }
}