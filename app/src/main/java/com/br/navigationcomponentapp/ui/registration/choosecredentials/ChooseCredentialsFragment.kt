package com.br.navigationcomponentapp.ui.registration.choosecredentials

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.br.navigationcomponentapp.R
import kotlinx.android.synthetic.main.fragment_choose_credentials.*


class ChooseCredentialsFragment : Fragment() {

    val args: ChooseCredentialsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_credentials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text_choose_credentials_name.text = getString(
            R.string.choose_credentials_text_name, args.name)
    }

}