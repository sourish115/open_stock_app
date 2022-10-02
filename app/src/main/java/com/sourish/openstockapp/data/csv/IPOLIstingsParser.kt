package com.sourish.openstockapp.data.csv

import com.opencsv.CSVReader
import com.sourish.openstockapp.domain.model.IPOListingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IPOLIstingsParser @Inject constructor(): CSVParser<IPOListingModel> {
    override suspend fun parse(stream: InputStream): List<IPOListingModel> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO){
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchnage = line.getOrNull(2)

                    IPOListingModel(name ?: return@mapNotNull null,symbol ?: return@mapNotNull null,exchnage ?: return@mapNotNull null)
                }
                .also {
                    csvReader.close()
                }
        }
    }
}