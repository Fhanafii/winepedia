package com.fhanafi.winepedia

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvWines: RecyclerView
    private val list = ArrayList<Wine>()

    // menampilkan tampilan awal aplikasi
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ToolBar)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //mengubah warna action bar
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        //mengubah warna status bar
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvWines = findViewById(R.id.rv_wines)
        rvWines.setHasFixedSize(true)

        list.addAll(getListWines())
        showRecyclerList()
    }

    //menampilkan opsi menu list dan grid di awal
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //buat mengubah tampilan item menjadi grid atau list
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_list -> {
                rvWines.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvWines.layoutManager = GridLayoutManager(this, 2)
            }
            R.id.about_page -> {
                val intent = Intent(this, ActivityAbout::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListWines(): ArrayList<Wine>{
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataScore = resources.getStringArray(R.array.data_score)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataImported = resources.getStringArray(R.array.data_made)
        val listWine = ArrayList<Wine>()
        for (i in dataName.indices){
            val wine = Wine(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1), dataScore[i],dataPrice[i],dataImported[i])
            listWine.add(wine)
        }
        return listWine
    }

    private fun showRecyclerList(){
        rvWines.layoutManager = LinearLayoutManager(this)
        val ListWineAdapter = ListWineAdapter(list)
        rvWines.adapter = ListWineAdapter

        ListWineAdapter.setOnClickCallback(object: ListWineAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Wine) {
                showSelectedWine(data)
                // change
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.Key_wine, data) // Use the constant defined in DetailActivity
                }
                startActivity(intent)
            }
        })
    }

    private fun showSelectedWine(wine: Wine){
        Toast.makeText(this, "Kamu memilih " + wine.name, Toast.LENGTH_SHORT).show()
    }
}