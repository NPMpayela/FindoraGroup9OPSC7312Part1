package com.example.findoraapi

import com.example.findoraapi.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {

    @GET("api/events")
    fun getEvents(): Call<List<Event>>

    @GET("api/events/{id}")
    fun getEventById(@Path("id") eventId: Int): Call<Event>

    @POST("api/Event")
    @Headers("Content-Type: application/json")
    fun createEvent(@Body event: Event): Call<Void>


}
