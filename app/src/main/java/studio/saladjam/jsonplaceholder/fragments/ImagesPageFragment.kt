package studio.saladjam.jsonplaceholder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import studio.saladjam.jsonplaceholder.databinding.FragmentImagespageBinding

class ImagesPageFragment :Fragment() {

    private lateinit var binding: FragmentImagespageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagespageBinding.inflate(inflater)
        return binding.root
    }
}