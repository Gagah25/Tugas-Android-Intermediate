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
import com.example.submissionstoryapp.data.UserPreference
import com.example.submissionstoryapp.databinding.FragmentLoginBinding
import com.example.submissionstoryapp.model.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var emailL: String
    private lateinit var passL: String
    private lateinit var msg: String
    private lateinit var tkn: String

    private lateinit var sharedPref: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(LoginViewModel::class.java)
        sharedPref = UserPreference(requireActivity())

        binding.loginBtn.setOnClickListener{
            emailL = binding.email.text.toString()
            passL = binding.pass.text.toString()

            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            viewModel.setLogin(emailL, passL)
            viewModel.setDataLogin(emailL, passL)

            viewModel.getDataLogin().observe(viewLifecycleOwner,{
                if (it != null){
                    with(it){
                        tkn = token.toString()
                    }
                    sharedPref.saveToken(tkn)
                    sharedPref.setPref(UserPreference.PREF_TOKEN, tkn)
                }
            })

            viewModel.getLogin().observe(viewLifecycleOwner, {
                if (it != null){
                    with(it){
                        msg = error.toString()
                    }
                }else{
                    Toast.makeText(requireContext(), "Gagal Login", Toast.LENGTH_LONG).show()
                }
                if (msg == "false"){
                    Toast.makeText(activity, "Anda Sudah Login\nTunggu Sebentar", Toast.LENGTH_SHORT).show()
                    CoroutineScope(Dispatchers.IO).launch {
                        if (emailL.isNotEmpty() && passL.isNotEmpty()){
                            sharedPref.setPref(UserPreference.PREF_USERNAME, emailL)
                            sharedPref.setPref(UserPreference.PREF_IS_LOGIN, true)
                            delay(1000L)
                            view.findNavController().navigate(R.id.action_loginFragment2_to_listStoryActivity)
                        }
                    }
                }
                else{
                    Toast.makeText(activity, "Gagal Login", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}