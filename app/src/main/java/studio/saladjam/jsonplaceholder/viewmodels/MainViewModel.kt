package studio.saladjam.jsonplaceholder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.saladjam.jsonplaceholder.enums.Pages
import studio.saladjam.jsonplaceholder.interfaces.ImageSelector
import studio.saladjam.jsonplaceholder.interfaces.MainNavigator
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto

/**
 * This class is used to navigate Fragments
 * */
class MainViewModel :ViewModel(), MainNavigator, ImageSelector {

    private val _currentPage = MutableLiveData<Pages>()
    val currentPage: LiveData<Pages>
        get() = _currentPage

    val _targetPage = MutableLiveData<Pages>().apply {
        value = Pages.REQUEST_API
    }
    val targetPage: LiveData<Pages>
        get() = _targetPage

    override fun navigateTo(page: Pages) {
        _targetPage.value = page
    }

    override fun doneNavigateToPage() {
        _currentPage.value = _targetPage.value
        _targetPage.value = null
    }

    private val _selectedImage = MutableLiveData<DatabasePhoto>()
    val selectedImage: LiveData<DatabasePhoto>
        get() = _selectedImage
    override fun selectImage(photo: DatabasePhoto) {
        _selectedImage.value = photo
        navigateTo(Pages.DISPLAY_SELECTED)
    }

    override fun doneNavigateToSelectedPhoto() {
        _selectedImage.value = null
        doneNavigateToPage()
    }

}