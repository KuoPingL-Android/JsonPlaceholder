package studio.saladjam.jsonplaceholder.repository

import androidx.lifecycle.LiveData
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

interface Repository {
    fun getPhotos(): LiveData<List<DatabasePhoto>>
}