package ru.samsung.itschool.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.samsung.itschool.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //https://postman-echo.com/get?foo1=bar1&foo2=bar2
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.49.108:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service: EchoController = retrofit.create(EchoController::class.java)
        val call: Call<MainData> = service.hello("aaa", "bbb")
        Thread(){
            kotlin.run {
                val userResponse: Response<MainData> = call.execute()
                val result: MainData? = userResponse.body()
                Log.d("!!!",Gson().toJson(result))
            }
        }.start()

    }
}