package com.example.covidvaccinetracker

data class DataModelClass(

    val centerName: String,
    val centerAddress: String,
    val centerFromTime: String,
    val centerToTime: String,
    var fee_type: String,
    var ageLimit: Int,
    var vaccineName: String,
    var availableCapacity: Int
)
