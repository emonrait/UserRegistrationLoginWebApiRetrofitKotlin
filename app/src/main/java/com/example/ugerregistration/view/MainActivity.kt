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
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_login.setOnClickListener {
            loginUser()
            //startActivity(Intent(this, DashboardActivity::class.java))

        }
        link_register.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }


    }

    fun loginUser() {
        var apiinstance = ApiService()
        var mobile = input_userid.text.toString().trim()
        var password = input_password.text.toString().trim()

        val jsonParams: MutableMap<String?, Any?> = ArrayMap()
//put something inside the map, could be null
        //put something inside the map, could be null

        jsonParams["mobile"] = mobile
        jsonParams["password"] = password

        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            JSONObject(jsonParams).toString()
        )

        if (mobile.isEmpty()) {
            input_userid.error = "Mobile No Required"
            input_userid.requestFocus()
        }
        if (password.isEmpty()) {
            input_password.error = "Password Required"
            input_password.requestFocus()
        } else {

            var call: Call<ApiResponse> = apiinstance.login(body)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ApiResponse>,
                    response: Response<ApiResponse>
                ) {
                    if (response.isSuccessful) {

                        val intent = Intent(
                            applicationContext,
                            DashboardActivity::class.java
                        ) //not application context
                        intent.putExtra("id", response.body()?.id.toString())
                        intent.putExtra("name", response.body()?.name.toString())
                        intent.putExtra("mobile", response.body()?.mobile.toString())
                        intent.putExtra("password", response.body()?.password.toString())
                        intent.putExtra("imagelink", response.body()?.imagelink.toString())
                        startActivity(intent)

                        var s = response.body().toString()
                        Toast.makeText(
                            applicationContext,
                            s,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Userid & Password is Wrong!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }

            })

        }


    }
}