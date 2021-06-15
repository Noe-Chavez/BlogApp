package mx.com.disoftware.blogapp.domain.auth

import com.google.firebase.auth.FirebaseUser
import mx.com.disoftware.blogapp.data.remote.auth.LoginDataSource

class LoginRepoImpl(private val dataSource: LoginDataSource) : LoginRepo {

    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)

}