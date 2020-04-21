package studio.saladjam.jsonplaceholder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import studio.saladjam.jsonplaceholder.enums.Pages
import studio.saladjam.jsonplaceholder.fragments.RequestPageFragment
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
                        val fragment = RequestPageFragment()
                        fragment.view?.layoutParams = params
                        transaction.add(R.id.fragment_container, RequestPageFragment(), it.id)
                        transaction.commit()
                    }

                    Pages.DISPLAY_RESULT -> {

                    }

                    Pages.DISPLAY_SELECTED -> {

                    }
                }
            }
        })
    }
}
