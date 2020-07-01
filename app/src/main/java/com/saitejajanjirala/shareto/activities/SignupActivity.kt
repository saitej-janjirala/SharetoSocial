package com.saitejajanjirala.shareto.activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.saitejajanjirala.shareto.R
import com.saitejajanjirala.shareto.utils.Connectivity
import com.saitejajanjirala.shareto.utils.Emailvalidator
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    lateinit var lcspinner: Spinner
    lateinit var acspinner:Spinner
    lateinit var nametext:EditText
    lateinit var emailtext:EditText
    lateinit var phonenumbertext:EditText
    lateinit var namelayout:TextInputLayout
    lateinit var emaillayout:TextInputLayout
    lateinit var phonenumberlayout:TextInputLayout
    private var aclist:ArrayList<Array<out String>>?=null
    private var lcselected:String?=null
    private var acselected:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        setactionbar()
        nametext=findViewById(R.id.nametext)
        emailtext=findViewById(R.id.emailtext)
        phonenumbertext=findViewById(R.id.numbertext)
        namelayout=findViewById(R.id.namelayout)
        emaillayout=findViewById(R.id.emaillayout)
        phonenumberlayout=findViewById(R.id.numberlayout)
        acspinner=findViewById(R.id.acspinner)
        lcspinner=findViewById(R.id.lcspinner)
        aclist= ArrayList()
        aclist!!.add(resources.getStringArray(R.array.ADILABAD))
        aclist!!.add(resources.getStringArray(R.array.BHONGIR))
        aclist!!.add(resources.getStringArray(R.array.CHEVELLA))
        aclist!!.add(resources.getStringArray(R.array.HYDERABAD))
        aclist!!.add(resources.getStringArray(R.array.KARIMNAGAR))
        aclist!!.add(resources.getStringArray(R.array.KHAMMAM))
        aclist!!.add(resources.getStringArray(R.array.MAHABUBABAD))
        aclist!!.add(resources.getStringArray(R.array.MAHABUBANAGAR))
        aclist!!.add(resources.getStringArray(R.array.MALKAJGIRI))
        aclist!!.add(resources.getStringArray(R.array.NAGARKURNOOL))
        aclist!!.add(resources.getStringArray(R.array.NALGONDA))
        aclist!!.add(resources.getStringArray(R.array.NIZAMABAD))
        aclist!!.add(resources.getStringArray(R.array.PEDDAPALLI))
        aclist!!.add(resources.getStringArray(R.array.SECUNDERABAD))
        aclist!!.add(resources.getStringArray(R.array.WARANGAL))
        aclist!!.add(resources.getStringArray(R.array.ZAHEERABAD))
        val lclist=resources.getStringArray(R.array.LCS)
        val lcadapter=
            ArrayAdapter(this@SignupActivity,android.R.layout.simple_spinner_item,lclist)
        lcadapter.setDropDownViewResource(R.layout.spinnerbg)
        lcspinner.adapter=lcadapter
        lcspinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                lcselected=lclist[p2]
                if(p2==0){
                    val ar=ArrayList<String>()
                    ar.add("ASSEMBLY CONSTITUTION")
                    val acadapter= ArrayAdapter(this@SignupActivity,android.R.layout.simple_spinner_item,ar)
                    acadapter.setDropDownViewResource(R.layout.spinnerbg)
                    acspinner.adapter=acadapter
                    acspinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            acselected=ar[0]
                        }
                    }
                }
                else{
                    val ac= aclist!![p2-1]
                    val acadapter= ArrayAdapter(this@SignupActivity,android.R.layout.simple_spinner_item,ac)
                    acadapter.setDropDownViewResource(R.layout.spinnerbg)
                    acspinner.adapter=acadapter
                    acspinner.onItemSelectedListener=object: AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            acselected=ac[p2]
                        }
                    }
                }
            }
        }
        nametext.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                namelayout.isErrorEnabled=true
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.trim().length<3 || nametext.text.isNullOrBlank()){
                    namelayout.isErrorEnabled=true
                    namelayout.error="Name Must be atleast 3 characters"
                }
                else{
                    namelayout.isErrorEnabled=false
                }
            }
        })
        emailtext.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emaillayout.isErrorEnabled=true
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(!Emailvalidator().isEmailValid(p0.toString())){
                    emaillayout.isErrorEnabled=true
                    emaillayout.error="Enter Proper Email"
                }
                else{
                    emaillayout.isErrorEnabled=false
                }
            }
        })
        phonenumbertext.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phonenumberlayout.isErrorEnabled=true
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0!!.trim().length!=10) {
                    phonenumberlayout.isErrorEnabled = true
                    phonenumberlayout.error="Number must be 10 digits"
                }
                else{
                    phonenumberlayout.isErrorEnabled=false
                }
            }
        })
        createaccount.setOnClickListener {
            val obj=Connectivity(this@SignupActivity)
            if(obj.checkconnectivity()) {
                if (namelayout.isErrorEnabled || emaillayout.isErrorEnabled || phonenumberlayout.isErrorEnabled || lcselected.equals(lclist[0])
                    || acselected.equals("ASSEMBLY CONSTITUTION")) {
                    Snackbar.make(topsignup, "Enter details Properly", Snackbar.LENGTH_SHORT)
                        .setAction("close") {}
                        .show()
                }
                else {
                    val intent=Intent(this@SignupActivity, AddsociallinksActivity::class.java)
                    intent.putExtra("name",nametext.text.toString())
                    intent.putExtra("email",emailtext.text.toString())
                    intent.putExtra("number",phonenumbertext.text.toString())
                    intent.putExtra("lc",lcselected)
                    intent.putExtra("ac",acselected)
                    startActivity(intent)
                }
            }
            else{
                obj.showdialog()
            }
        }
    }
    fun setactionbar(){
        supportActionBar?.title="Sign Up"
        val colorDrawable:ColorDrawable= ColorDrawable(resources.getColor(R.color.colorAccent))
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