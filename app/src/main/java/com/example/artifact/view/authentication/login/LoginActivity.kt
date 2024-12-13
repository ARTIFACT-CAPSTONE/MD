package com.example.artifact.view.authentication.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.artifact.R
import com.example.artifact.data.pref.UserModel
import com.example.artifact.databinding.ActivityLoginBinding
import com.example.artifact.di.Injection
import com.example.artifact.view.ViewModelFactory
import com.example.artifact.view.authentication.register.RegisterActivity
import com.example.artifact.view.ui.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(
            Injection.provideRepository(
                this
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLoginButtonEnable()

        binding.loginUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setLoginButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.loginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setLoginButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        binding.loginButton.setOnClickListener {
            val username = binding.loginUsername.text.toString()
            val password = binding.loginPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.input_data), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.loginPassword.error = getString(R.string.invalid_password)
                return@setOnClickListener
            }

            val jsonString = """
                {
                    "username": "$username",
                    "password": "$password"
                  }
                """.trimIndent()

            val requestBody = RequestBody.create("application/json".toMediaType(), jsonString)

            loginViewModel.login(requestBody)
        }

        loginViewModel.getSession().observe(this) { isLoggedIn ->
            if (isLoggedIn.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        setupView()
        setupAction()
        playAnimation()

        loginViewModel.result.observe(this) {
            if (it.message != "Login successful") {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.saveSession(
                    UserModel(
                        it.token.toString()
                    )
                )
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setLoginButtonEnable() {
        val username =
            binding.loginUsername.text != null
        val password = binding.loginPassword.text != null && binding.loginPassword.text.toString().length >= 8
        binding.loginButton.isEnabled = username && password
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
        binding.tvLoginRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvLoginTitle, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.loginUsername, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.etlLoginUsername, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.loginPassword, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.etlLoginPassword, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val registertxt = ObjectAnimator.ofFloat(binding.tvLoginQuestion, View.ALPHA, 1f).setDuration(100)
        val register = ObjectAnimator.ofFloat(binding.tvLoginRegister, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                registertxt,
                register
            )
            startDelay = 100
        }.start()
    }
}