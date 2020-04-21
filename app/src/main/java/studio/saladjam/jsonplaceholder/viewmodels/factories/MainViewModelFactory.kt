package studio.saladjam.jsonplaceholder.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.saladjam.jsonplaceholder.viewmodels.MainViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel()
                else ->
                    throw IllegalArgumentException("MainViewModelFactory: $modelClass no assignable")
            }
        } as T
    }

}