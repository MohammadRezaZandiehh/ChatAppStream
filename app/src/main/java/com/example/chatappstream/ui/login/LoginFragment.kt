package com.example.chatappstream.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.chatappstream.R
import com.example.chatappstream.databinding.FragmentLoginBinding
import com.plcoding.streamchatapp.ui.BindingFragment
import com.plcoding.streamchatapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class LoginFragment : BindingFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentLoginBinding::inflate

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            setupConnectingUiState()
            viewModel.connectUser(binding.etUsername.text.toString())
        }

        binding.etUsername.addTextChangedListener {
            binding.etUsername.error = null                                                         //whenever text changes we want to reset the error message that we potentially set here to nothing
        }
        subscribeToEvents()
    }

    private fun subscribeToEvents() {                                                               // catch the Events
        lifecycleScope.launchWhenStarted {
            viewModel.loginEvent.collect{ event ->                                                  // loginEvent --> (immutable) --> so we can not send events to sharedFlow
                when(event) {
                    is LoginViewModel.LogInEvent.ErrorInputTooShort -> {
                        setupIdleUiState()                                                          // reset the UI  kind of view events we get here
                        binding.etUsername.error = getString(R.string.error_username_too_short, Constants.MIN_USERNAME_LENGTH)
                    }
                    is LoginViewModel.LogInEvent.ErrorLogIn -> {
                        setupIdleUiState()
                        Toast.makeText(
                            requireContext(),
                            event.error,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is LoginViewModel.LogInEvent.Success -> {
                        setupIdleUiState()
                        Toast.makeText(
                            requireContext(),
                            "Successful login",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    private fun setupConnectingUiState() {
        binding.progressBar.isVisible = true
        binding.btnConfirm.isEnabled = false
    }

    private fun setupIdleUiState() {
        binding.progressBar.isVisible = false
        binding.btnConfirm.isEnabled = true
    }
}