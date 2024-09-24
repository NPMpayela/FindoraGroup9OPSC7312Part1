package com.example.findoraapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomePage : AppCompatActivity() {

    private lateinit var eventRecyclerView: RecyclerView
    //private lateinit var eventAdapter: FindoraEventAdapter
    private var eventList: ArrayList<Event> = ArrayList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
       Thread.sleep(3000)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_page)


        // Retrieve the event data
//        val event = intent.getParcelableExtra<Event>("EVENT_DATA")
//
//        // Create a list with the event if you only have one event
//        var eventList = listOf(event)
//
//        // Find RecyclerView and set it up
//        val recyclerView: RecyclerView = findViewById(R.id.eventRecyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = FindoraEventAdapter(eventList)

       //  Your main activity logic goes here
//        val spinner = findViewById<Spinner>(R.id.finderSpinner)
//        val adapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.finderSpinner,
//            R.layout.simple_spinner_item // Custom item layout
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinner.adapter = adapter

     //   eventList = intent.getParcelableArrayListExtra("event_list") ?: ArrayList()




        val spinner2 = findViewById<Spinner>(R.id.eventSpinner)
        val adapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.eventCategories,
            R.layout.simple_spinner_item // Custom item layout
        )
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = adapter2

        val spinner3 = findViewById<Spinner>(R.id.daytimeSpinner)
        val adapter3 = ArrayAdapter.createFromResource(
            this,
            R.array.daytimeSpinner,
            R.layout.simple_spinner_item // Custom item layout
        )
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner3.adapter = adapter3


        findViewById<Button>(R.id.btnHostnext).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Get the event list from the intent
        //eventList = intent.getParcelableArrayListExtra("event_list") ?: ArrayList()

        // Set up the RecyclerView
        eventRecyclerView = findViewById(R.id.eventRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(this)

       // eventAdapter = FindoraEventAdapter(eventList)
        //eventRecyclerView.adapter = eventAdapter
    }
}