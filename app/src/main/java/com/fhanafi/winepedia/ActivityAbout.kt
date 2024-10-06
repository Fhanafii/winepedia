package com.fhanafi.winepedia

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fhanafi.winepedia.DetailActivity.Companion.TITLE_ABOUT

class ActivityAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Enable the back button (up button) in the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //mengubah title dari action bar
        val title = intent.getStringExtra(TITLE_ABOUT)
        setTitle(title ?: getString(R.string.about_name))

        //mengubah warna action bar
        window.navigationBarColor = ContextCompat.getColor(this, R.color.colorPrimary)

        //mengubah warna status bar
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }

    // Handle back arrow click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Finish the current activity (go back to MainActivity)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}