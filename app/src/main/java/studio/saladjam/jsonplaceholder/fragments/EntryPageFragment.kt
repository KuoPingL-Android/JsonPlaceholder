package studio.saladjam.jsonplaceholder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import studio.saladjam.jsonplaceholder.databinding.FragmentEntrypageBinding

class EntryPageFragment: Fragment() {

    private lateinit var binding: FragmentEntrypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntrypageBinding.inflate(inflater)

        binding.buttonRequestapi.setOnClickListener {

            fragmentManager?.beginTransaction()?.let {

            }
        }

        return binding.root
    }
}