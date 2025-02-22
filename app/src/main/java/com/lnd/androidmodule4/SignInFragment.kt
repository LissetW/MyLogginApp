package com.lnd.androidmodule4

import SignUpFragment
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import com.lnd.androidmodule4.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflar el layout con ViewBinding
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                val toastMessage = getString(R.string.toast_field_required)
                Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
            } else {
                val homeIntent = Intent(requireContext(), HomeActivity::class.java).apply {
                    putExtra("EXTRA_EMAIL", email)
                    putExtra("EXTRA_PASSWORD", password)
                }
                startActivity(homeIntent)
            }
        }

        // Configurar el onClickListener para el TextView
        binding.tvSignUp.setOnClickListener {
            // Reemplazar el fragmento de SignIn con SignUp
            parentFragmentManager.beginTransaction()
                .replace(R.id.containerFragment, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}
