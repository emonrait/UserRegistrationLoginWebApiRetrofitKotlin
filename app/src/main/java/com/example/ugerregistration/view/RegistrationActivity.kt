package com.example.ugerregistration.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.collection.ArrayMap
import com.example.ugerregistration.R
import com.example.ugerregistration.model.ApiResponse
import com.example.ugerregistration.model.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.input_password
import kotlinx.android.synthetic.main.activity_registration.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        btn_register.setOnClickListener {
            saveUser()

        }
        link_login.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    fun saveUser() {
        var apiinstance = ApiService()
        var name = input_name.text.toString().trim()
        var mobile = input_mobile.text.toString().trim()
        var password = input_password.text.toString().trim()
        var imagelink =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Bank_Asia_Limited.svg/1200px-Bank_Asia_Limited.svg.png"
        if (name.isEmpty()) {
            input_name.error = "Name Required"
            input_name.requestFocus()
        }
        if (mobile.isEmpty()) {
            input_mobile.error = "Mobile No Required"
            input_mobile.requestFocus()
        }
        if (password.isEmpty()) {
            input_password.error = "Password Required"
            input_password.requestFocus()
        } else {

            val jsonParams: MutableMap<String?, Any?> = ArrayMap()

            jsonParams["name"] = name
            jsonParams["mobile"] = mobile
            jsonParams["password"] = password
            jsonParams["imagelink"] = imagelink

            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                JSONObject(jsonParams).toString()
            )

            var call: Call<ApiResponse> = apiinstance.createUser(body)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        var s = response.body().toString()
                        Toast.makeText(
                            applicationContext,
                            s,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "This user is Already Exist",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })

        }


    }
}