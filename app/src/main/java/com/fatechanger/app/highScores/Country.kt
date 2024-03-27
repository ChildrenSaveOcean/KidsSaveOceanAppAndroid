package com.fatechanger.app.highScores

class Country(var name: String, var numOfLetters: Int) : Comparable<Country> {
    var rank: Int = 0

    override fun compareTo(otherCountry: Country) = otherCountry.numOfLetters - numOfLetters

}
