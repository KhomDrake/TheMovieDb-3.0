package com.vlv.network.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vlv.network.database.data.Favorite
import com.vlv.network.database.data.FavoriteType
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType

@Dao
interface TheMovieDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("SELECT * FROM history WHERE type = :type")
    fun historyByType(
        type: HistoryType
    ) : LiveData<List<History>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE type = :type order by id asc")
    suspend fun favoriteByType(
        type: FavoriteType
    ) : List<Favorite>

    @Query("SELECT * FROM favorite WHERE itemId = :itemId")
    suspend fun getFavorite(itemId: Int) : Favorite?


}