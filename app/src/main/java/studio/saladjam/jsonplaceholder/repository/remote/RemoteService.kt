package studio.saladjam.jsonplaceholder.repository.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import studio.saladjam.jsonplaceholder.models.network.RemotePhoto
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface PhotosService {
    suspend fun getPhotos(urlString: String): List<RemotePhoto>
}

object PhotosNetwork: PhotosService {

    const val BASE_URL = "https://jsonplaceholder.typicode.com/photos"

    override suspend fun getPhotos(urlString: String): List<RemotePhoto> {
        return  withContext(Dispatchers.IO) {
            val inputStream: InputStream
            val jsonString: String
            var list = listOf<RemotePhoto>()

            val url = URL(urlString)
            // without the withContext, this will cause
            // "inappropriate blocking method call" warning to occur
            val conn = url.openConnection() as HttpsURLConnection

            // make GET request to the given URL
            conn.connect()

            // receive response as inputStream
            inputStream = conn.inputStream

            if (inputStream != null) {
                jsonString = inputStream.toString()
                println(jsonString)
            } else {
                // TODO: Log ERROR
            }

            return@withContext list
        }
    }
}