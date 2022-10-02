package com.sourish.openstockapp.domain.repo

import com.sourish.openstockapp.domain.model.IPOListingModel
import com.sourish.openstockapp.utils.ResultType
import kotlinx.coroutines.flow.Flow

interface IPORepository {

    suspend fun getIPOListings(
        query: String,
        fetchRemote: Boolean
    ): Flow<ResultType<List<IPOListingModel>>>
}