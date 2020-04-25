package studio.saladjam.jsonplaceholder.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto
import studio.saladjam.jsonplaceholder.repository.RepositoryDataSource
import studio.saladjam.jsonplaceholder.repository.local.PhotoDatabase.Companion.getDatabase

class ImagePageViewModel(application: Application) : AndroidViewModel(application) {
    private val photosRepository = RepositoryDataSource(getDatabase(application))

    private val _photos = photosRepository.photos
    val photos: LiveData<List<DatabasePhoto>>
        get() = _photos

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob +
            Dispatchers.Main)

    private val _scrolledPosition = MutableLiveData<Int>()
    val scrolledPosition : LiveData<Int>
        get() = _scrolledPosition

    fun setScrolledPosition(position: Int) {
        _scrolledPosition.value = position
    }

    init {
        refreshPhoto()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun refreshPhoto() {
        viewModelScope.launch {
            photosRepository.refreshPhotos()
        }
    }
}