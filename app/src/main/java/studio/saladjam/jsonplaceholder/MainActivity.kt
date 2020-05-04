package studio.saladjam.jsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import studio.saladjam.jsonplaceholder.databinding.ActivityMainBinding
import studio.saladjam.jsonplaceholder.databinding.ActivityMainBindingImpl
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

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.targetPage.observe(this, Observer {
            it?.let {
                setTitleFor(it.id)
                when(it) {
                    Pages.REQUEST_API -> {
                        val transaction = supportFragmentManager.beginTransaction()
                        transaction.add(R.id.fragment_container, EntryPageFragment(), it.id)
//                        transaction.setCustomAnimations()
//                        transaction.addToBackStack(it.id)
                        transaction.commit()
                        viewModel.doneNavigateToPage()
                    }

                    Pages.DISPLAY_IMAGES -> {
                        val transaction = supportFragmentManager.beginTransaction()
                        val fragment = ImagesPageFragment()
                        transaction.replace(R.id.fragment_container, fragment, it.id)
                        transaction.addToBackStack(it.id)
                        transaction.commit()
                        viewModel.doneNavigateToPage()
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
                            transaction.addToBackStack(it.id)
                            transaction.commit()
                            viewModel.doneNavigateToSelectedPhoto()

                        } ?: Toast.makeText(this, getString(R.string.hint_image_not_selected), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragments = supportFragmentManager.fragments
        fragments.elementAt(fragments.size - 1)?.let {
            setTitleFor(it.tag)
        }
    }

    private fun setTitleFor(pageID: String?) {
        binding.textviewTitle.text = pageID
    }
}
