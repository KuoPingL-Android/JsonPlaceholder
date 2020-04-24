package studio.saladjam.jsonplaceholder.adapters.recyclerviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.saladjam.jsonplaceholder.JSONPlaceholderApplication
import studio.saladjam.jsonplaceholder.R
import studio.saladjam.jsonplaceholder.databinding.ItemImageBinding
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto
import studio.saladjam.jsonplaceholder.utils.ImageLoader

class PhotoListAdapter(private val onClickListener: OnClickListener) : ListAdapter<DatabasePhoto, PhotoListAdapter.ImageViewHolder>(PhotoDiff) {
    companion object PhotoDiff: DiffUtil.ItemCallback<DatabasePhoto>() {
        override fun areItemsTheSame(oldItem: DatabasePhoto, newItem: DatabasePhoto): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DatabasePhoto, newItem: DatabasePhoto): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val photo = getItem(position)
        holder.bind(photo)
        holder.itemView.setOnClickListener {
            if (!holder.isLoading) {
                onClickListener.onClick(photo)
            } else {
                Toast.makeText(JSONPlaceholderApplication.INSTANCE,
                    JSONPlaceholderApplication.INSTANCE.getText(R.string.hint_loading),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ImageViewHolder(private val binding: ItemImageBinding):
        RecyclerView.ViewHolder(binding.root) {

        val isLoading: Boolean
            get() = binding.prgressBarImage.visibility == View.VISIBLE

        fun bind(photo: DatabasePhoto) {
            binding.textviewId.text = photo.id
            binding.textviewTitle.text = JSONPlaceholderApplication.INSTANCE
                .getString(R.string.hint_loading)
            binding.prgressBarImage.visibility = View.VISIBLE

            binding.image.contentDescription = photo.thumbnailUrl
            binding.image.setImageResource(R.drawable.image_placeholder)
            ImageLoader.loadBitmap(photo.thumbnailUrl, binding.image)
            { bitmap ->
                if(photo.thumbnailUrl == binding.image.contentDescription) {
                    bitmap?.let {
                        binding.image.apply {
                            alpha = 0f
                            setImageBitmap(it)
                            animate()
                                .alpha(1f).duration = 500.toLong()

                        }
                        binding.textviewTitle.text = photo.id
                        binding.prgressBarImage.visibility = View.GONE
                    }
                }
            }
        }
    }

    class OnClickListener(val clickListener: (photo: DatabasePhoto) -> Unit) {
        fun onClick(photo: DatabasePhoto) = clickListener(photo)
    }
}