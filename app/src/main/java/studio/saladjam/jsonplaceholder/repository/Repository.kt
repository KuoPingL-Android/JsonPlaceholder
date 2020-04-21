package studio.saladjam.jsonplaceholder.repository

import androidx.lifecycle.LiveData
import studio.saladjam.jsonplaceholder.models.Photo

interface Repository {
    fun getPhotos(): LiveData<List<Photo>>
}