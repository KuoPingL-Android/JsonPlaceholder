package studio.saladjam.jsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import studio.saladjam.jsonplaceholder.enums.Pages
import studio.saladjam.jsonplaceholder.fragments.EntryPageFragment
import studio.saladjam.jsonplaceholder.fragments.ImagesPageFragment
import studio.saladjam.jsonplaceholder.fragments.SelectImageFragment
import studio.saladjam.jsonplaceholder.viewmodels.MainViewModel
import studio.saladjam.jsonplaceholder.viewmodels.factories.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModelFactory = MainViewModelFactory()
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.currentPage.observe(this, Observer {
            it?.let {
                val params = ConstraintLayout
                    .LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT)
                when(it) {
                    Pages.REQUEST_API -> {
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.add(R.id.fragment_container, EntryPageFragment(), it.id)
                        transaction.commit()
                    }

                    Pages.DISPLAY_IMAGES -> {
                        val transaction = supportFragmentManager.beginTransaction()
                        val fragment = ImagesPageFragment()
                        transaction.replace(R.id.fragment_container, fragment, it.id)
                        transaction.addToBackStack(viewModel.previousPage.value?.id)
                        transaction.commit()
                    }

                    Pages.DISPLAY_SELECTED -> {
                        viewModel.selectedImage.value?.let {photo ->
                            val transaction = supportFragmentManager.beginTransaction()
                            val fragment = SelectImageFragment()
                            val bundle = Bundle().apply {
                                putParcelable(SelectImageFragment.KEY_PHOTO, photo)
                            }
                            fragment.arguments = bundle
                            transaction.replace(R.id.fragment_container, fragment, it.id)
                            transaction.addToBackStack(viewModel.previousPage.value?.id)
                            transaction.commit()
                            viewModel.doneNavigateToSelectedPhoto()

                        } ?: Toast.makeText(this, "Not Image selected", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
