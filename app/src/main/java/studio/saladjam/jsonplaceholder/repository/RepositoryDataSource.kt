package studio.saladjam.jsonplaceholder.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto
import studio.saladjam.jsonplaceholder.models.network.RemotePhoto
import studio.saladjam.jsonplaceholder.models.network.asLocalModel
import studio.saladjam.jsonplaceholder.repository.local.PhotoDatabase
import studio.saladjam.jsonplaceholder.repository.remote.PhotosNetworkService
import studio.saladjam.jsonplaceholder.repository.remote.PhotosService

class RepositoryDataSource(private val database: PhotoDatabase ) : Repository {

    val photos = database.photoDao.getPhotos()

    override suspend fun refreshPhotos() {
        withContext(Dispatchers.IO) {
            val result = PhotosNetworkService.getPhotos(PhotosNetworkService.BASE_URL)
            database.photoDao.insertAll(result.asLocalModel())
        }
    }
}