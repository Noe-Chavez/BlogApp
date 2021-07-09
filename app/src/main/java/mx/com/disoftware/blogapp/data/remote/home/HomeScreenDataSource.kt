package mx.com.disoftware.blogapp.data.remote.home

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.data.model.Post
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeScreenDataSource {

    suspend fun getLatestPosts(): Result<List<Post>> {

        val postList = mutableListOf<Post>()

        // se ejecuta en segundo plano la corrunina.
        withContext(Dispatchers.IO) {
            val querySnapshot = FirebaseFirestore.getInstance()
                .collection("posts")
                .orderBy("created_at", Query.Direction.DESCENDING)
                .get().await()
            for (post in querySnapshot.documents) {
                // Tranformar de JSON (proveniente de FireBase) a objeto Post y lo agrega a la lista de post
                post.toObject(Post::class.java)?.let {
                    it.apply {
                        // Estimar cuánto tarda el servidor en recuperar la fecha y hora de éste.
                        created_at = post.getTimestamp("created_at", DocumentSnapshot.ServerTimestampBehavior.ESTIMATE)?.toDate()
                    }
                    postList.add(it)
                }

            }
        }

        return Result.Success(postList)
    }
}