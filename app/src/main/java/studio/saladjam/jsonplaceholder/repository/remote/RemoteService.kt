package studio.saladjam.jsonplaceholder.repository.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import studio.saladjam.jsonplaceholder.models.network.RemotePhoto
import studio.saladjam.jsonplaceholder.models.network.toRemotePhotos
import studio.saladjam.jsonplaceholder.utils.readTextAndClose
import studio.saladjam.jsonplaceholder.utils.toMutableList
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

interface PhotosService {
    suspend fun getPhotos(urlString: String): List<RemotePhoto>
}

object PhotosNetworkService: PhotosService {

    const val BASE_URL = "https://jsonplaceholder.typicode.com/photos"

    // without the withContext, this will cause
    // "inappropriate blocking method call" warning to occur

    override suspend fun getPhotos(urlString: String): List<RemotePhoto> {
        return  withContext(Dispatchers.IO) {
            val inputStream: InputStream
            val jsonString: String
            val jsonArray: JSONArray
            var list = listOf<RemotePhoto>()

            val url = URL(urlString)

            val conn = url.openConnection() as HttpsURLConnection

            try {
                // make GET request to the given URL
                conn.connect()
                // receive response as inputStream
                inputStream = conn.inputStream

                if (inputStream != null) {
                    jsonString = inputStream.readTextAndClose()
                    jsonArray = JSONArray(jsonString)
                    list = jsonArray.toRemotePhotos()
                } else {
                    // TODO: Log ERROR
                    println("inputStream=null")
                }
            } catch (e: IOException) {
                e.stackTrace
            } finally {
                try {
                    conn.disconnect()
                } catch (e: IOException) {
                    e.stackTrace
                }
            }

            return@withContext list
        }
    }
}