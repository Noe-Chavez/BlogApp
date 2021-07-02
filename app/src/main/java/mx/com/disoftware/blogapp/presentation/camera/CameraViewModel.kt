package mx.com.disoftware.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.domain.auth.AuthRepo
import mx.com.disoftware.blogapp.domain.camera.CameraRepo
import mx.com.disoftware.blogapp.presentation.auth.AuthViewModel

class CameraViewModel(private val repo: CameraRepo) : ViewModel() {
    fun upLoadPhoto(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.uploadPhoto(imageBitmap, description)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class CameraViewModelFactory(private val repo: CameraRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(CameraRepo: Class<T>): T {
        return CameraViewModel(repo) as T
    }
}