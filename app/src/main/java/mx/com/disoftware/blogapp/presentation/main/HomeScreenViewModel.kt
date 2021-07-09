package mx.com.disoftware.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.domain.home.HomeScreenRepo
import java.lang.Exception

class HomeScreenViewModel(private val repo: HomeScreenRepo) : ViewModel() {
    /**
     *  @viewModelScope, indica que las corrutinas que se ejecutan, viven mientras que el viewModel
     *  esté vivo, una vez que éste muere, las corrutinas también lo harán liverando recursos para
     *  que el sistema pueda utilizarlos.
     *
     *  @Dispatchers.Main indica que se ejecuten en el hilo principal de la aplicación.
     */
    fun fetchLatestPost() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
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
