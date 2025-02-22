import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lnd.androidmodule4.HomeActivity
import com.lnd.androidmodule4.R
import com.lnd.androidmodule4.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding : FragmentSignUpBinding

    companion object {
        @JvmStatic
        fun newInstance() = SignUpFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        setupSpinner()
        setupListeners()
        binding.btnRegister.isEnabled = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupSpinner() {
        val countries = arrayOf("Selecciona un país", "México", "Argentina", "Colombia", "Ecuador", "Dinamarca", "España")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, countries)
        binding.spCountry.adapter = adapter
    }

    private fun setupListeners() {
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { validateFields() }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etName.addTextChangedListener(textWatcher)
        binding.etLastName.addTextChangedListener(textWatcher)
        binding.etEmail.addTextChangedListener(textWatcher)
        binding.etPassword1.addTextChangedListener(textWatcher)
        binding.etPassword2.addTextChangedListener(textWatcher)

        binding.cbConditions.setOnCheckedChangeListener { _, _ -> validateFields() }
        binding.spCountry.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                validateFields()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword1.text.toString()
            val genderId = binding.rgGender.checkedRadioButtonId
            val gender = if (genderId == R.id.rbMan) "Masculino" else "Femenino"
            val country = binding.spCountry.selectedItem.toString()

            val homeIntent = Intent(requireContext(), HomeActivity::class.java).apply {
                putExtra("EXTRA_NAME", name)
                putExtra("EXTRA_LAST_NAME", lastName)
                putExtra("EXTRA_EMAIL", email)
                putExtra("EXTRA_PASSWORD", password)
                putExtra("EXTRA_GENDER", gender)
                putExtra("EXTRA_COUNTRY", country)
            }
            startActivity(homeIntent)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateFields() {
        var isFormValid = true

        if (binding.etName.text.toString().isEmpty()) {
            binding.etName.error = "El nombre es obligatorio"
            isFormValid = false
        }
        if (!isValidEmail(binding.etEmail.text.toString())) {
            binding.etEmail.error = "Ingresa un correo válido"
            isFormValid = false
        }
        if (binding.etPassword1.text.toString().length < 6) {
            binding.etPassword1.error = "La contraseña debe tener al menos 6 caracteres"
            isFormValid = false
        }
        if (binding.etPassword1.text.toString() != binding.etPassword2.text.toString()) {
            binding.etPassword2.error = "Las contraseñas no coinciden"
            isFormValid = false
        }
        if (binding.rgGender.checkedRadioButtonId == -1) {
            binding.tvGender.error = "Selecciona un género"
            isFormValid = false
        }
        if (binding.spCountry.selectedItemPosition == 0) {
            binding.tvCountry.error = "Selecciona un país"
            isFormValid = false
        }
        if (!binding.cbConditions.isChecked) {
            isFormValid = false
        }

        binding.btnRegister.isEnabled = isFormValid
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
