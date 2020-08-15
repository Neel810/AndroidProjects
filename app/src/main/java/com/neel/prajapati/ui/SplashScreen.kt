package com.neel.prajapati.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mysocietyapp.watchman.Util.Util
import com.mysocietyapp.watchman.Util.Util.ONE
import com.neel.prajapati.R
import com.neel.prajapati.Utils.Pref


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            if ( Pref.getValue(this, Util.IS_LOGIN, "").equals(ONE)) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }else{
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }

        }, 3000)
    }
}