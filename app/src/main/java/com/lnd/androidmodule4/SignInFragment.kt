package com.lnd.androidmodule4

import SignUpFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        // Configurar el onClickListener para el TextView
        binding.tvSignUp.setOnClickListener {
            // Reemplazar el fragmento de SignIn con SignUp
            parentFragmentManager.beginTransaction()
                .replace((view.parent as ViewGroup).id, SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
        (activity as AppCompatActivity).supportActionBar?.hide()
    }
    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show() // Mostrar la ActionBar cuando el usuario sale del Fragment

    }
}
