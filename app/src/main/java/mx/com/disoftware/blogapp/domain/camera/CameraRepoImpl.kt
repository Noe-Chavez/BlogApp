package mx.com.disoftware.blogapp.domain.camera

import android.graphics.Bitmap
import mx.com.disoftware.blogapp.data.remote.camera.CameraDataSource

class CameraRepoImpl(private val dataSource: CameraDataSource) : CameraRepo {
    override suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {
        dataSource.uploadPhoto(imageBitmap, description)
    }
}