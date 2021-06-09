package mx.com.disoftware.blogapp.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import mx.com.disoftware.blogapp.core.Resource
import mx.com.disoftware.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatestPosts(): Resource<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().document("posts").get().await()
    }
}