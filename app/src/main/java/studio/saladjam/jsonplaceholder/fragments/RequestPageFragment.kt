package studio.saladjam.jsonplaceholder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import studio.saladjam.jsonplaceholder.databinding.FragmentRequestpageBinding

class RequestPageFragment: Fragment() {

    private lateinit var binding: FragmentRequestpageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestpageBinding.inflate(inflater)

        binding.buttonRequestapi.setOnClickListener {
            fragmentManager?.beginTransaction()
        }

        return binding.root
    }
}