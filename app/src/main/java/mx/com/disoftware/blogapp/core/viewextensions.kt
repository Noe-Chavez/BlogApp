package mx.com.disoftware.blogapp.core

import android.view.View

/**
         función de extención para establecer la visibilidad de una vista.
 **/
fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}