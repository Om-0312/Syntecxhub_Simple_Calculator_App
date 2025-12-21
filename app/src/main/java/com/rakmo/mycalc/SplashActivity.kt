package com.rakmo.mycalc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 1. Hide the Action Bar for full screen look
        supportActionBar?.hide()

        // 2. Find views
        val logo = findViewById<ImageView>(R.id.splashLogo)
        val text = findViewById<TextView>(R.id.splashText)

        // 3. Load the animation
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_slide)

        // 4. Start animation on elements
        logo.startAnimation(anim)
        text.startAnimation(anim)

        // 5. Move to Main Activity after 4 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Destroys the splash activity so back button doesn't return to it
        }, 4000)
    }
}
