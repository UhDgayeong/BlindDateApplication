package com.example.blinddate.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.blinddate.MainActivity
import com.example.blinddate.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

//    private val auth = Firebase.auth
    private lateinit var auth : FirebaseAuth
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val loginBtn : Button = findViewById(R.id.loginBtn)

        loginBtn.setOnClickListener {
            val email : TextInputEditText = findViewById(R.id.emailArea)
            val pwd : TextInputEditText = findViewById(R.id.pwdArea)

            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "로그인 실패",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}