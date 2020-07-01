package com.saitejajanjirala.shareto.activities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.saitejajanjirala.shareto.R
import com.saitejajanjirala.shareto.utils.Connectivity
import kotlinx.android.synthetic.main.activity_addsociallinks.*


class AddsociallinksActivity : AppCompatActivity() {
    var mname:String?=null
    var mnumber:String?=null
    var mlc:String?=null
    var mac:String?=null
    var memail:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addsociallinks)
        setactionbar()
        mname=intent.getStringExtra("name")
        mnumber=intent.getStringExtra("number")
        mlc=intent.getStringExtra("lc")
        mac=intent.getStringExtra("ac")
        memail=intent.getStringExtra("email")
        facebooktext.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(facebooktext.text.isBlank()||facebooktext.text.isEmpty()){
                    facebooklayout.error="Enter correct id"
                }
                else{
                    facebooklayout.isErrorEnabled=false
                }
            }
        })
        twittertext.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(twittertext.text.isBlank()|| twittertext.text.isEmpty()){
                    twitterlayout.error="Enter proper id"
                }
                else{
                    twitterlayout.isErrorEnabled=false
                }
            }
        })
        whatsapptext.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(whatsapptext.text.isEmpty()|| whatsapptext.text.isBlank()){
                    whatsapplayout.error="Enter correct number"
                }
                else{
                    whatsapplayout.isErrorEnabled=false
                }
            }
        })

        next.setOnClickListener {
            val view=currentFocus
            if(view!=null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            val obj=Connectivity(this@AddsociallinksActivity)
            if(obj.checkconnectivity()) {
                if (whatsapplayout.isErrorEnabled || twitterlayout.isErrorEnabled || facebooklayout.isErrorEnabled) {
                    Snackbar.make(toplinks, "Enter details Properly", Snackbar.LENGTH_SHORT)
                        .setAction("close") {}
                        .show()
                } else {
//                    val dialog=ProgressDialog(this@AddsociallinksActivity)
//                    dialog.setTitle("Loading....")
//                    dialog.setCancelable(false)
//                    dialog.create()
//                    dialog.show()
                       val intent= Intent(this@AddsociallinksActivity,OtpActivity::class.java)
                        intent.putExtra("from","signup")
                        intent.putExtra("name",mname)
                        intent.putExtra("email",memail)
                        intent.putExtra("number",mnumber)
                        intent.putExtra("lc",mlc)
                        intent.putExtra("ac",mac)
                        intent.putExtra("fb",facebooktext.text.toString())
                        intent.putExtra("twitter",twittertext.text.toString())
                        intent.putExtra("whatsapp",whatsapptext.text.toString())
                        intent.putExtra("referal",referalcodeaddlinks.text.toString())
                        startActivity(intent)
//                        dialog.dismiss()
//                        val error=AlertDialog.Builder(this@AddsociallinksActivity)
//                            .setTitle("Error")
//                            .setMessage("The Referal code you entered is Invalid")
//                            .setPositiveButton("ok"){text,listener->
//                            }
//                            .create()
//                            .show()

//                     else {
//                        val ref = FirebaseDatabase.getInstance().reference.child("referalcodes")
//                        ref.addValueEventListener(object : ValueEventListener {
//                            override fun onCancelled(error: DatabaseError) {
//                            }
//
//                            override fun onDataChange(snapshot: DataSnapshot) {
//                                snapshot.children.forEach {
////                                    if(it.value!!.equals(referalcodeaddlinks.text.toString())){
////                                            dialog.dismiss()
////                                            startActivity(intent)
////                                    }
////                                    else
//
//                                }
//                            }
//                        })
//                    }
                }
            }
            else{
                obj.showdialog()
            }
        }
    }
    fun setactionbar(){
        supportActionBar?.title="Connect Links"
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
    override fun onStart() {
        super.onStart()
        addlinksname.text=mname
        addlinksmobileno.text=mnumber
        addlinkslc.text=mlc
        addlinksac.text=mac
    }
}