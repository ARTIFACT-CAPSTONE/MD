package com.example.artifact.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.artifact.R
import com.example.artifact.databinding.ActivityAboutUsBinding
import com.example.artifact.databinding.ActivityUploadBinding
import com.example.artifact.databinding.FragmentProfileBinding
import com.example.artifact.view.authentication.logout.LogoutActivity
import com.example.artifact.view.ui.profile.ProfileFragment

class AboutUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBase.setOnClickListener {
            val intent = Intent(this, ProfileFragment::class.java)
            startActivity(intent)
        }
    }
}