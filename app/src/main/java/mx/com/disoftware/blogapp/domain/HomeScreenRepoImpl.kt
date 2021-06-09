package mx.com.disoftware.blogapp.domain

import mx.com.disoftware.blogapp.core.Resource
import mx.com.disoftware.blogapp.data.model.Post

class HomeScreenRepoImpl() : HomeScreenRepo {
    override suspend fun getLatestPosts(): Resource<List<Post>> {
        TODO("Not yet implemented")
    }
}