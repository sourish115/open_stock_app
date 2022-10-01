package com.sourish.openstockapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [IPOListingEntity::class],
    version = 1
)
abstract class IPODatabase: RoomDatabase() {
    abstract val dao: StockListDao
}