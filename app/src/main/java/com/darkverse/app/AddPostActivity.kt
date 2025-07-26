package com.darkverse.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.darkverse.app.models.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class AddPostActivity : AppCompatActivity() {

    private lateinit var editPostContent: EditText
    private lateinit var buttonPost: Button
    private lateinit var buttonPickMedia: Button
    private lateinit var mediaPreview: ImageView

    private var mediaUri: Uri? = null
    private var mediaType: String = ""

    companion object {
        private const val PICK_MEDIA_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        editPostContent = findViewById(R.id.editPostContent)
        buttonPost = findViewById(R.id.buttonPost)
        buttonPickMedia = findViewById(R.id.buttonPickMedia)
        mediaPreview = findViewById(R.id.mediaPreview)

        buttonPickMedia.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
            startActivityForResult(intent, PICK_MEDIA_REQUEST)
        }

        buttonPost.setOnClickListener {
            val content = editPostContent.text.toString().trim()
            if (content.isEmpty() && mediaUri == null) {
                Toast.makeText(this, "اكتب شيئًا أو اختر إعلامًا", Toast.LENGTH_SHORT).show()
            } else {
                if (mediaUri != null) {
                    uploadMediaAndPost(content)
                } else {
                    uploadPost(content, null, null)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_MEDIA_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                mediaUri = uri
                mediaType = contentResolver.getType(uri) ?: ""

                mediaPreview.visibility = ImageView.VISIBLE
                if (mediaType.startsWith("image/")) {
                    Glide.with(this).load(uri).into(mediaPreview)
                } else if (mediaType.startsWith("video/")) {
                    mediaPreview.setImageResource(android.R.color.darker_gray)
                } else {
                    mediaPreview.visibility = ImageView.GONE
                    Toast.makeText(this, "نوع الوسائط غير مدعوم", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                Toast.makeText(this, "لم يتم اختيار ملف", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadMediaAndPost(content: String) {
        val uri = mediaUri
        if (uri == null) {
            Toast.makeText(this, "لم يتم اختيار أي ملف", Toast.LENGTH_SHORT).show()
            return
        }

        val filename = UUID.randomUUID().toString()
        val storageRef = FirebaseStorage.getInstance().getReference("/media/$filename")

        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl
                    .addOnSuccessListener { downloadUri ->
                        val type = if (mediaType.startsWith("image/")) "image" else "video"
                        uploadPost(content, downloadUri.toString(), type)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "فشل في جلب رابط الملف: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "فشل في رفع الإعلام: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
    }

    private fun uploadPost(content: String, mediaUrl: String?, mediaType: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        val post = Post(
            username = user?.displayName ?: "مجهول",
            content = content,
            mediaUrl = mediaUrl ?: "",
            mediaType = mediaType ?: "",
            timestamp = System.currentTimeMillis()
        )

        FirebaseDatabase.getInstance().getReference("posts")
            .push()
            .setValue(post)
            .addOnSuccessListener {
                Toast.makeText(this, "تم نشر المنشور", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "فشل في النشر: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
