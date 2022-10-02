package com.sourish.openstockapp.data.repo

import android.app.RemoteAction
import android.os.RemoteException
import com.sourish.openstockapp.data.csv.CSVParser
import com.sourish.openstockapp.data.local.IPODatabase
import com.sourish.openstockapp.data.local.StockListDao
import com.sourish.openstockapp.data.mappers.toIPOListingEntity
import com.sourish.openstockapp.data.mappers.toIPOListingModel
import com.sourish.openstockapp.data.remote.StockListAPI
import com.sourish.openstockapp.domain.model.IPOListingModel
import com.sourish.openstockapp.domain.repo.IPORepository
import com.sourish.openstockapp.utils.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IPORepositoryImpl @Inject constructor(
    val api: StockListAPI,
    val db: IPODatabase,
    val parser: CSVParser<IPOListingModel>
): IPORepository{

    private val dao = db.dao


    override suspend fun getIPOListings(
        query: String,
        fetchRemote: Boolean
    ): Flow<ResultType<List<IPOListingModel>>> {
        return flow {
            emit(ResultType.Loading(true))

            val localListings = dao.searchIPOListings(query)
            emit(ResultType.Success(localListings.map { it.toIPOListingModel() }))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val loadOnlyFromCache = !isDbEmpty && !fetchRemote

            if(loadOnlyFromCache){
                emit(ResultType.Loading(false))
                return@flow
            }

            val remoteResult = try {
                val response = api.getStockList()
                parser.parse(response.byteStream())
            }catch (e:IOException){
                e.printStackTrace()
                emit(ResultType.Error(message = "Couldn't load data"))
                null
            }catch (e:HttpException){
                e.printStackTrace()
                emit(ResultType.Error(message = "Cant fetch from API"))
                null
            }

            remoteResult?.let { listings ->
                dao.clearIPOListings()
                dao.insertIPOListings(listings.map { it.toIPOListingEntity() })

                emit(ResultType.Success(listings))
                emit(ResultType.Loading(false))
            }
        }
    }

}