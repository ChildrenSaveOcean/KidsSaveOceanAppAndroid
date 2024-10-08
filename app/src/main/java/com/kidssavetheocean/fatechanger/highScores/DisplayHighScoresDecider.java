package com.kidssavetheocean.fatechanger.highScores;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayHighScoresDecider {

    public static List<Country> fetchCountriesToBeDisplay (List<Country> countries, String yourCountryName) {
        // Sorts the countries in descending order by number of letters sent.
        Collections.sort(countries);

        // Checks to see where your country is in the rankings.
        int yourCountryIndex = -1;
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getName().equalsIgnoreCase(yourCountryName)) {
                yourCountryIndex = i;
                break;
            }
        }

        // If your country did send a single letter sent OR if the number of top countries who sent at least 1 letter is 10 or less,
        // then show all of the countries in the top 10. (Not gurantee to have 10 countries present)
        if (yourCountryIndex == -1 || countries.size() <= 10) {
            for (int i = 0; i < countries.size(); i++) {
                countries.get(i).setRank(i + 1);
            }
            return countries;
        }

        // If your country sent at least 1 letter (from previous check) and is in the top 7,
        // and there are 11 or more countries (from previous check), show the top 10.
        else if (yourCountryIndex <= 6) {
            for (int i = 0; i < 10; i++) {
                countries.get(i).setRank(i + 1);
            }
            return countries.subList(0, 10);
        }

        // If none of the above conditions are true, and your country is in the bottom 4 and has send at least 1 letter,
        // then show the top 3 countries and the bottom 7 countries.
        else if (yourCountryIndex >= countries.size() - 4) {
            List<Country> solution = new ArrayList<>(10);

            for (int i = 0; i < 3; i++) {
                countries.get(i).setRank(i + 1);
            }
            solution.addAll(countries.subList(0, 3));

            for (int i = countries.size() - 7; i < countries.size(); i++) {
                countries.get(i).setRank(i + 1);
            }
            solution.addAll(countries.subList(countries.size() - 7, countries.size()));

            return solution;
        }

        // Else, your country is somewhere in the middle so show top 3 countries, then the immediate 3 counties above your country,
        // your country and then the next 3 countries that are behind your country.
        else {
            List<Country> solution = new ArrayList<>(10);

            for (int i = 0; i < 2; i++) {
                countries.get(i).setRank(i + 1);
            }
            solution.addAll(countries.subList(0, 3));

            for (int i = yourCountryIndex - 3; i < yourCountryIndex + 4; i++) {
                countries.get(i).setRank(i + 1);
            }
            solution.addAll(countries.subList(yourCountryIndex - 3, yourCountryIndex + 4));
            return solution;
        }
    }

    public static void logCountriesToBeDisplay (List<Country> countries, String yourCountryName) {
        List<Country> countriesToBeDisplayed = fetchCountriesToBeDisplay(countries, yourCountryName);
        for (Country country : countriesToBeDisplayed) {
            Log.d(DisplayHighScoresDecider.class.getSimpleName(), country.getRank() + ". " + country.getName() + ": " + country.getNumOfLetters());
        }
    }

    public static void logAllCounties (List<Country> countries) {
        Collections.sort(countries);
        for (int i = 0; i < countries.size(); i++) {
            Log.d(DisplayHighScoresDecider.class.getSimpleName(), (i + 1) + ". " + countries.get(i).getName() + ": " + countries.get(i).getNumOfLetters());
        }
    }
}
