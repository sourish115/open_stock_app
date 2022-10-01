package com.sourish.openstockapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IPOListingEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)
