package com.example.findoraapi

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.telecom.Call.Details
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
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
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var organisers: EditText
    private lateinit var details: EditText
    private lateinit var categorySpinner: Spinner
    private lateinit var date: EditText
    private lateinit var location: EditText
    private lateinit var pickDateButton: ImageButton
    //private lateinit var autocompleteFragment: AutocompleteSupportFragment
    // Autocomplete handling


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
//Places Location auto-complete
       val apiKey = BuildConfig.MAPS_API_KEY
        Places.initialize(applicationContext, apiKey)

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
        location.setOnClickListener {
            // Launch Autocomplete for location selection
            val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
            startAutocomplete.launch(intent)
        }

        // Navigation buttons
       findViewById<Button>(R.id.btnNext).setOnClickListener {

           // Get the input date as a string
           val dateText = date.text.toString()

           // Parse the date string into a Date object
           val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) // Adjust the format as needed
           val parsedDate: Date? = dateFormat.parse(dateText)

           // Convert Date to String in ISO format before sending to the API
           val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
           val isoDateString = isoDateFormat.format(parsedDate)

           // Create Event object with the ISO formatted date string
           val event = Event(
               title = title.text.toString(),
               organisers = organisers.text.toString(),
               location = location.text.toString(),
               date = isoDateString // Use the ISO formatted date string
           )

           // Start the second activity and pass the event object
           val intent = Intent(this, MainActivity2::class.java).apply {
               putExtra("EVENT_DATA", event)
           }

           startActivity(intent)  // This was commented out, uncomment this to make the button work
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
