package com.sourish.openstockapp.views.ipo_listings

import com.sourish.openstockapp.domain.model.IPOListingModel

data class IPOListingsState(
    val IPOList: List<IPOListingModel> = emptyList(),
    val isLoading: Boolean = false,
    val isRefresing: Boolean = false,
    val searchQuery: String = ""
)
