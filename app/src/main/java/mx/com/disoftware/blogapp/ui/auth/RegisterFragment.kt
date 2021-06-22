package mx.com.disoftware.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

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

            if (userName.isEmpty()) {
                binding.editTextUsername.error = "this field cannot be empty"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.editTextEmail.error = "this field cannot be empty"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.editTextPassword.error = "this field cannot be empty"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                binding.editTextConfirmPassword.error = "this field cannot be empty"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.editTextConfirmPassword.error = "Password does not march";
                binding.editTextPassword.error = "Password does not march";
                return@setOnClickListener // finaliza la ejcución hasta donde se llama esta acción, es decir al botón.
            }

            Log.d("SignUp", "$userName $password $email")
        }
    }

}