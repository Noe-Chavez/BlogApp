package mx.com.disoftware.blogapp.data.remote.camera

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import mx.com.disoftware.blogapp.data.model.Post
import java.io.ByteArrayOutputStream
import java.util.*

class CameraDataSource {

    suspend fun uploadPhoto(imageBitmap: Bitmap, description: String) {

        // obtener uid de usuario logeado.
        val user = FirebaseAuth.getInstance().currentUser
        // Generar un número único.
        val randomName = UUID.randomUUID().toString()
        // Creando una carpeta post dentro de una carptea que se nombra con el UID del usaurio.
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/post/$randomName")
        // Comprimiendo foto.
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        // Cargando foto a FireBase y obteniendo la ruta de la imagen de FireBase..
        var downloadUrl: String = ""
        withContext(Dispatchers.IO) { // se ejecuta en segundo plano.
            downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()
        }
        // Obteneiendo los posts
        user?.let { firebaseUser ->
            firebaseUser.displayName?.let { displayName ->
                FirebaseFirestore.getInstance().collection("posts").add(Post(
                    profile_name = displayName,
                    profile_picture = firebaseUser.photoUrl.toString(), // al hacer la conversión a String se verifica la nulabilidad.
                    post_image = downloadUrl,
                    post_description = description,
                    uid = user.uid
                ))
            }
        }
    }
}