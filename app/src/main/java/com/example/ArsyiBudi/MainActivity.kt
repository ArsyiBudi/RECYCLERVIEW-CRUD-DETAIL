package me.projeckandro.arsyi_recycler

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

//import me.projeckandro.arsyi_recycler.model.ItemData
//import me.projeckandro.arsyi_recycler.view.ItemAdapter
import android.os.Build
import android.widget.ImageView
import com.example.ArsyiBudi.AddItemActivity
import com.example.ArsyiBudi.ItemAdapter
import com.example.ArsyiBudi.ItemData
import com.example.ArsyiBudi.R

class MainActivity : AppCompatActivity() {

    private lateinit var addBtn: FloatingActionButton
    private lateinit var recycle: RecyclerView
    lateinit var itemList: ArrayList<ItemData>
    lateinit var itemAdapter: ItemAdapter

    private var imageUri: Uri? = null // Tambahkan deklarasi imageUri

    private val addResultLauncher =
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
        setContentView(R.layout.activity_main)

        itemList = ArrayList()
        addBtn = findViewById(R.id.addingBtn)
        recycle = findViewById(R.id.mRecycler)
        itemAdapter = ItemAdapter(this, itemList)
        recycle.layoutManager = LinearLayoutManager(this)
        recycle.adapter = itemAdapter
        addBtn.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            addResultLauncher.launch(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            val newItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data.getParcelableExtra<ItemData>("item")
            } else {
                data.getParcelableExtra<ItemData>("item")
            }
            val position = data.getIntExtra("position", 0)
            itemList[position] = newItem
            itemAdapter.notifyItemChanged(position)
        }
    }
}

