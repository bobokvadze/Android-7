package ge.bobokvadze.usersapp

import coil.load
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import coil.transform.Transformation
import ge.bobokvadze.usersapp.model.Data
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.transform.RoundedCornersTransformation
import ge.bobokvadze.usersapp.databinding.UserItemBinding

class UsersAdapter(
    private val listeners: Listeners
) : ListAdapter<Data, UsersViewHolder>(UsersDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UsersViewHolder(
        UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ), listeners
    )

    override fun onBindViewHolder(
        holder: UsersViewHolder, position: Int
    ) = holder.bind(getItem(position))
}

class UsersViewHolder(
    private val binding: UserItemBinding,
    private val listeners: Listeners,
    private val loadImage: LoadImage = LoadImage.BaseLauncher()
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Data) = with(binding) {
        tvEmail.text = item.email
        tvName.text = item.firstName
        loadImage.load(userImage, item.avatar)

        root.setOnClickListener {
            listeners.onClick(item)
        }
    }
}

class UsersDiffCallBack : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Data, newItem: Data) = oldItem == newItem
}

interface Listeners {
    fun onClick(item: Data)
}

interface LoadImage {

    fun load(imageView: ImageView, url: String?)

    abstract class Abstract(
        private val placeHolder: Int = R.drawable.ic_launcher_background,
        private val allowCrossFade: Boolean = true,
        private val crossFadeDuration: Int = 500,
        private val transformations: Transformation = RoundedCornersTransformation(10f)
    ) : LoadImage {

        override fun load(imageView: ImageView, url: String?) {
            imageView.load(url) {
                placeholder(placeHolder)
                crossfade(allowCrossFade)
                crossfade(crossFadeDuration)
                transformations(transformations)
            }
        }
    }

    class BaseLauncher : Abstract(
        placeHolder = R.drawable.ic_launcher_background,
        crossFadeDuration = 700
    )
}