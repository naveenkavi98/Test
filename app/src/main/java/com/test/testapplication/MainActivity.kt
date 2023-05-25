package com.test.testapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.test.testapplication.adapter.UserAdapter
import com.test.testapplication.api.RestApi
import com.test.testapplication.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    val check = true
        
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this@MainActivity.registerReceiver(mConnReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        callLoginApi()
    }

    private fun callLoginApi() {
        RestApi.retrofit.callUserListApi().enqueue( object :
            Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                Log.e( "onResponse: ", "$response")
                Log.e( "onResponse: ", "${response.body()}")
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val adapter = UserAdapter(responseBody)
                    adapter.setOnClicked(object : UserAdapter.OnClicked{
                        override fun clicked(id: String) {
                            val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                            intent.putExtra("ID",id)
                            startActivity(intent)
                        }

                    })
                    findViewById<RecyclerView>(R.id.rcv_list).adapter =  adapter
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e( "onFailure: ", "$t")
            }

        })
    }

    private val mConnReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            /*  boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);*/
            val currentNetworkInfo =
                intent.getParcelableExtra<NetworkInfo>(ConnectivityManager.EXTRA_NETWORK_INFO)
            //   NetworkInfo otherNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
            if (currentNetworkInfo!!.isConnected) {
                if (check) {
                    
                }
                Toast.makeText(this@MainActivity, "Connected :) ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "No Internet :( ", Toast.LENGTH_LONG).show();
            }
        }
    }

}