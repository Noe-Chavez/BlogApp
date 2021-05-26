package mx.com.disoftware.blogapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnTakePicture: Button

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
            dispatchTakePictureIntent()
        }

    }

    private fun dispatchTakePictureIntent() {
        // Obtener la foto y validar que se tiene una app instalada para ello.
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No existe alguna aplicación para tomar fotos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

}

data class Ciudad(val population: Int = 0, val color:String = "")