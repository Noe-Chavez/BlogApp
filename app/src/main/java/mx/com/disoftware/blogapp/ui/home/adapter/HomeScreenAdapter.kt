package mx.com.disoftware.blogapp.ui.home.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.com.disoftware.blogapp.core.BaseViewHolder
import mx.com.disoftware.blogapp.data.model.Post
import mx.com.disoftware.blogapp.databinding.PostItemViewBinding

/**
 * El * en ....r<BaseViewHolder<*>>() establce que se puede recibir cualquier tipo de ViewHolder.
 */
class HomeScreenAdapter(private val postList: List<Post>) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size

    private inner class HomeScreenViewHolder (
        val binding: PostItemViewBinding,
        val context: Context
        ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name

            // Si no recibimos una descripción entonces ocultamos el textview, de lo contrario se muestra.
            if (item.post_description.isEmpty())
                binding.postDescription.visibility = View.GONE
            else
                binding.postDescription.text = item.post_description

            binding.postTimestamp.text = "Hace 2 horas"
        }

    }

}