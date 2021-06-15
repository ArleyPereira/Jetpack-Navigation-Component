package com.br.navigationcomponentapp.ui.profile

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.br.navigationcomponentapp.R
import com.br.navigationcomponentapp.extensions.navigateWithAnimations
import com.br.navigationcomponentapp.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        loginViewModel.authenticationStateEvent.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    text_profile_username.text =
                        getString(R.string.profile_text_username, loginViewModel.username)
                }
                is LoginViewModel.AuthenticationState.Unauthenticated -> {
                    findNavController().navigate(R.id.loginFragment)
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_nav_profile_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save_profile_fragment -> {
                Toast.makeText(activity, "Save", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_favorite_profile_fragment -> {
                Toast.makeText(activity, "favorite", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}