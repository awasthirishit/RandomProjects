package com.example.covidvaccinetracker

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var centersRV: RecyclerView
    lateinit var centerRVAdapter: Adapter
    lateinit var centerList: List<DataModelClass>
    lateinit var pincode : EditText
    lateinit var searchButton: Button
    lateinit var loadingPB : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        pincode = findViewById(R.id.pincode)
        searchButton = findViewById(R.id.search)
        loadingPB = findViewById(R.id.progressbar)

        centersRV = findViewById(R.id.recycler)
        centerList = ArrayList<DataModelClass>()


        searchButton.setOnClickListener {
            var image : ImageView = findViewById(R.id.image)

            val pinCode = pincode.text.toString()


            if (pinCode.length != 6) {
                Toast.makeText(this@MainActivity, "Please enter valid pin code", Toast.LENGTH_SHORT)
                    .show()
            } else {
                (centerList as ArrayList<DataModelClass>).clear()

                val c = Calendar.getInstance()

                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                //date picker dailog
                val dpd = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        loadingPB.setVisibility(View.VISIBLE)
                        image.setVisibility(View.GONE)

                        val dateStr: String = """$dayOfMonth - ${monthOfYear + 1} - $year"""
                        getAppointments(pinCode, dateStr)
                    },
                    year,
                    month,
                    day
                )
                // calling a method to display
                // our datepicker dialog.
                dpd.show()
            }
        }


    }
    // below is the method for getting data from API.
    private fun getAppointments(pinCode: String, date: String) {
        val url =
            "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=" + pinCode + "&date=" + date
        val queue = Volley.newRequestQueue(this@MainActivity)

        val request =
            JsonObjectRequest(
                Request.Method.GET, url, null, { response ->

                    loadingPB.setVisibility(View.GONE)

                    try {

                        val centerArray = response.getJSONArray("centers")

                        if (centerArray.length().equals(0)) {
                            Toast.makeText(this, "No Center Found", Toast.LENGTH_SHORT).show()
                        }
                        for (i in 0 until centerArray.length()) {
                            val centerObj = centerArray.getJSONObject(i)


                            val centerName: String = centerObj.getString("name")
                            val centerAddress: String = centerObj.getString("address")
                            val centerFromTime: String = centerObj.getString("from")
                            val centerToTime: String = centerObj.getString("to")
                            val fee_type: String = centerObj.getString("fee_type")

                            val sessionObj = centerObj.getJSONArray("sessions").getJSONObject(0)
                            val ageLimit: Int = sessionObj.getInt("min_age_limit")
                            val vaccineName: String = sessionObj.getString("vaccine")
                            val avaliableCapacity: Int = sessionObj.getInt("available_capacity")


                            val center = DataModelClass(
                                centerName,
                                centerAddress,
                                centerFromTime,
                                centerToTime,
                                fee_type,
                                ageLimit,
                                vaccineName,
                                avaliableCapacity
                            )

                            centerList = centerList + center
                        }

                        centerRVAdapter = Adapter(centerList)

                        centersRV.layoutManager = LinearLayoutManager(this)

                        centersRV.adapter = centerRVAdapter

                        centerRVAdapter.notifyDataSetChanged()

                    } catch (e: JSONException) {

                        e.printStackTrace();
                    }
                },
                { error ->
                    Log.e("TAG", "RESPONSE IS $error")
                    Toast.makeText(
                        this@MainActivity,
                        "Fail to get response",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                })
        queue.add(request)

    }
}
