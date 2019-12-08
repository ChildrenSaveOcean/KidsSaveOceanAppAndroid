package com.kidssaveocean.fatechanger.extensions

import android.location.Location
import com.kidssaveocean.fatechanger.firebase.model.CountryModel

fun Location.getCountryByLocation(countries : Iterable<CountryModel>) : CountryModel? {
    var closestCoutry : CountryModel? = null
    var minDistance = 0.0
    val startPoint = Location("myLocation")
    startPoint.latitude = this.latitude
    startPoint.longitude = this.longitude
    for (country in countries) {
        val endPoint = Location("capitalLocation")
        endPoint.latitude = country.latitude
        endPoint.longitude = country.longitude
        val distance = startPoint.distanceTo(endPoint).toDouble()
        if (minDistance == 0.0 || distance < minDistance) {
            minDistance = distance
            closestCoutry = country
        }
    }
    return closestCoutry
}