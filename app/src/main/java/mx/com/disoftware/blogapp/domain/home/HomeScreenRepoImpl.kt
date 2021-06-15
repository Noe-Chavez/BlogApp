package mx.com.disoftware.blogapp.domain.home

import mx.com.disoftware.blogapp.core.Resource
import mx.com.disoftware.blogapp.data.model.Post
import mx.com.disoftware.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource) : HomeScreenRepo {
    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()
}