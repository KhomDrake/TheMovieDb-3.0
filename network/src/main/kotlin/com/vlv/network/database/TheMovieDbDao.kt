package com.vlv.network.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType

@Dao
interface TheMovieDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Query("SELECT * FROM history WHERE type = :type")
    suspend fun historyByType(
        type: HistoryType
    ) : List<History>

}