package mx.com.disoftware.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.core.Resource
import mx.com.disoftware.blogapp.data.remote.auth.LoginDataSource
import mx.com.disoftware.blogapp.databinding.FragmentLoginBinding
import mx.com.disoftware.blogapp.domain.auth.LoginRepoImpl
import mx.com.disoftware.blogapp.presentation.auth.LoginScreenViewModel
import mx.com.disoftware.blogapp.presentation.auth.LoginScreenViewModelFacotry


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    // Se inicializa y toma el valor de getInstance (FirebaseAuth), se instancia hasta que se manda a llamar.
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<LoginScreenViewModel> { LoginScreenViewModelFacotry(LoginRepoImpl(
        LoginDataSource()
    )) }

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
        viewModel.signIn(email, password).observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
                is Resource.Failure -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

}