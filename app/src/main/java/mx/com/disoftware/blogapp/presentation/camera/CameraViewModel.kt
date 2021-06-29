package mx.com.disoftware.blogapp.presentation.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.domain.camera.CameraRepo

class CameraViewModel(private val repo: CameraRepo) : ViewModel() {
    fun upLoadPhoto(imageBitmap: Bitmap, description: String) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {

        } catch (e: Exception) {
            emit(Result.Failure())
        }
    }
}