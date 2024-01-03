package com.example.tictactoe


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val logoImageView = findViewById<ImageView>(R.id.logoImageView)
        val titleView = findViewById<ImageView>(R.id.imageView)

        // Apply animation to the logo
        val animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        logoImageView.startAnimation(animation)

        val animation2 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        titleView.startAnimation(animation2)



        // Handler to navigate to the main activity after the splash duration
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out) // Apply fade-in transition animation
            finish() // Close the splash activity to prevent going back to it
        }, SPLASH_DELAY)
    }
}

