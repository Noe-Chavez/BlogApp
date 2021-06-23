package mx.com.disoftware.blogapp.ui.auth

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.data.remote.auth.AuthDataSource
import mx.com.disoftware.blogapp.databinding.FragmentRegisterBinding
import mx.com.disoftware.blogapp.domain.auth.AuthRepoImpl
import mx.com.disoftware.blogapp.presentation.auth.AuthViewModel
import mx.com.disoftware.blogapp.presentation.auth.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImpl(AuthDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp();
    }

    private fun signUp() {
        binding.btnSignUp.setOnClickListener {

            val userName = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()

            if (validateUserData(
                    userName,
                    email,
                    password,
                    confirmPassword
                )
            ) return@setOnClickListener

            createUser(email, password, userName)

        }
    }

    private fun createUser(email: String, password: String, userName: String) {
        viewModel.signUp(email, password, userName).observe(viewLifecycleOwner, { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.btnSignUp.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Result.Failure -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(requireContext(), "ERROR: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateUserData(
        userName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (userName.isEmpty()) {
            binding.editTextUsername.error = "this field cannot be empty"
            return true
        }

        if (email.isEmpty()) {
            binding.editTextEmail.error = "this field cannot be empty"
            return true
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "this field cannot be empty"
            return true
        }

        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "this field cannot be empty"
            return true
        }

        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Password does not march";
            binding.editTextPassword.error = "Password does not march";
            return true // finaliza la ejcución hasta donde se llama esta acción, es decir al botón.
        }
        return false
    }

}