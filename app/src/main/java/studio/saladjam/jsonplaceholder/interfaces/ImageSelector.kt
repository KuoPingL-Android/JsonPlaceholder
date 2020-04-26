package studio.saladjam.jsonplaceholder.interfaces

import androidx.lifecycle.MutableLiveData
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

interface ImageSelector {
    fun selectImage(photo: DatabasePhoto)
    fun doneNavigateToSelectedPhoto()
}