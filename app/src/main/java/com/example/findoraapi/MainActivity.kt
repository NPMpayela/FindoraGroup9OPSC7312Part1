package com.example.findoraapi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.findoraapi.R.id.datepickbtn
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import okhttp3.OkHttpClient



class MainActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var organisers: EditText
    private lateinit var details: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var date: EditText
    private lateinit var location: EditText
    private lateinit var pickDateButton: ImageButton

    private lateinit var eventapiservice: EventService

    private val startAutocomplete =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val place = Autocomplete.getPlaceFromIntent(intent)
                    location.setText("${place.displayName}, ${place.location}") // Use new fields
                    Log.i("MainActivity", "Place: ${place.displayName}, ${place.id}")
                } else {
                    Toast.makeText(this, "No address found. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Autocomplete canceled.", Toast.LENGTH_SHORT).show()
                Log.i("MainActivity", "User canceled autocomplete")
            } else {
                Toast.makeText(this, "Error occurred while fetching the place.", Toast.LENGTH_SHORT).show()
                Log.e("MainActivity", "Error with Autocomplete resultCode: ${result.resultCode}")
            }
        }



    // @SuppressLint("MissingInflatedId")
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Event Data
        title = findViewById(R.id.ettTitle)
        organisers = findViewById(R.id.ettHosts)
        categorySpinner = findViewById(R.id.spinner)
        details=findViewById(R.id.ettDetails)
        location=findViewById(R.id.etLocation)
        date = findViewById(R.id.ettDate)

       pickDateButton = findViewById(datepickbtn)

       pickDateButton.setOnClickListener {
           showDatePickerDialog(date)
       }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:4659")
          //  .client(getUnsafeOkHttpClient())// Add your backend URL here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        eventapiservice = retrofit.create(EventService::class.java)


//Places Location auto-complete
      // val apiKey = BuildConfig.MAPS_API_KEY
        //Places.initialize(applicationContext, apiKey)

        // Set up spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.eventSpinner,
            R.layout.simple_spinner_item // Custom item layout
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Location EditText
        location = findViewById(R.id.etLocation)
       // locimgbtn=findViewById(R.id.imageView3)
        location.setOnClickListener{
            // Launch Autocomplete for location selection
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
            startAutocomplete.launch(intent)
        }

        findViewById<Button>(R.id.btnNext).setOnClickListener {

            // Get the input date as a string
           val dateText = date.text.toString()
//
//            // Parse the date string into a Date object
           val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val parsedDate: Date? = dateFormat.parse(dateText)
//
            // Convert Date to String in ISO format before sending to the API
            val isoDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val isoDateString = isoDateFormat.format(parsedDate)

            // Create Event object with the ISO formatted date string
            val event = com.example.findoraapi.Event(
                _id = null,
                eventName =title.text.toString(),
                organisers = organisers.text.toString(),
                category= categorySpinner.selectedItem.toString(),
                location = location.text.toString(),
                date = date.text.toString(),
                details=details.text.toString()
            )

            // Send the event object to the API using Retrofit
//            eventapiservice = RetrofitClient.instance
//            val call = eventapiservice.createEvent(event)

            //call.enqueue(object
            eventapiservice.createEvent(event).enqueue(object: retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Event created successfully", Toast.LENGTH_SHORT).show()

                        // Use this@MainActivity to refer to the activity context
                        val intent = Intent(this@MainActivity, MainActivity2::class.java)
                        startActivity(intent)
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("API Error", "Response code: ${response.code()} - Message: ${response.errorBody()?.string()}")
                        Toast.makeText(this@MainActivity, "Failed to create event. Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        Log.d("Event Data", event.toString())

                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

            // Start the second activity and pass the event object
//            val intent = Intent(this, MainActivity2::class.java).apply {
//                putExtra("EVENT_DATA", event)
//            }

            startActivity(intent)
        }


        findViewById<Button>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }


    }

    private fun showDatePickerDialog(dateEditText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the EditText with the selected date
                val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dateEditText.setText(formattedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

}
