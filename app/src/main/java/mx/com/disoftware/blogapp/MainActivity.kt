package mx.com.disoftware.blogapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

    }
}

data class Ciudad(val population: Int = 0, val color:String = "")