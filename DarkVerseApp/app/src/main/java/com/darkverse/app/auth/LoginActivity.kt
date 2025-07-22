package com.darkverse.app.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.MainActivity
import com.darkverse.app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = FirebaseAuth.getInstance()
        
        // Check if user is already logged in
        if (auth.currentUser != null) {
            navigateToMain()
            return
        }
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            
            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }
        
        binding.registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            binding.emailInputLayout.error = "يرجى إدخال البريد الإلكتروني"
            return false
        }
        
        if (password.isEmpty()) {
            binding.passwordInputLayout.error = "يرجى إدخال كلمة المرور"
            return false
        }
        
        if (password.length < 6) {
            binding.passwordInputLayout.error = "كلمة المرور يجب أن تكون 6 أحرف على الأقل"
            return false
        }
        
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        return true
    }
    
    private fun loginUser(email: String, password: String) {
        binding.loginButton.isEnabled = false
        binding.loginButton.text = "جاري تسجيل الدخول..."
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.loginButton.isEnabled = true
                binding.loginButton.text = "تسجيل الدخول"
                
                if (task.isSuccessful) {
                    Toast.makeText(this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "فشل في تسجيل الدخول: ${task.exception?.message}", 
                        Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

