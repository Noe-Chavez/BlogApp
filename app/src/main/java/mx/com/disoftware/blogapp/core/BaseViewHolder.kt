package mx.com.disoftware.blogapp.core

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Recibe cualquier viewholder y regresa con la vista del item el cual queremos acceder.
 */
abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}