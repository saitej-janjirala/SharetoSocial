package com.saitejajanjirala.sharetosocial.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.saitejajanjirala.sharetosocial.R
import com.saitejajanjirala.sharetosocial.utils.Connectivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        phonenumbertext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phonenumberlayout.isErrorEnabled = true
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.trim().length != 10) {
                    phonenumberlayout.isErrorEnabled = true
                    phonenumberlayout.error = "Number must be 10 digits"
                } else {
                    phonenumberlayout.isErrorEnabled = false
                }
            }
        })
        gotosingupbutton.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
        forgotpassword.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ForgotpasswordActivity::class.java))
        }
        loginbutton.setOnClickListener {
            val obj = Connectivity(this@LoginActivity)
            if(obj.checkconnectivity()) {
                if(phonenumberlayout.isErrorEnabled){
                    Snackbar.make(toplogin, "Enter details Properly", Snackbar.LENGTH_SHORT)
                        .setAction("close") {}
                        .show()
                }
                else{
                    val intent=Intent(this@LoginActivity,OtpActivity::class.java)
                    intent.putExtra("from","login")
                    intent.putExtra("number",phonenumbertext.text.toString())
                    startActivity(intent)
                }
            }
            else{
                obj.showdialog()
            }
        }
        loginasadmin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, AdminloginActivity::class.java))
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}