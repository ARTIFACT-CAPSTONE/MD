package com.example.artifact.view.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.artifact.databinding.FragmentProfileBinding
import com.example.artifact.view.AboutUsActivity
import com.example.artifact.view.authentication.logout.LogoutActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.LogoutButton.setOnClickListener {
            logout()
        }

        binding.AboutUsButton.setOnClickListener {
            val intent = Intent(requireContext(), AboutUsActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun logout() {
        val intent = Intent(requireContext(), LogoutActivity::class.java)
        startActivity(intent)

        requireActivity().finish()
    }
}
