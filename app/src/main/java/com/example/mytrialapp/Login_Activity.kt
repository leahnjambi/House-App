package com.example.mytrialapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    lateinit var EnterName : EditText
    lateinit var EnterEmail : EditText
    lateinit var  EnterPassword : EditText
    lateinit var Login : Button
    lateinit var progressDialog: ProgressDialog
    lateinit var mAuth :FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        EnterName = findViewById(R.id.edtName)
        EnterEmail = findViewById(R.id.edtEmail)
        EnterPassword = findViewById(R.id.edtPassword)
        Login= findViewById(R.id.btnLogin)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please Wait...")
        mAuth = FirebaseAuth.getInstance()


        // Set on ClickListener to all the Edit Text and the Button
        Login.setOnClickListener {
            var name = EnterName.text.toString().trim()
            var email = EnterEmail.text.toString().trim()
            var password = EnterPassword.text.toString().trim()
            if (name.isEmpty()|| email.isEmpty() || password.isEmpty()){
                message("Empty Fields!!!!","Please fill the input")

            }else{
                progressDialog.show()
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this,"Sign in successfully",
                            Toast.LENGTH_LONG).show()
                        mAuth.signOut()
                        finish()
                        val anza = Intent(this,MainActivity::class.java)
                        startActivity(anza)
                    }else{
                        message("Sign in failed,Try Again",it.exception!!.message.toString())
                    }
                }
            }

        }
    }
    fun message(title:String, message: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Close",null)
        alertDialog.create().show()
    }
    fun clear(){
        EnterName.setText("")
        EnterEmail.setText("")
    }
}

