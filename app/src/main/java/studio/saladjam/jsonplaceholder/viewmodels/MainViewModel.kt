package studio.saladjam.jsonplaceholder.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import studio.saladjam.jsonplaceholder.enums.Pages
import studio.saladjam.jsonplaceholder.interfaces.MainNavigator

/**
 * This class is used to navigate Fragments
 * */
class MainViewModel :ViewModel(), MainNavigator {

    private val _currentPage = MutableLiveData<Pages>().apply {
        value = Pages.REQUEST_API
    }
    val currentPage: LiveData<Pages>
        get() = _currentPage

    override fun navigateTo(page: Pages) {
        _currentPage.value = page
    }
}