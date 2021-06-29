package mx.com.disoftware.blogapp.ui.auth

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.data.remote.auth.AuthDataSource
import mx.com.disoftware.blogapp.databinding.FragmentSetupProfileBinding
import mx.com.disoftware.blogapp.domain.auth.AuthRepoImpl
import mx.com.disoftware.blogapp.presentation.auth.AuthViewModel
import mx.com.disoftware.blogapp.presentation.auth.AuthViewModelFactory

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

    private lateinit var binding: FragmentSetupProfileBinding

    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImpl(
        AuthDataSource()
    )) }

    private val REQUEST_IMAGE_CAPTURE = 1

    private var bitmap: Bitmap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSetupProfileBinding.bind(view)
        binding.profileImage.setOnClickListener {
            val takePrictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePrictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "No hay nunguna app para abrir la camara", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCreateProfile.setOnClickListener {
            val username = binding.evUsername.text.toString().trim()
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Uploading...").create()
            bitmap?.let {
                viewModel.updateUserProfile(imageBitmap = it, username = username).observe(viewLifecycleOwner, { result ->
                    when(result) {
                        is Result.Loading -> {
                            Log.d("Setup", "Loanding")
                            alertDialog.show()
                        }
                        is Result.Success -> {
                            Log.d("Setup", "Seucces")
                            alertDialog.dismiss()
                            findNavController().navigate(R.id.action_setupProfileFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            Log.d("Setup", "Failure")
                            alertDialog.dismiss()
                        }
                    }
                })
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImage.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }
    }

}