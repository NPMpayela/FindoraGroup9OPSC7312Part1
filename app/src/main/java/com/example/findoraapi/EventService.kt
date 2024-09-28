package com.example.findoraapi

import com.example.findoraapi.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {

//    @GET("Events")
//    fun getEvents(): Call<List<Event>>

    @GET("Events")
    fun getEvents(): Call<EventResponse>

    @GET("api/events/{id}")
    fun getEventById(@Path("id") eventId: Int): Call<Event>

    @POST("CreateEvent")
    @Headers("Content-Type: application/json")
    fun createEvent(@Body event: Event): Call<Void>


}
