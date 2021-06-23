package mx.com.disoftware.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.domain.home.HomeScreenRepo
import java.lang.Exception

class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {
    /**
     *  Dispatchers.IO se ejecuta en un hilo separado al principal.
     */
    fun fetchLatestPost() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(repo.getLatestPosts())
        } catch (e: Exception) {
            // En caso de que falle val querySnapshot en la clase HomeScreenDataSource
            emit(Result.Failure(e))
        }
    }
}

/* *
*   Debido a que en el ViewModel, no se puede recibir nada por el cosntructor, amenos que se cree un Factory.
* */
class HomeScreenViewModelFactory(private val repo: HomeScreenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}
