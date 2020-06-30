package com.saitejajanjirala.sharetosocial.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.saitejajanjirala.sharetosocial.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            val sharedPreferences=getSharedPreferences("users",Context.MODE_PRIVATE)
            if(sharedPreferences.contains("uid")){
                startActivity(Intent(this@MainActivity,MainscreenActivity::class.java))
                finish()
            }
            else if(sharedPreferences.contains("admin")){
                startActivity(Intent(this@MainActivity,PostActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                finish()
            }
        },1200)
    }
}