package com.fhanafi.winepedia

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fhanafi.winepedia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    // deklarasi View Binding
    private lateinit var binding: ActivityDetailBinding
    private var dataWine: Wine? = null

    // deklarasi konstanta untuk kunci intent
    companion object {
        const val Key_wine = "key_wine"
        const val TITLE_DETAIL = "title"
        const val TITLE_ABOUT = "about"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // inisialisasi View Binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //mengubah title dari action bar
        val title = intent.getStringExtra(TITLE_DETAIL)
        setTitle(title ?: getString(R.string.detail_name))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //mengubah warna action bar
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        //mengubah warna status bar
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mengambil data dari intent
        dataWine = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(Key_wine, Wine::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Key_wine)
        }

        //menampilkan data ke layout
        binding.detailTvName.text = dataWine?.name
        binding.detailTvDescription.text = dataWine?.description
        binding.detailNumScore.text = dataWine?.score
        binding.detailNumPrice.text = dataWine?.price
        binding.detailNumCase.text = dataWine?.imported
        dataWine?.photo?.let {
            binding.detailImgPhoto.setImageResource(it)
        }
    }

    // fungsi untuk menampilkan menu_detail
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    // fungsi untuk share content menggunakan intent
    private fun shareContent(){
        val shareText = "Check out this Wine!\n\n" +
                "Name: ${dataWine?.name}\n" +
                "Description: ${dataWine?.description}"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    // fungsi untuk menampilkan tombol back dan share
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_share -> {
                shareContent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}