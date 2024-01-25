package com.example.ArsyiBudi

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class AddItemActivity : AppCompatActivity() {

    private var imageUri: Uri? = null // Tambahkan deklarasi imageUri

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val imageView: ImageView = findViewById(R.id.image_view)
            if (result.resultCode == Activity.RESULT_OK && data != null) {
                imageUri = data.data
                imageView.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val itemTitle = findViewById<EditText>(R.id.itemTitle)
        val itemSubtitle = findViewById<EditText>(R.id.itemSubtitle)
        val itemDesc = findViewById<EditText>(R.id.itemDesc)
        val imageView: ImageView = findViewById(R.id.image_view)
        val addImageBtn = findViewById<Button>(R.id.select_image_button)
        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val cancelBtn = findViewById<Button>(R.id.cancelBtn)

        if (intent.hasExtra("title")) {
            itemTitle.setText(intent.getStringExtra("title"))
            itemSubtitle.setText(intent.getStringExtra("subtitle"))
            itemDesc.setText(intent.getStringExtra("description"))
            val imagestr = intent.getStringExtra("image")
            val image = Uri.parse(imagestr)
            imageView.setImageURI(image)
        }

        addImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
        }

        saveBtn.setOnClickListener {
            val title = itemTitle.text.toString()
            val subtitle = itemSubtitle.text.toString()
            val desc = itemDesc.text.toString()
            val imageUri = this.imageUri

            val itemData = ItemData(title, subtitle, desc, imageUri)

            val resultIntent = Intent().apply {
                putExtra("action", "add")
                putExtra("item", itemData)
                if (intent.hasExtra("position")) {
                    putExtra("position", intent.getIntExtra("position", 0))
                }
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
            Toast.makeText(this, "Add Item Successful", Toast.LENGTH_SHORT).show()
        }

        cancelBtn.setOnClickListener {
            finish()
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }
    }
}