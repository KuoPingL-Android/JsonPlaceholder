package studio.saladjam.jsonplaceholder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import studio.saladjam.jsonplaceholder.databinding.FragmentEntrypageBinding
import studio.saladjam.jsonplaceholder.enums.Pages
import studio.saladjam.jsonplaceholder.viewmodels.MainViewModel
import studio.saladjam.jsonplaceholder.viewmodels.factories.MainViewModelFactory

class EntryPageFragment: Fragment() {

    private lateinit var binding: FragmentEntrypageBinding
    private val mainViewModelFactory = MainViewModelFactory()
    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity(), mainViewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntrypageBinding.inflate(inflater)

        binding.buttonRequestapi.setOnClickListener {
            mainViewModel.navigateTo(Pages.DISPLAY_IMAGES)
        }

        return binding.root
    }
}