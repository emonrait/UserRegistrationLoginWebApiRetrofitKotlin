package com.example.ugerregistration.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import com.example.ugerregistration.R
import com.example.ugerregistration.model.ApiResponse
import com.example.ugerregistration.model.ApiService
import com.example.ugerregistration.model.getProgressDrawable
import com.example.ugerregistration.model.loadImage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_update.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {
    var updateImageLink = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val id = getIntent().getStringExtra("id")
        val name = getIntent().getStringExtra("name")
        val mobile = getIntent().getStringExtra("mobile")
        val password = getIntent().getStringExtra("password")
        val imagelink = getIntent().getStringExtra("imagelink")



        input_id.setText(id)
        input_name.setText(name)
        input_mobile.setText(mobile)
        input_password.setText(password)

        picUpdate.loadImage(
            imagelink, getProgressDrawable(picUpdate.context)
        )

        picUpdate.setOnClickListener {
            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAutoZoomEnabled(true)
                .setAspectRatio(4, 4) // .setRequestedSize(300,800)
                .start(this@UpdateActivity)

        }

        btn_update.setOnClickListener {
            updateUser()
        }

    }

    fun updateUser() {
        var apiinstance = ApiService()
        var id = input_id.text.toString().trim()
        var name = input_name.text.toString().trim()
        var mobile = input_mobile.text.toString().trim()
        var password = input_password.text.toString().trim()
        //var imagelink = input_imagelink.text.toString()
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
//put something inside the map, could be null
            //put something inside the map, could be null

            jsonParams["id"] = id
            jsonParams["name"] = name
            jsonParams["mobile"] = mobile
            jsonParams["password"] = password
            //jsonParams["imagelink"] = imagelink

            val body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                JSONObject(jsonParams).toString()
            )

            var call: Call<ApiResponse> = apiinstance.userUpdate(body)
            call.enqueue(object : Callback<ApiResponse> {
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        startActivity(Intent(applicationContext, DashboardActivity::class.java))
                        var s = response.body().toString()
                        Toast.makeText(
                            applicationContext,
                            s,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Something Went Wrong" + response.errorBody().toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            })

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                updateImageLink = resultUri.toString()
                picUpdate.setImageURI(resultUri)
                Toast.makeText(
                    applicationContext,
                    updateImageLink,
                    Toast.LENGTH_LONG
                ).show()
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

}