package com.example.submissionstoryapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.submissionstoryapp.R
import com.example.submissionstoryapp.databinding.FragmentBeginBinding

class BeginFragment : Fragment() {

    private var _binding: FragmentBeginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBeginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_beginFragment_to_loginFragment)
        )
        binding.btnSignup.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_beginFragment_to_signupFragment)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}