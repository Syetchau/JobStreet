package com.example.remotejobapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.remotejobapp.model.FavouriteJob

@Dao
interface FavouriteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteJob(favouriteJob: FavouriteJob)

    @Query("SELECT * FROM fav_job ORDER BY id DESC")
    fun getAllFavouriteJob(): LiveData<List<FavouriteJob>>

    @Delete
    suspend fun deleteFavJob(favouriteJob: FavouriteJob)
}