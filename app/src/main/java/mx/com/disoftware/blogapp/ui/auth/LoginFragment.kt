package mx.com.disoftware.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    // Se inicializa y toma el valor de getInstance (FirebaseAuth), se instancia hasta que se manda a llamar.
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn();
        doLogin()

    }

    private fun isUserLoggedIn() {
        // let se ejecuta en caso de no recibir un nulo
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            // obtener cadena del los campos email y password al igual que se limpian de espacios
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password) // en caso de que se entre no se pasa a signin
            signIn(email, password)
        }
    }

    private fun validateCredentials(email: String, password: String) {
        if (email.isEmpty()) {
            binding.editTextEmail.error = "E-mail is empty"
            return // para salirse
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return // para salirse
        }

    }

    private fun signIn(email: String, password: String) {
        // Lógica de FareBase para poder iniciar sesión
    }

}