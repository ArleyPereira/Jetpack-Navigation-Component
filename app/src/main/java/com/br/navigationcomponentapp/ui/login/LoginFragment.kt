package com.br.navigationcomponentapp.ui.login

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.br.navigationcomponentapp.R
import com.br.navigationcomponentapp.extensions.dismissError
import com.br.navigationcomponentapp.extensions.navigateWithAnimations
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.authenticationStateEvent.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    findNavController().popBackStack()
                }

                is LoginViewModel.AuthenticationState.InvalidAuthentication -> {
                    val validationFields: Map<String, EditText> = initValidationFields()

                    authenticationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }
        })

        btn_login.setOnClickListener {
            val username = edt_user_name.text.toString()
            val password = edt_password.text.toString()

            viewModel.authentication(username, password)
        }

        btn_signup.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_loginFragment_to_navigation)
        }

        edt_user_name.addTextChangedListener {
            edt_user_name.dismissError()
        }

        edt_password.addTextChangedListener {
            edt_password.dismissError()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            cancelAuthentication()
        }

    }

    private fun cancelAuthentication() {
        viewModel.refusedAuthentication()
        findNavController().popBackStack(R.id.startFragment, false)
    }

    private fun initValidationFields() = mapOf(
        LoginViewModel.INPUT_USERNAME.first to edt_user_name,
        LoginViewModel.INPUT_PASSWORD.first to edt_password
    )

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_nav_login_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit_login_fragment -> {
                Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> {
                cancelAuthentication()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}