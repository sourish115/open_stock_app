package com.sourish.openstockapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIPOListings(
        ipoLisitingEntities: List<IPOListingEntity>
    )

    @Query("DELETE FROM ipolistingentity")
    suspend fun clearIPOListings()

    @Query("""
        SELECT *
        FROM ipolistingentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
    """)
    suspend fun searchIPOListings(query: String): List<IPOListingEntity>
}