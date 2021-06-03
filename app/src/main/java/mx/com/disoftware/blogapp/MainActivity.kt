package mx.com.disoftware.blogapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnTakePicture: Button

    private var resultLauncher = this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            uploadPicture(imageBitmap);
        }
    }

    private var REQUEST_IMAGE_CAPTURE = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        btnTakePicture = findViewById(R.id.btn_take_picture)

        // Creando instancia a firebase
        val db = FirebaseFirestore.getInstance()

        /* -------------- Información cada vez que se inicia la app ---------------------- */

        // Consultando informacion a Firebease
        db.collection("ciudades")
            .document("LA")
            .get()
            .addOnSuccessListener { document ->
                // se puede obtener tod0 el objeto meidante una conversion
                val ciudad = document.toObject(Ciudad::class.java)
                Log.d("Firebase", "DocumentSnapshot data: ${ciudad.toString()}")
                Log.d("Firebase", "DocumentSnapshot data: ${document.data}")
        }.addOnFailureListener { error ->
                Log.d("Firebase", "DocumentSnapshot data: ${error.toString()}")
        }

        // Escribiendo datos a Firebase
        db.collection("ciudades")
            .document("CDMX")
            .set(Ciudad(2500000, "Orange"))
            .addOnSuccessListener {
                Log.d("Firebase", "Ciudad guardada de manera correcta")
            }.addOnFailureListener { error ->
                Log.d("Firebase", "Error ${error.toString()}")
            }

        /* -------------- Información en tiempo real que se inicia la app ---------------------- */
        db.collection("ciudades")
            .document("LA")
            .addSnapshotListener { value, error ->
                value?.let { document ->
                    // se puede obtener tod0 el objeto meidante una conversion
                    val ciudad = document.toObject(Ciudad::class.java)
                    Log.d("Firebase", "DocumentSnapshot data mediante objeto: ${ciudad.toString()}")
                    Log.d("Firebase", "DocumentSnapshot data: ${document.data}")
                }
                error.let {
                    Log.d("Firebase", "Ha ocurrido un error: ${it.toString()}")
                }
            }

        btnTakePicture.setOnClickListener {
            //dispatchTakePictureIntent()
            openSomeActivityForResult()
        }

    }

    fun openSomeActivityForResult() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    // Para subir la foto a firestore de Firebase
    fun uploadPicture(bitmap: Bitmap) {
        // Obteniendo una referencia al almacenamiento Storage de Firebase
        val storageRef = FirebaseStorage.getInstance().reference
        // Nombre con el que se guardará la imagen
        val imageRef = storageRef.child("image.jpg")
        // Comprimir imagen
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)

        uploadTask.continueWithTask { task ->
            // Si ocurre un error al regresar la promesa
            if(!task.isSuccessful) {
                task.exception?.let { exception ->
                    throw exception
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downLoadUrl = task.result.toString()
                Log.d("Storage", "uploadPrictureUrl: $downLoadUrl")
            }
        }

    }

}

data class Ciudad(val population: Int = 0, val color:String = "")