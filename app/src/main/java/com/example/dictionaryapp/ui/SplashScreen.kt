package com.example.dictionaryapp.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionaryapp.R
import gr.net.maroulis.library.EasySplashScreen

class SplashScreen : AppCompatActivity() {

    lateinit var splashScreenConfig: EasySplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setup splashscreen
        setupSplashScreen()

        //setup setSplashTextColors display textColors
        setSplashTextColors()

        val splashScreen = splashScreenConfig.create()
        setContentView(splashScreen)
    }

    private fun setupSplashScreen() {
        splashScreenConfig = EasySplashScreen(this)
            .withFullScreen()
            .withTargetActivity(MainActivity::class.java)
            .withSplashTimeOut(5000)
            .withHeaderText("Created by Prince Ikede")
            .withFooterText("Created for NIKE")
            .withBeforeLogoText("Swoooosh")
            .withAfterLogoText("Just Do It")
            .withLogo(R.mipmap.ic_launcher_round)
    }

    private fun setSplashTextColors() {
        splashScreenConfig.headerTextView.setTextColor(Color.BLACK)
        splashScreenConfig.footerTextView.setTextColor(Color.BLACK)
        splashScreenConfig.beforeLogoTextView.setTextColor(Color.BLACK)
        splashScreenConfig.afterLogoTextView.setTextColor(Color.BLACK)
    }
}