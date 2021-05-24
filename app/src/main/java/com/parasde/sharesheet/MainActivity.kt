package com.parasde.sharesheet

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private val REQ_GALLERY_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // text share
        findViewById<Button>(R.id.shareBtn).setOnClickListener {
            val text = findViewById<EditText>(R.id.shareText).text.toString()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Share")
            startActivity(shareIntent)
        }

        // gallery image share
        findViewById<Button>(R.id.imageShareBtn).setOnClickListener {
            val galleryIntent = Intent().apply {
                action = Intent.ACTION_PICK
                type = "image/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*"))
            }
            startActivityForResult(galleryIntent, REQ_GALLERY_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQ_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                val share = Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "image/*"
                    data = intent.data
                    putExtra(Intent.EXTRA_STREAM, intent.data)
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }, "Share")
                startActivity(share)
            }
        }
    }
}