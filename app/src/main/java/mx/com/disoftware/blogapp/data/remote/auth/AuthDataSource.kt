package mx.com.disoftware.blogapp.data.remote.auth

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import mx.com.disoftware.blogapp.data.model.User
import java.io.ByteArrayOutputStream

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        // Await se ocupa ya que es una llamada asíncrona al servidor de FireBase que valida las credenciales del usario...
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, username: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uidUser ->
            // Se crea una lista de usruarios en FireStorage con id del usuario que regresa al registarrse.
            FirebaseFirestore.getInstance().collection("users").document(uidUser).set(User(email, username, "Aqui_va_su_foto.png")).await()
        }
        return authResult.user
    }

    suspend fun updateUserProfile(imageBitmap: Bitmap, username: String) {
        // obtener uid de usuario logeado.
        val user = FirebaseAuth.getInstance().currentUser
        // Creando una carpeta con la foto del usuario usando su uid.
        val imageRef = FirebaseStorage.getInstance().reference.child("${user?.uid}/profile_picture")
        // Comprimiendo foto.
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        // Cargando foto a FireBase y obteniendo la ruta de la imagen de FireBase..
        val downloadUrl = imageRef.putBytes(baos.toByteArray()).await().storage.downloadUrl.await().toString()

        // Actualizar perfil
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .setPhotoUri(Uri.parse(downloadUrl))
            .build()
        user?.updateProfile(profileUpdates)?.await()
    }

}