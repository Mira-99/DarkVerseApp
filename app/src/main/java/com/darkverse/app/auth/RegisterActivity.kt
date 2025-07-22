package com.darkverse.app.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.MainActivity
import com.darkverse.app.databinding.ActivityRegisterBinding
import com.darkverse.app.models.User
import com.darkverse.app.models.UserRank
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()
            
            if (validateInput(username, email, password, confirmPassword)) {
                registerUser(username, email, password)
            }
        }
        
        binding.loginText.setOnClickListener {
            finish()
        }
        
        // زر تسجيل الدخول كضيف
        binding.guestButton.setOnClickListener {
            loginAsGuest()
        }
    }
    
    private fun validateInput(username: String, email: String, password: String, confirmPassword: String): Boolean {
        if (username.isEmpty()) {
            binding.usernameInputLayout.error = "يرجى إدخال اسم المستخدم"
            return false
        }
        
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
        
        if (password != confirmPassword) {
            binding.confirmPasswordInputLayout.error = "كلمات المرور غير متطابقة"
            return false
        }
        
        // إزالة أي رسائل خطأ سابقة
        binding.usernameInputLayout.error = null
        binding.emailInputLayout.error = null
        binding.passwordInputLayout.error = null
        binding.confirmPasswordInputLayout.error = null
        
        return true
    }
    
    private fun registerUser(username: String, email: String, password: String) {
        binding.registerButton.isEnabled = false
        binding.registerButton.text = "جاري إنشاء الحساب..."
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        createUserProfile(it.uid, username, email)
                    }
                } else {
                    binding.registerButton.isEnabled = true
                    binding.registerButton.text = "إنشاء حساب"
                    Toast.makeText(this, "فشل في إنشاء الحساب: ${task.exception?.message}", 
                        Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun createUserProfile(uid: String, username: String, email: String) {
        val user = User(
            uid = uid,
            username = username,
            email = email,
            displayName = username,
            rank = UserRank.NEWBIE.name,
            level = 1,
            experience = 0
        )
        
        database.reference.child("users").child(uid).setValue(user)
            .addOnCompleteListener { task ->
                binding.registerButton.isEnabled = true
                binding.registerButton.text = "إنشاء حساب"
                
                if (task.isSuccessful) {
                    Toast.makeText(this, "تم إنشاء الحساب بنجاح", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "فشل في حفظ بيانات المستخدم", Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun loginAsGuest() {
        binding.guestButton.isEnabled = false
        binding.guestButton.text = "جاري تسجيل الدخول كضيف..."
        
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                binding.guestButton.isEnabled = true
                binding.guestButton.text = "متابعة كضيف"
                
                if (task.isSuccessful) {
                    Toast.makeText(this, "تم تسجيل الدخول كضيف", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    Toast.makeText(this, "فشل في تسجيل الدخول كضيف: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
    
    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
