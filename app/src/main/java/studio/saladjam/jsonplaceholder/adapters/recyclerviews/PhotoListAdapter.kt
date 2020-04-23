package studio.saladjam.jsonplaceholder.adapters.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.saladjam.jsonplaceholder.databinding.ItemImageBinding
import studio.saladjam.jsonplaceholder.models.local.DatabasePhoto
import studio.saladjam.jsonplaceholder.utils.ImageLoader

class PhotoListAdapter(private val onClickListener: OnClickListener) : ListAdapter<DatabasePhoto, PhotoListAdapter.ImageViewHolder>(PhotoDiff) {
    companion object PhotoDiff: DiffUtil.ItemCallback<DatabasePhoto>() {
        override fun areItemsTheSame(oldItem: DatabasePhoto, newItem: DatabasePhoto): Boolean {
            return oldItem === newItem
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
        holder.itemView.setOnClickListener { onClickListener.onClick(photo) }
    }

    class ImageViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: DatabasePhoto) {
            ImageLoader.loadBitmap(photo.thumbnailUrl, binding.image)
            binding.textviewId.text = photo.id
            binding.textviewTitle.text = photo.title
        }
    }

    class OnClickListener(val clickListener: (photo: DatabasePhoto) -> Unit) {
        fun onClick(photo: DatabasePhoto) = clickListener(photo)
    }
}