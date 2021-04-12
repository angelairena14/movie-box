package com.test.moviebox.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.test.moviebox.R

class SplsahScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splsah_screen)
        Handler().postDelayed({
            startActivity(Intent(this, MovieListActivity::class.java))
            finish()
        },2000)
    }
}