package com.darkverse.app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.models.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddPostActivity : AppCompatActivity() {

    private lateinit var editPostContent: EditText
    private lateinit var buttonPost: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        editPostContent = findViewById(R.id.editPostContent)
        buttonPost = findViewById(R.id.buttonPost)

        buttonPost.setOnClickListener {
            val content = editPostContent.text.toString().trim()
            if (content.isNotEmpty()) {
                uploadPost(content)
            } else {
                Toast.makeText(this, "اكتب شيئًا أولًا", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadPost(content: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val post = Post(
            username = user?.displayName ?: "مجهول",
            content = content,
            timestamp = System.currentTimeMillis()
        )

        FirebaseDatabase.getInstance().getReference("posts")
            .push()
            .setValue(post)
            .addOnSuccessListener {
                Toast.makeText(this, "تم نشر المنشور", Toast.LENGTH_SHORT).show()
                finish() // رجوع لواجهة الرئيسية
            }
            .addOnFailureListener {
                Toast.makeText(this, "فشل في النشر", Toast.LENGTH_SHORT).show()
            }
    }
}
