package com.kidssaveocean.fatechanger.highScores

class Country(var name: String, var numOfLetters: Int) : Comparable<Country> {
    var rank: Int = 0

    override fun compareTo(otherCountry: Country) = otherCountry.numOfLetters - numOfLetters

}
