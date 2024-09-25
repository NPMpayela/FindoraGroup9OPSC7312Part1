import com.example.findoraapi.Event
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {

    @GET("api/events")
    fun getEvents(): Call<List<Event>>  // Changed to List<Event> for type safety

    @GET("api/events/{id}")
    fun getEventById(@Path("id") eventId: Int): Call<Event>

    @POST("api/Event")  // Ensure to include "api/" here if it's part of the base URL
    fun createEvent(@Body event: Event): Call<Void>
}
