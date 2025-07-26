package com.darkverse.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.darkverse.app.models.Post
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddPostActivity : AppCompatActivity() {

    private lateinit var contentEditText: EditText
    private lateinit var addMediaButton: Button
    private lateinit var postButton: Button
    private lateinit var progressBar: ProgressBar

    private var selectedMediaUri: Uri? = null
    private var selectedMediaType: String = "" // "image" or "video"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        contentEditText = findViewById(R.id.contentEditText)
        addMediaButton = findViewById(R.id.addMediaButton)
        postButton = findViewById(R.id.postButton)
        progressBar = findViewById(R.id.progressBar)

        addMediaButton.setOnClickListener {
            pickMediaFromDevice()
        }

        postButton.setOnClickListener {
            uploadPost()
        }
    }

    private fun pickMediaFromDevice() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
        startActivityForResult(Intent.createChooser(intent, "Select Media"), 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK && data != null) {
            selectedMediaUri = data.data
            selectedMediaType = when {
                data.type?.startsWith("image") == true -> "image"
                data.type?.startsWith("video") == true -> "video"
                else -> ""
            }
            Toast.makeText(this, "تم اختيار الوسائط: $selectedMediaType", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadPost() {
        val content = contentEditText.text.toString().trim()
        val username = "DarkUser" // عدّل حسب نظام تسجيل الدخول
        val timestamp = System.currentTimeMillis()

        progressBar.visibility = ProgressBar.VISIBLE
        postButton.isEnabled = false

        if (selectedMediaUri != null) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/posts/$filename")

            ref.putFile(selectedMediaUri!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val post = Post(
                            username = username,
                            content = content,
                            mediaUrl = uri.toString(),
                            mediaType = selectedMediaType,
                            timestamp = timestamp
                        )
                        savePostToDatabase(post)
                    }
                }
                .addOnFailureListener {
                    showError("فشل في رفع الوسائط")
                }
        } else {
            val post = Post(
                username = username,
                content = content,
                mediaUrl = "",
                mediaType = "",
                timestamp = timestamp
            )
            savePostToDatabase(post)
        }
    }

    private fun savePostToDatabase(post: Post) {
        val dbRef = FirebaseDatabase.getInstance().getReference("posts")
        val postId = dbRef.push().key ?: return

        dbRef.child(postId).setValue(post)
            .addOnSuccessListener {
                Toast.makeText(this, "تم نشر المنشور!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                showError("فشل في حفظ المنشور")
            }
    }

    private fun showError(msg: String) {
        progressBar.visibility = ProgressBar.GONE
        postButton.isEnabled = true
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
