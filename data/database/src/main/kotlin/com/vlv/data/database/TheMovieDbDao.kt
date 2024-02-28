package com.vlv.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vlv.data.database.data.CountryEntity
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ImageEntity
import com.vlv.data.database.data.ItemType
import com.vlv.data.database.data.LanguageEntity

@Dao
interface TheMovieDbDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: History)

    @Delete
    suspend fun deleteHistory(history: History)

    @Query("SELECT * FROM history WHERE type = :type")
    fun historyByType(
        type: ItemType
    ) : LiveData<List<History>>

    @Query("SELECT * FROM history WHERE type = :type")
    suspend fun historyByTypeAsync(
        type: ItemType
    ) : List<History>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE type = :type order by id asc")
    suspend fun favoriteByType(
        type: ItemType
    ) : List<Favorite>

    @Query("SELECT * FROM favorite WHERE itemId = :itemId")
    suspend fun getFavorite(itemId: Int) : Favorite?

    @Query("SELECT * FROM imageentity")
    suspend fun getImages() : List<ImageEntity>

    @Query("DELETE FROM imageentity")
    suspend fun removeImages()

    @Query("SELECT * FROM countryentity ORDER BY countryentity.englishName")
    suspend fun getCountries() : List<CountryEntity>

    @Query("DELETE FROM countryentity")
    suspend fun removeCountries()

    @Query("SELECT * FROM languageentity ORDER BY languageentity.englishName")
    suspend fun getLanguages() : List<LanguageEntity>

    @Query("DELETE FROM languageentity")
    suspend fun removeLanguages()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(images: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguages(images: List<LanguageEntity>)


}