package com.example.arsyibudi

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ArsyiBudi.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.arsyibudi.model.ItemData
import com.example.arsyibudi.view.ItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var addBtn:FloatingActionButton
    private lateinit var recycle:RecyclerView
    lateinit var itemList:ArrayList<ItemData>
    lateinit var itemAdapter:ItemAdapter

    private val addResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val data: Intent? = result.data

        if (result.resultCode == Activity.RESULT_OK) {
            val newItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra("item", ItemData::class.java)
            } else {
                @Suppress("DEPRECATION") data?.getParcelableExtra("item")
            }
            newItem?.let {
                itemList.add(it)
                itemAdapter.notifyItemInserted(itemList.size-1) // Update RecyclerView
            }
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
                data?.getParcelableExtra("item", ItemData::class.java)
            } else {
                @Suppress("DEPRECATION") data?.getParcelableExtra("item")
            }
            val position = data?.getIntExtra("position", 0)
            newItem?.let {
                itemList[position!!].title = it.title
                itemList[position].subtitle = it.subtitle
                itemList[position].desc = it.desc
                itemList[position].img = it.img
                itemAdapter.notifyDataSetChanged() // Update RecyclerView
            }
        }
    }
}
