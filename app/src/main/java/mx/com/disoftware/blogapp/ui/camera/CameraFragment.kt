package mx.com.disoftware.blogapp.ui.camera

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.data.remote.camera.CameraDataSource
import mx.com.disoftware.blogapp.data.remote.home.HomeScreenDataSource
import mx.com.disoftware.blogapp.databinding.FragmentCameraBinding
import mx.com.disoftware.blogapp.domain.camera.CameraRepoImpl
import mx.com.disoftware.blogapp.domain.home.HomeScreenRepoImpl
import mx.com.disoftware.blogapp.presentation.HomeScreenViewModel
import mx.com.disoftware.blogapp.presentation.HomeScreenViewModelFactory
import mx.com.disoftware.blogapp.presentation.camera.CameraViewModel
import mx.com.disoftware.blogapp.presentation.camera.CameraViewModelFactory

class CameraFragment : Fragment(R.layout.fragment_camera) {

    private val REQUEST_IMAGE_CAPTURE = 1;
    private lateinit var binding: FragmentCameraBinding
    private lateinit var bitmap: Bitmap
    private val viewModel by viewModels<CameraViewModel> {
        CameraViewModelFactory(CameraRepoImpl(CameraDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCameraBinding.bind(view)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No se enceontró nunguna app de cámara", Toast.LENGTH_LONG).show()
        }

        binding.btnUploadPhoto.setOnClickListener {
            bitmap?.let {
                viewModel.upLoadPhoto(it, binding.etPhotoDescription.text.toString().trim()).observe(viewLifecycleOwner, { result ->
                    when(result){
                        is Result.Loading -> {
                            Toast.makeText(requireContext(), "Uploading photo...", Toast.LENGTH_LONG).show()
                        }
                        is Result.Success -> {
                            findNavController().navigate(R.id.action_cameraFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_LONG).show()
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
            binding.imgAddPhoto.setImageBitmap(imageBitmap)
            bitmap = imageBitmap
        }

    }

}