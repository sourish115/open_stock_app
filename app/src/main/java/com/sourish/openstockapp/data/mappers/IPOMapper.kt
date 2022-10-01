package com.sourish.openstockapp.data.mappers

import com.sourish.openstockapp.data.local.IPOListingEntity
import com.sourish.openstockapp.domain.model.IPOListingModel

fun IPOListingEntity.toIPOListingModel() : IPOListingModel {
    return IPOListingModel(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun IPOListingModel.toIPOListingEntity() : IPOListingEntity {
    return IPOListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}