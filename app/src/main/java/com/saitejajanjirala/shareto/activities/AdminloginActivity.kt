package com.saitejajanjirala.shareto.activities

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saitejajanjirala.shareto.R
import com.saitejajanjirala.shareto.utils.Connectivity
import kotlinx.android.synthetic.main.activity_adminlogin.*

class AdminloginActivity : AppCompatActivity() {
    var arrayadmins=arrayOf("select the admin","admin1","admin2")
    var selectedadmin:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminlogin)
        setactionbar()
        val madapter= ArrayAdapter(this@AdminloginActivity,android.R.layout.simple_spinner_item,arrayadmins)
            madapter.setDropDownViewResource(R.layout.spinnerbg)
        admintype.adapter=madapter
        admintype.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
              selectedadmin=arrayadmins[p2]
            }
        }
        loginadmin.setOnClickListener {
            if(adminname.text.isBlank()||adminname.text.isEmpty()||adminpassword.text.isBlank()
                ||adminpassword.text.isEmpty()||selectedadmin.equals("select the admin")){
                Snackbar.make(topadmin,"Enter details properly",Snackbar.LENGTH_LONG)
                    .setAction("close"){}
                    .show()
            }
            else{
                val obj=Connectivity(this@AdminloginActivity)
                if(obj.checkconnectivity()){
                    val progress=ProgressDialog(this@AdminloginActivity)
                        progress.setCancelable(false)
                        progress.setTitle("Checking...")
                        progress.create()
                        progress.show()
                    val db=FirebaseDatabase.getInstance().reference.child("admins").child(selectedadmin!!)
                    db.addValueEventListener(object:ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            progress.dismiss()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val map=HashMap<String,Any>()
                            map["name"] = adminname.text.toString()
                            map["password"] = adminpassword.text.toString()
                            if(snapshot.value?.equals(map)!!){
                                progress.dismiss()
                                val pref=getSharedPreferences("users", Context.MODE_PRIVATE)
                                pref.edit().putString("admin",map["name"] as String).apply()
                                Toast.makeText(this@AdminloginActivity,"Welcome",Toast.LENGTH_LONG).show()
                            }
                            else{
                                progress.dismiss()
                                Snackbar.make(topadmin,"The details are wrong",Snackbar.LENGTH_LONG)
                                    .setAction("close"){}
                                    .show()
                            }
                        }
                    })
                }
                else{
                    obj.showdialog()
                }
            }
        }
    }
    fun setactionbar(){
        supportActionBar?.title="Admin Login"
        val colorDrawable: ColorDrawable = ColorDrawable(resources.getColor(R.color.colorAccent))
        supportActionBar?.setBackgroundDrawable(colorDrawable)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                super.onBackPressed()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}