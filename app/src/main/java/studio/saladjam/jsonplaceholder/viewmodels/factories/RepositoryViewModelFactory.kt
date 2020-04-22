package studio.saladjam.jsonplaceholder.viewmodels.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.saladjam.jsonplaceholder.viewmodels.ImagePageViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class RepositoryViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(ImagePageViewModel::class.java) -> ImagePageViewModel(application)
                else ->
                    throw IllegalArgumentException("modelClass: $modelClass is unknown to RepositoryViewModelFactory")
            }
        } as T
    }
}