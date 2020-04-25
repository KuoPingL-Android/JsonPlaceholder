package studio.saladjam.jsonplaceholder.fragments

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_imagespage.*
import studio.saladjam.jsonplaceholder.JSONPlaceholderApplication
import studio.saladjam.jsonplaceholder.adapters.recyclerviews.PhotoListAdapter
import studio.saladjam.jsonplaceholder.databinding.FragmentImagespageBinding
import studio.saladjam.jsonplaceholder.viewmodels.ImagePageViewModel
import studio.saladjam.jsonplaceholder.viewmodels.factories.RepositoryViewModelFactory

class ImagesPageFragment :Fragment() {

    private lateinit var binding: FragmentImagespageBinding
    private val viewModel by lazy {
        ViewModelProvider(this,
            RepositoryViewModelFactory(JSONPlaceholderApplication.INSTANCE))
            .get(ImagePageViewModel::class.java)
    }
    private lateinit var adapter: PhotoListAdapter
    private val gridLayoutManager by lazy {
        GridLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagespageBinding.inflate(inflater)
        adapter = PhotoListAdapter(PhotoListAdapter.OnClickListener {
            // navigate to next page
            println(it)
        })

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = gridLayoutManager

        viewModel.photos.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}