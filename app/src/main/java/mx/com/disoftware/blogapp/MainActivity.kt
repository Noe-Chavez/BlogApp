package mx.com.disoftware.blogapp

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import mx.com.disoftware.blogapp.core.hide
import mx.com.disoftware.blogapp.core.show
import mx.com.disoftware.blogapp.databinding.ActivityMainBinding
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
         *  agregamos el navigation component (nav_graph) el BottomNavigationView para que el nav_grapth
         *  sepa qué inflar.
         */
        // Casteo a NavHostragment.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        // Nota se pueden evitar las loienas anteriores cambiando de androidx.fragment.app.FragmentContainerView afragment en el xml

        // Ocultar o mostrar la barra de navegación
        observeDestinationChange()

    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                // Ocultar el menú ya sea que se esté en el Login o en el registro
                R.id.loginFragment -> binding.bottomNavigationView.visibility = View.GONE
                R.id.registerFragment -> binding.bottomNavigationView.visibility = View.GONE
                // En cualquier otro caso a los anteriores, mostramos el menú de navegación
                else -> binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

}