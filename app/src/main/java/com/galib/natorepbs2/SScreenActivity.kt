package com.galib.natorepbs2

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class SScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR)
        supportActionBar?.hide()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        } else {
            setContentView(R.layout.activity_splash_screen)
            Executors.newSingleThreadScheduledExecutor().schedule({
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish() }, 1, TimeUnit.SECONDS)
        }
    }
}