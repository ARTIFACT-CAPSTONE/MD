package com.example.artifact.view.authentication.register

import com.example.artifact.R
import com.example.artifact.databinding.ActivityRegisterBinding
import com.example.artifact.di.Injection
import com.example.artifact.view.ViewModelFactory
import com.example.artifact.view.authentication.login.LoginActivity
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(
            Injection.provideRepository(
                this
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRegisterButtonEnable()

        binding.registerName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.registerEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.registerPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setRegisterButtonEnable()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        setupView()
        setupAction()
        playAnimation()

        registerViewModel.result.observe(this) {
            if (it.message == "Jury registered successfully") {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION") if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val username = binding.registerName.text.toString()
            val password = binding.registerPassword.text.toString()
            val email = binding.registerEmail.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.input_data), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.registerPassword.error = getString(R.string.invalid_password)
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.registerEmail.error = getString(R.string.invalid_email)
                return@setOnClickListener
            }

            val jsonString = """
                {
                    "username": "$username",
                    "password": "$password",
                    "email": "$email"
                  }
                """.trimIndent()

            val requestBody = RequestBody.create("application/json".toMediaType(), jsonString)

            registerViewModel.register(requestBody)
        }
        binding.tvRegisterLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRegisterButtonEnable() {
        val name =
            binding.registerName.text != null && binding.registerName.text.toString().isNotEmpty()
        val email =
            binding.registerEmail.text != null && android.util.Patterns.EMAIL_ADDRESS.matcher(
                binding.registerEmail.text.toString()
            )
                .matches()
        val password =
            binding.registerPassword.text != null && binding.registerPassword.text.toString().length >= 8
        binding.registerButton.isEnabled = name && email && password
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.registerButton.isEnabled = !isLoading
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivRegister, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvRegisterTitle, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.registerName, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.etlRegisterName, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.registerEmail, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.etlRegisterEmail, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.registerPassword, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.etlRegisterPassword, View.ALPHA, 1f).setDuration(100)
        val register =
            ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                register
            )
            startDelay = 100
        }.start()
    }
}