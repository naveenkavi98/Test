package com.test.testapplication


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.test.testapplication.api.RestApi
import com.test.testapplication.model.UserResponseItem
import com.test.testapplication.utils.CommonUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        callUserDetailsApi(intent.getStringExtra("ID")!!)
    }

    private fun callUserDetailsApi(stringExtra: String) {
        RestApi.retrofit.callUserDetailsApi(stringExtra).enqueue(object :
            Callback<UserResponseItem> {
            override fun onResponse(
                call: Call<UserResponseItem>,
                response: Response<UserResponseItem>
            ) {
                Log.e("onResponse: ", "$response")
                Log.e("onResponse: ", "${response.body()}")
                if (response.isSuccessful) {
                    val responseBody = response.body()!!

                    findViewById<TextView>(R.id.txt_id).text = "Employee Id : ${responseBody.id}"
                    findViewById<TextView>(R.id.txt_name).text = "Name : "+ CommonUtils.toCamelCaseSentence(responseBody.name)
                    findViewById<TextView>(R.id.txt_email).text = "Email : " + "${responseBody.email}".toLowerCase()
                    findViewById<TextView>(R.id.txt_address).text = "${responseBody.address.street}\n${responseBody.address.city}-${responseBody.address.zipcode}"
                    findViewById<TextView>(R.id.txt_phone).text = "Phone Number: ${responseBody.phone}"
                    findViewById<TextView>(R.id.txt_company_name).text = "Company Name: ${responseBody.company.name}"
                    findViewById<TextView>(R.id.txt_company_website).text = "Company Website ${responseBody.website}"
                    findViewById<TextView>(R.id.txt_phone).setOnClickListener {
                        val dialIntent = Intent (Intent.ACTION_DIAL)
                        dialIntent.data = Uri.parse("tel:" + responseBody.phone)
                        this@DetailsActivity.startActivity(dialIntent)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponseItem>, t: Throwable) {
                Log.e("onFailure: ", "$t")
            }

        })
    }

}