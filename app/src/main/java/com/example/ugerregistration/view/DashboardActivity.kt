package com.example.ugerregistration.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.ugerregistration.R
import com.example.ugerregistration.model.getProgressDrawable
import com.example.ugerregistration.model.loadImage
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val id = getIntent().getStringExtra("id")
        val name = getIntent().getStringExtra("name")
        val mobile = getIntent().getStringExtra("mobile")
        val password = getIntent().getStringExtra("password")
        val imagelink = getIntent().getStringExtra("imagelink")
        //val imagelink="https://img.favpng.com/9/25/24/computer-icons-instagram-logo-sticker-png-favpng-LZmXr3KPyVbr8LkxNML458QV3_t.jpg"


        user_id.setText(id)
        user_name.setText(name)
        user_mobile.setText(mobile)
        user_password.setText(password)


        pic.loadImage(
            imagelink, getProgressDrawable(pic.context)
        )



        btnUpdate.setOnClickListener {

            val intent = Intent(applicationContext, UpdateActivity::class.java)
            intent.putExtra("id", id.toString())
            intent.putExtra("name", name.toString())
            intent.putExtra("mobile", mobile.toString())
            intent.putExtra("password", password.toString())
            intent.putExtra("imagelink", imagelink.toString())
            startActivity(intent)

        }
    }
}