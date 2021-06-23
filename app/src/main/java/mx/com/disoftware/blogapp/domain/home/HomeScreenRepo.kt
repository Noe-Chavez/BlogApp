package mx.com.disoftware.blogapp.domain.home

import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Result<List<Post>>
}