package com.example.findoraapi

//import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import kotlinx.coroutines.CoroutineStart
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalTime
import kotlin.io.encoding.Base64

class MainActivity2 : AppCompatActivity() {

    private lateinit var event: Event

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
    private val PICK_IMAGE_REQUEST = 1
    private val eventList = arrayListOf<Event>()

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

     //   event = intent.getParcelableExtra("EVENT_DATA") ?: Event()

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

       findViewById<Button>(R.id.btnUploadevent).setOnClickListener {

           // Get start and end time from the EditText fields**
//           event.startTime = startTimeTP.text.toString() // Assuming startTimeTP is an EditText
//           event.endTime = endTimeTP.text.toString() // Assuming endTimeTP is an EditText
//
//           // Get the selected event type from the Spinner**
//           event.eventType = eventType.selectedItem.toString()

           // Get the Bitmap from the ImageView (assuming the image was previously set)**
//           val drawable = imageView.drawable
//           if (drawable != null) {
//               // Convert the drawable to Bitmap and store it in the event object
//               val bitmap = (drawable as BitmapDrawable).bitmap
//               event.imageBitmap = bitmap
//           }

           // Create intent to move to HomePage with eventList
           val intent = Intent(this, HomePage::class.java)
           //intent.putParcelableArrayListExtra("event_list", eventList)
           startActivity(intent)
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

//    fun convertImageToBase64(bitmap: Bitmap): String {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//        return Base64.encodeToString(byteArray, CoroutineStart.DEFAULT)
//    }
}