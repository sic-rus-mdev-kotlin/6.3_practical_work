package ru.samsung.itschool.myapplication


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EchoController {
    @GET("/get")
    fun hello(@Query("foo") foo:String, @Query("bar") bar:String): Call<MainData>
}