package mx.com.disoftware.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import mx.com.disoftware.blogapp.R
import mx.com.disoftware.blogapp.core.Result
import mx.com.disoftware.blogapp.data.remote.home.HomeScreenDataSource
import mx.com.disoftware.blogapp.databinding.FragmentHomeScreenBinding
import mx.com.disoftware.blogapp.domain.home.HomeScreenRepoImpl
import mx.com.disoftware.blogapp.presentation.HomeScreenViewModel
import mx.com.disoftware.blogapp.presentation.HomeScreenViewModelFactory
import mx.com.disoftware.blogapp.ui.home.adapter.HomeScreenAdapter
import kotlin.Result as Result1

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(HomeScreenRepoImpl(HomeScreenDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPost().observe(viewLifecycleOwner, { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                    binding.progressCircular.visibility = View.GONE
                }
                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Error al cargar los datos del servidor de FireBase",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}