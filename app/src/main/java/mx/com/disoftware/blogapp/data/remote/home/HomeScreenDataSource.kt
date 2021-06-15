package mx.com.disoftware.blogapp.data.remote.home

import com.google.firebase.firestore.FirebaseFirestore
import mx.com.disoftware.blogapp.core.Resource
import mx.com.disoftware.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {
    suspend fun getLatestPosts(): Resource<List<Post>> {
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("posts").get().await()
        for (post in querySnapshot.documents) {
            // Tranformar de JSON (proveniente de FireBase) a objeto Post y lo agrega a la lista de post
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }

        }
        return Resource.Success(postList)
    }
}