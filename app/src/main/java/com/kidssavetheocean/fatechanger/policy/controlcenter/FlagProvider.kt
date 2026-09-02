package com.kidssavetheocean.fatechanger.policy.controlcenter

object CountryFlagProvider {
    /** * Returns the flag emoji for the given ISO 3166-1 alpha-2 country code. * Normalizes to uppercase. * Falls back to 🌐 for unknown/invalid country codes. */
    fun flagFor(countryCode: String): String {
        val normalized = countryCode.uppercase().trim()

        if (normalized.length != 2 || normalized.any { it !in 'A'..'Z' }) {
            return "\uD83C\uDF10" // 🌐
        }

        return normalized.map { char ->
            String(Character.toChars(0x1F1E6 + (char - 'A')))
        }.joinToString("")
    }
}