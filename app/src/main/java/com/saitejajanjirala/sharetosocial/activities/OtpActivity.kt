package com.saitejajanjirala.sharetosocial.activities

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saitejajanjirala.sharetosocial.R
import com.saitejajanjirala.sharetosocial.models.Rewards
import com.saitejajanjirala.sharetosocial.models.User
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class OtpActivity : AppCompatActivity() {
    private var xnumber:String?=null
    private var xname:String?=null
    private var xemail:String?=null
    private var xlc:String?=null
    private var xac:String?=null
    private var xfb:String?=null
    private var xtwitter:String?=null
    private var xwhatsapp:String?=null
    private var xfrom:String?=null
    private var xreferal:String?=null
    private var resendtoken:PhoneAuthProvider.ForceResendingToken?=null
    private var xverificationid:String?=null
    private var mauth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        setactionbar()
        xfrom=intent.getStringExtra("from")
        if(xfrom.equals("login")){
            xnumber=intent.getStringExtra("number")
            sendotp()
        }
        else if(xfrom.equals("signup")) {
            xname = intent.getStringExtra("name")
            xemail = intent.getStringExtra("email")
            xnumber = intent.getStringExtra("number")
            xlc = intent.getStringExtra("lc")
            xac = intent.getStringExtra("ac")
            xfb = intent.getStringExtra("fb")
            xtwitter = intent.getStringExtra("twitter")
            xwhatsapp = intent.getStringExtra("whatsapp")
            xreferal = intent.getStringExtra("referal")
            sendotp()
        }
        mauth= FirebaseAuth.getInstance()
        Submit.setOnClickListener {
            val otptxt=otptext.text.toString()
            if(otptxt.length==6){
                verifyandsignin(otptxt)
            }
        }
    }
    fun sendotp(){
        val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                if(credential.smsCode!=null) {
                    otptext.setText(credential.smsCode)
                }
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@OtpActivity,e.message.toString(),
                    Toast.LENGTH_LONG).show()
            }
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                xverificationid=verificationId
                Toast.makeText(this@OtpActivity,"code sent",Toast.LENGTH_SHORT).show()
                resendtoken=token
            }
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$xnumber!!",        // Phone number to verify
            60,                 // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this,               // Activity (for callback binding)
            mCallbacks
        );        // OnVerificationStateChangedCallbacks

    }
    fun verifyandsignin(code:String){
        val phoneAuthCredential:PhoneAuthCredential=PhoneAuthProvider
            .getCredential(xverificationid!!,code)
        signinwithcredentials(phoneAuthCredential)
    }
    fun signinwithcredentials(authCredential: PhoneAuthCredential){
        mauth!!.signInWithCredential(authCredential).addOnSuccessListener {
            val uid = it.user!!.uid
            ifaccountopened(uid)
        }
        .addOnFailureListener {
            Toast.makeText(this@OtpActivity,it.message.toString(),Toast.LENGTH_SHORT).show()
        }
    }
    fun setactionbar(){
        supportActionBar?.title="OTP"
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
    fun ifaccountopened(uid:String){
        val dbref=FirebaseDatabase.getInstance().reference.child("users").child(uid)
        dbref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                val bool=snapshot.exists()
                doit(bool,uid)
            }
        })

    }
    fun doit(bool:Boolean,uid:String){
        if(xfrom.equals("login")){
            if(!bool) {
                Toast.makeText(this@OtpActivity,"No account found with this number",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@OtpActivity,"Welcome",Toast.LENGTH_SHORT).show()
                savetosharedprefs(uid)
            }
        }
        else if(xfrom.equals("signup")) {
            if (!bool) {
                val dialog=ProgressDialog(this@OtpActivity)
                dialog.setTitle("Please Wait...")
                dialog.create()
                dialog.show()
                if(xreferal!!.isBlank() || xreferal!!.isEmpty()){
                    createuserdata(uid)
                    dialog.dismiss()
                }
                else{
                    val db = FirebaseDatabase.getInstance().reference.child("users")
                    db.addValueEventListener(object:ValueEventListener{
                        override fun onCancelled(error: DatabaseError) {
                            dialog.dismiss()
                        }
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if(snapshot.exists()){
                                var value:Boolean=false
                                var user:User?=null
                                for(i in snapshot.children){
                                    user=i.getValue(User::class.java)
                                    if(user!!.referalcode.equals(xreferal!!)){
                                        value=true
                                        break
                                    }
                                }
                                if(value){
                                    if (user != null) {
                                        user.uid?.let {
                                          val update=FirebaseDatabase.getInstance().reference.child("rewards").child(it)
                                              update.addValueEventListener(object:ValueEventListener{
                                                  override fun onCancelled(error: DatabaseError) {
                                                  }
                                                  override fun onDataChange(snapshot: DataSnapshot) {
                                                      val rewards:Rewards?=snapshot.getValue(Rewards::class.java)
                                                      val map=HashMap<String,Any?>()
                                                      map["rewardpoints"]=rewards!!.rewardpoints!!.plus(20)
                                                      update.updateChildren(map)
                                                      createuserdata(uid)
                                                      dialog.dismiss()
                                                  }
                                              })
                                        }
                                    }
                                    else{
                                        dialog.dismiss()
                                    }
                                }
                            }
                            else{
                                dialog.dismiss()
                                showerrordialog("Invalid referal code")
                            }
                        }

                    })
                }

            } else {
                Toast.makeText(
                    this@OtpActivity,
                    "Account Already Exists with this phone number",
                    Toast.LENGTH_SHORT
                ).show()
                Handler().postDelayed({
                    super.onBackPressed()
                    finish()
                }, 500)
            }
        }
        //have to change to hashmap
    }
    fun createuserdata(uid: String){
        val usersmap = HashMap<String, Any>()
        usersmap["name"] = xname!!
        usersmap["email"] = xemail!!
        usersmap["number"] = xnumber!!
        usersmap["lc"] = xlc!!
        usersmap["ac"] = xac!!
        usersmap["fb"] = xfb!!
        usersmap["twitter"] = xtwitter!!
        usersmap["whatsapp"] = xwhatsapp!!
        usersmap["referalcode"] = uid.subSequence(uid.length - 7, uid.length - 1)
        usersmap["referedby"]=xreferal!!
        val db=FirebaseDatabase.getInstance().reference.child("users").child(uid)
        db.updateChildren(usersmap).addOnSuccessListener {
            val rewardsmap=HashMap<String,Any>()
            rewardsmap["rewardpoints"]=0
            rewardsmap["level"]="Beginner"
            FirebaseDatabase.getInstance().reference.child("rewards")
                .child(uid)
                .updateChildren(rewardsmap)
                .addOnSuccessListener {
                    Toast.makeText(this@OtpActivity, "Success", Toast.LENGTH_SHORT).show()
                    savetosharedprefs(uid)
                }
        }.addOnFailureListener {
                Toast.makeText(this@OtpActivity, "Error", Toast.LENGTH_SHORT).show()
            }
    }
    fun showerrordialog(msg:String){
        val dialog=AlertDialog.Builder(this@OtpActivity)
            .setMessage(msg)
            .setPositiveButton("ok"){text,listener->}
            .create()
            .show()
    }
    fun savetosharedprefs(uid:String){
        val prefs=getSharedPreferences("users",Context.MODE_PRIVATE)
        prefs.edit().putString("uid",uid).apply()
        Handler().postDelayed({
            val intent=Intent(this@OtpActivity,MainscreenActivity::class.java)
            intent.putExtra("uid",uid)
            startActivity(intent)
        },500)
    }

}