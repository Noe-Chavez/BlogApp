package mx.com.disoftware.blogapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mx.com.disoftware.blogapp.core.Resource
import mx.com.disoftware.blogapp.domain.auth.LoginRepo

class LoginScreenViewModel(private val repo: LoginRepo) : ViewModel() {

    fun signIn(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

// Para poder hacer uso del repo, ya que como la clase hereda de ViewModel, ésta no se permite que
// Se pasen parámetros, por lo que para ello es necesario especificar cómo es que se inyecta esa
// dependencia haciendo uso del patrón factory.
class LoginScreenViewModelFacotry(private val repo: LoginRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //return modelClass.getConstructor(LoginRepo::class.java).newInstance(repo)
        // Simplificando el return.
        return LoginScreenViewModel(repo) as T
    }
}