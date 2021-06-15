package com.br.navigationcomponentapp.ui.registration.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.br.navigationcomponentapp.R
import com.br.navigationcomponentapp.ui.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_profile_data.*


class ProfileDataFragment : Fragment() {

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validationFields = initValidationFields()
        listenToRegistrationViewModelEvents(validationFields)

        btn_profile_data_next.setOnClickListener {
            val name = edt_profile_data_username.text.toString()
            val profile = edt_profile_data_bio.text.toString()

            registrationViewModel.collectProfileData(name, profile)
        }
    }

    private fun initValidationFields() = mapOf(
        RegistrationViewModel.INPUT_NAME.first to edt_profile_data_username,
        RegistrationViewModel.INPUT_BIO.first to edt_profile_data_bio
    )

    private fun listenToRegistrationViewModelEvents(validationFields: Map<String, EditText>) {
        registrationViewModel.registrationStateEvent.observe(viewLifecycleOwner) { registrationState ->
            when (registrationState) {
                is RegistrationViewModel.RegistrationState.CollectCredentials -> {
                    val name = edt_profile_data_username.text.toString()

                    val directions = ProfileDataFragmentDirections
                        .actionProfileDataFragmentToChooseCredentialsFragment(name)

                    findNavController().navigate(directions)
                }
                is RegistrationViewModel.RegistrationState.InvalidProfileData -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }
        }
    }

}