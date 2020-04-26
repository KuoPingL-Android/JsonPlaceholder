package studio.saladjam.jsonplaceholder.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import studio.saladjam.jsonplaceholder.JSONPlaceholderApplication
import studio.saladjam.jsonplaceholder.R
import studio.saladjam.jsonplaceholder.databinding.FragmentSelectedimagepageBinding
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto
import studio.saladjam.jsonplaceholder.utils.ImageLoader

class SelectImageFragment : Fragment() {

    companion object {
        const val KEY_PHOTO = "PHOTO"
    }

    private lateinit var binding: FragmentSelectedimagepageBinding
    private var selectedPhoto: DatabasePhoto? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectedimagepageBinding.inflate(inflater)

        (arguments?.get(KEY_PHOTO) as? DatabasePhoto)?.let {
            selectedPhoto = it
        }

        binding.imageSelected.post{
            selectedPhoto?.let {
                ImageLoader.loadBitmap(it.url,
                    binding.imageSelected.width,
                    binding.imageSelected.height) {bitmap ->
                    bitmap?.let {

                        binding.imageSelected.apply {
                            alpha = 0f
                            setImageBitmap(it)
                            animate().alpha(1f).duration = 500L
                        }
                    } ?: binding.imageSelected.setImageResource(R.drawable.image_placeholder)
                }
            }

        }

        binding.textviewSelectedId.text = JSONPlaceholderApplication.INSTANCE.getString(R.string.selected_photo_id, selectedPhoto?.id)
        binding.textviewSelectedTitle.text = JSONPlaceholderApplication.INSTANCE.getString(R.string.selected_photo_title, selectedPhoto?.title)
        return binding.root
    }
}