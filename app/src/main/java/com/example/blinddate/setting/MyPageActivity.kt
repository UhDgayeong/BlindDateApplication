package com.example.blinddate.setting

import android.content.ContentValues.TAG
import android.content.Context
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.blinddate.MainActivity
import com.example.blinddate.R
import com.example.blinddate.auth.UserDataModel
import com.example.blinddate.utils.FirebaseRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyPageActivity : AppCompatActivity() {

    val uid = Firebase.auth.currentUser?.uid.toString()
    val TAG = "MyPageActivity"

    lateinit var myUid : TextView
    lateinit var myNickname : TextView
    lateinit var myAge : TextView
    lateinit var myCity : TextView
    lateinit var myGender : TextView
    lateinit var myImage : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        myUid = findViewById(R.id.myUid)
        myNickname = findViewById(R.id.myNickname)
        myAge = findViewById(R.id.myAge)
        myCity = findViewById(R.id.myCity)
        myGender = findViewById(R.id.myGender)
        myImage = findViewById(R.id.myImage)

        getMyData()
    }

    private fun getMyData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                Log.d(TAG, dataSnapshot.toString())
                val data = dataSnapshot.getValue(UserDataModel::class.java)
                print(data)

                myUid.text = data?.uid
                myNickname.text = data?.nickname
                myAge.text = data?.age
                myCity.text = data?.city
                myGender.text = data?.gender

                val imageRef = Firebase.storage.reference.child(data!!.uid + ".png")

                imageRef.downloadUrl.addOnCompleteListener(OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(baseContext)
                            .load(task.result)
                            .into(myImage)
                    }
                })
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FirebaseRef.userInfoRef.child(uid).addValueEventListener(postListener)
    }
}