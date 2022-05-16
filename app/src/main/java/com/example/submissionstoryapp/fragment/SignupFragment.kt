package com.example.submissionstoryapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.submissionstoryapp.R
import com.example.submissionstoryapp.databinding.FragmentSignupBinding
import com.example.submissionstoryapp.model.SignupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupFragment: Fragment(R.layout.fragment_signup){

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignupViewModel
    private lateinit var nameR: String
    private lateinit var emailR: String
    private lateinit var passR: String
    private lateinit var msg: String
    private lateinit var sts: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SignupViewModel::class.java)

        binding.signupBtn.setOnClickListener{
            nameR = binding.nameField.text.toString()
            emailR = binding.emailField.text.toString()
            passR = binding.passField.text.toString()

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            viewModel.setRegister(nameR, emailR, passR)
            Toast.makeText(activity, "Anda Terdaftar : $nameR , $emailR", Toast.LENGTH_SHORT).show()

            viewModel.getRegister().observe(viewLifecycleOwner, {
                if (it != null){
                    with(it){
                        msg = error.toString()
                        sts = message.toString()
                    }
                }else{
                    Toast.makeText(requireContext(), "Gagal Signup", Toast.LENGTH_LONG).show()
                }
                if (msg != "true"){
                    Toast.makeText(activity, "Anda Sudah Terdaftar\nTunggu Sebentar", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(activity, "Gagal Daftar", Toast.LENGTH_LONG).show()
                }
            })
            CoroutineScope(Dispatchers.Main).launch {
                delay(2000L)
                view.findNavController().navigate(R.id.action_signupFragment2_to_loginFragment2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}