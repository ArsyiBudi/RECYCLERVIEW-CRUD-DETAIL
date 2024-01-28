package com.example.arsyibudi

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ArsyiBudi.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val title = intent.getStringExtra("title")
        val subtitle = intent.getStringExtra("subtitle")
        val description = intent.getStringExtra("description")
        val imagestr = intent.getStringExtra("image")
        val image = Uri.parse(imagestr)
        val titleview = findViewById<TextView>(R.id.detail_Title)
        val subtitleview = findViewById<TextView>(R.id.detail_Subtitle)
        val descview = findViewById<TextView>(R.id.detail_Description)
        val imageview = findViewById<ImageView>(R.id.detail_Image)

        titleview.text = title
        subtitleview.text = subtitle
        descview.text = description
        imageview.setImageURI(image)

    }
}