package com.example.ArsyiBudi

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ArsyiBudi.AddItemActivity
import com.example.ArsyiBudi.DetailActivity
import com.example.ArsyiBudi.ItemData
import com.example.ArsyiBudi.R

class ItemAdapter(val c: Context, val itemList: ArrayList<ItemData>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView
        var subtitle: TextView
        var img: ImageView
        var mMenus: ImageView

        init {
            title = v.findViewById(R.id.textView_Title)
            subtitle = v.findViewById(R.id.textView_Subtitle)
            img = v.findViewById(R.id.imageView_Profile)
            mMenus = v.findViewById(R.id.mMenus)

            mMenus.setOnClickListener { popupMenus() }
            v.setOnClickListener { itemDetail() }
        }

        private fun itemDetail() {
            val intent = Intent(c, DetailActivity::class.java)
            intent.putExtra("title", itemList[adapterPosition].title)
            intent.putExtra("subtitle", itemList[adapterPosition].subtitle)
            intent.putExtra("description", itemList[adapterPosition].desc)
            intent.putExtra("image", itemList[adapterPosition].img.toString())
            c.startActivity(intent)
        }

        private fun popupMenus() {
            val popupMenus = PopupMenu(c, v)
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.editItem -> {
                        val intent = Intent(c, AddItemActivity::class.java)
                        intent.putExtra("title", itemList[adapterPosition].title)
                        intent.putExtra("subtitle", itemList[adapterPosition].subtitle)
                        intent.putExtra("description", itemList[adapterPosition].desc)
                        intent.putExtra("image", itemList[adapterPosition].img.toString())
                        intent.putExtra("position", adapterPosition)
                        (c as Activity).startActivityForResult(intent, 2)
                        true
                    }
                    R.id.deleteItem -> {
                        // Set delete
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.baseline_warning_24)
                            .setMessage("Are you sure to delete this Item")
                            .setPositiveButton("Yes") { dialog, _ ->
                                // Code yang akan dijalankan saat tombol "Yes" ditekan
                                itemList.removeAt(adapterPosition)
                                notifyItemRemoved(adapterPosition)
                                Toast.makeText(c, "Item Deleted", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                        true
                    }
                    else -> true
                }
            }

            popupMenus.show()
                val popup = PopupMenu::class.java.getDeclaredField("mPopup")
                popup.isAccessible = true
                val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(menu, true)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val newList = itemList[position]
        holder.title.text = newList.title
        holder.subtitle.text = newList.subtitle
        holder.img.setImageURI(newList.img)
    }
}