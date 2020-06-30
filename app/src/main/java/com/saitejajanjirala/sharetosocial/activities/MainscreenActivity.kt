package com.saitejajanjirala.sharetosocial.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.saitejajanjirala.sharetosocial.R
import com.saitejajanjirala.sharetosocial.fragments.DashboardFragment
import com.saitejajanjirala.sharetosocial.fragments.HomeFragment
import com.saitejajanjirala.sharetosocial.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_mainscreen.*


class MainscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainscreen)
        settoolbar()
        open(DashboardFragment())
        bottomnavview.selectedItemId=R.id.dashboardb
        supportActionBar!!.title="Dashboard"
        navview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout->{
                    val dialog=AlertDialog.Builder(this@MainscreenActivity)
                        .setTitle("Exit")
                        .setMessage("Do you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes"){text,listener->
                            val sharedPreferences=getSharedPreferences("users",Context.MODE_PRIVATE)
                            sharedPreferences.edit().clear().apply()
                            Handler().postDelayed({
                                startActivity(Intent(this@MainscreenActivity,LoginActivity::class.java))
                                finish()
                            },500)
                        }
                        .setNegativeButton("No"){text,listener->
                        }
                        .create()
                        .show()
                    it.isChecked=false
                    it.isCheckable = false
                }
            }
            drawerLayout.closeDrawer(navview,true)
            return@setNavigationItemSelectedListener true
        }

        bottomnavview.setOnNavigationItemSelectedListener {
            it.isCheckable=true
            it.isChecked=true
            when(it.itemId){
                R.id.homeb->{
                    supportActionBar!!.title="Home"
                    open(HomeFragment())
                }
                R.id.taskb->{
                    supportActionBar!!.title="Tasks"
                    open(TasksFragment())
                }
                R.id.dashboardb->{
                    supportActionBar!!.title="Dashboard"
                    open(DashboardFragment())
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
    fun settoolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
//        toggle.isDrawerIndicatorEnabled = false
//        toggle.setHomeAsUpIndicator(R.drawable.ic_bar_chart)
//        toggle.setToolbarNavigationClickListener(object : OnClickListener() {
//            fun onClick(view: View?) {
//                drawerLayout.openDrawer(GravityCompat.START)
//            }
//        })
        toggle.syncState()
    }
    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navview,true)
        return true
    }
    fun open(fr:Fragment){
        supportFragmentManager.beginTransaction().replace(
            R.id.container,
            fr
        ).commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}