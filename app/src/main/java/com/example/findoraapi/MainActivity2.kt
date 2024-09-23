package com.example.findoraapi

import Event
//import android.annotation.SuppressLint
import android.content.Intent
//import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.LocalTime

class MainActivity2 : AppCompatActivity() {

    private lateinit var event: Event

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
    private val PICK_IMAGE_REQUEST = 1


    private lateinit var imageView: ImageView
    private lateinit var startTimeTP: EditText//TimePicker
    private lateinit var endTimeTP: EditText //TimePicker
    private lateinit var eventType: Spinner


   // @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

       imageView = findViewById(R.id.imageView)

        // Set up spinner
        val spinner = findViewById<Spinner>(R.id.eventType)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.eventType,
            R.layout.simple_spinner_item // Custom item layout
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        event = intent.getParcelableExtra("EVENT_DATA") ?: Event()

        startTimeTP = findViewById(R.id.etstarttime)
        endTimeTP = findViewById(R.id.etendtime)
        eventType = spinner

        findViewById<Button>(R.id.button).setOnClickListener {
                openImagePicker()
            }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnUploadevent).setOnClickListener{
//event.startTime = startTimeTP.

        }

    }
    private fun openImagePicker() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        this.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    //@Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageView.setImageURI(imageUri)
        }
    }
}