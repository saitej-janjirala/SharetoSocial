package com.saitejajanjirala.sharetosocial.utils

class Emailvalidator {
    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}