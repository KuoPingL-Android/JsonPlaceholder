package studio.saladjam.jsonplaceholder.repository

import androidx.lifecycle.LiveData
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

class RepositoryDataSource(val local: Repository, val remote: Repository) : Repository {
    override fun getPhotos(): LiveData<List<DatabasePhoto>> {
        return local.getPhotos()
    }
}