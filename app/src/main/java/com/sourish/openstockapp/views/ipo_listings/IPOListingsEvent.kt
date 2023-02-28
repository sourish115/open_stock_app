package com.sourish.openstockapp.views.ipo_listings

sealed class IPOListingsEvent {
    object Refresh: IPOListingsEvent()
    data class OnSearchQueryResult(val query: String): IPOListingsEvent()
}
