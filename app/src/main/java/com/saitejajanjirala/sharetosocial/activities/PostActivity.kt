package com.saitejajanjirala.sharetosocial.activities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.saitejajanjirala.sharetosocial.R

class PostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setactionbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logoutmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logoutfrom->{
                val dialog= AlertDialog.Builder(this@PostActivity)
                    .setTitle("Exit")
                    .setMessage("Do you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes"){text,listener->
                        val sharedPreferences=getSharedPreferences("users", Context.MODE_PRIVATE)
                        sharedPreferences.edit().clear().apply()
                        Handler().postDelayed({
                            startActivity(Intent(this@PostActivity,LoginActivity::class.java))
                            finish()
                        },500)
                    }
                    .setNegativeButton("No"){text,listener->
                    }
                    .create()
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val dialog=AlertDialog.Builder(this@PostActivity)
            .setTitle("Do you want to exit")
            .setPositiveButton("yes"){text,listener->
                ActivityCompat.finishAffinity(this@PostActivity)
            }
            .setNegativeButton("no"){text,listener->
            }
    }
    fun setactionbar(){
        supportActionBar?.title="Post Tasks"
        val colorDrawable: ColorDrawable = ColorDrawable(resources.getColor(R.color.colorAccent))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

    }
}