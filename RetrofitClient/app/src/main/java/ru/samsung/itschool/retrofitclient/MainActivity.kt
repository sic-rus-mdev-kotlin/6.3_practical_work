package ru.samsung.itschool.retrofitclient

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    val connectionURL: String = "http://192.168.49.112:8080"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val et_url: TextView = findViewById(R.id.url)
        et_url.text = connectionURL;
        val firstname: EditText = findViewById(R.id.firstname)
        val lastname: EditText = findViewById(R.id.lastname)
        val send: Button = findViewById(R.id.send)
        val get: Button = findViewById(R.id.get)
        val receive: Button = findViewById(R.id.receive)
        val result: TextView = findViewById(R.id.result)
        val retrofit = Retrofit.Builder()
            .baseUrl(connectionURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: UserController = retrofit.create(UserController::class.java)
        send.setOnClickListener(View.OnClickListener {
            Thread() {
                run {
                    val call: Call<Void> = service.hello(
                        User(
                            firstname.text.toString(),
                            lastname.text.toString()
                        )
                    )
                    call.execute()
                }
            }.start()
        })
        receive.setOnClickListener(View.OnClickListener {
            result.text=""
            val call: Call<List<User>> = service.list()
            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val list: List<User>? = response.body()
                        var strList: String = ""
                        list?.forEach { strList += it.firstName + "  " + it.lastName + "\n" }
                        result.text = strList
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.d("RetrofitClient","Receive data from server problem ")
                }
            })
        })
        get.setOnClickListener(View.OnClickListener {
            result.text=""
            val call: Call<User> =
                service.get(firstname.text.toString(), lastname.text.toString())
            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user: User? = response.body()
                        result.text = "найден: " + user?.firstName + " " + user?.lastName
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("RetrofitClient","Receive data from server problem ")
                }
            })
        })
    }
}