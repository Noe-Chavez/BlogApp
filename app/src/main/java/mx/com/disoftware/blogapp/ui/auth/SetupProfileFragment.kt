package mx.com.disoftware.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import mx.com.disoftware.blogapp.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}