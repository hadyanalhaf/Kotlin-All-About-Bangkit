package com.example.borneoapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var rvTrip: RecyclerView
    private val list = ArrayList<Trip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvTrip = findViewById(R.id.rv_borneotrip)
        rvTrip.setHasFixedSize(true)

        list.addAll(getListTrip())
        showRecyclerList()
    }
    private fun getListTrip(): ArrayList<Trip> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataLocs = resources.getStringArray(R.array.location)
        val listHero = ArrayList<Trip>()
        for (i in dataName.indices) {
            val hero = Trip(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1),dataLocs[i])
            listHero.add(hero)
        }
        return listHero
    }

    private fun showRecyclerList() {
        rvTrip.layoutManager = LinearLayoutManager(this)
        val listTripAdapter = ListTripAdapter(list)
        rvTrip.adapter = listTripAdapter

        listTripAdapter.setOnItemClickCallback(object : ListTripAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Trip) {
                showSelectedTrip(data)
            }
        })
    }
    private fun showSelectedTrip(trip: Trip) {

        val moveWithObjectIntent = Intent(this@MainActivity, Details::class.java)
        moveWithObjectIntent.putExtra(Details.EXTRA_TRIP, trip)
        startActivity(moveWithObjectIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
     override fun onOptionsItemSelected(menu: MenuItem): Boolean {
        when (menu.itemId) {
            R.id.action_about -> {
                val intent = Intent(this, About::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(menu)
        }
    }
}