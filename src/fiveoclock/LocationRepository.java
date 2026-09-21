package fiveoclock;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores all the locations and organizes them by time zone
 * 
 * @author Vivaan Dutt
 * @version 09/16/2026
 * 
 **/

public class LocationRepository {

    private final Map<ZoneId, List<Location>> locationsByZone;

    /**
     * Creates the repository and the Location objects
     */
    public LocationRepository() {
        this.locationsByZone = new HashMap<>();
        loadStartingLocations();
    }


    /**
     * Adds a new Location
     * 
     * @param location,
     *            the Location to add
     * @throws IllegalArgumentException
     *             if location is null
     */
    public void addLocation(Location location) {
        if (location == null)
            throw new IllegalArgumentException("Location must not be null");
        locationsByZone.computeIfAbsent(location.getZoneId(),
            key -> new ArrayList<>()).add(location);
    }


    /**
     * @return a List with all saved Locations
     */
    public List<Location> getAllLocations() {
        List<Location> all = new ArrayList<>();
        for (List<Location> group : locationsByZone.values()) {
            all.addAll(group);
        }
        return all;
    }


    /**
     * @param zone,
     *            the time zone being searched for
     * @return a copy of the locations stored in the time zone
     */
    public List<Location> getLocationsForZone(ZoneId zone) {
        List<Location> group = locationsByZone.get(zone);
        if (group == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(group);
    }


    /**
     * @return the number of time zones (not including empty ones)
     */
    public int getZoneCount() {
        return locationsByZone.size();
    }


    /**
     * Creates and stores a Location
     */
    private void addIfValid(
        String city,
        String country,
        String zone,
        String funFact) {
        try {
            addLocation(new Location(city, country, ZoneId.of(zone), funFact,
                ""));
        }
        catch (RuntimeException e) {
            System.err.println("Skipping invalid location record: " + city
                + " (" + zone + ") - " + e.getMessage());
        }
    }


    /**
     * Loads the starting list of Locations with fun facts
     */
    private void loadStartingLocations() {
        addIfValid("Honolulu", "United States", "Pacific/Honolulu",
            "Hawaii does not observe daylight saving time.");
        addIfValid("Anchorage", "United States", "America/Anchorage",
            "Anchorage gets over 19 hours of daylight in June.");
        addIfValid("Los Angeles", "United States", "America/Los_Angeles",
            "More than 200 languages are spoken here.");
        addIfValid("Denver", "United States", "America/Denver",
            "Denver sits exactly one mile above sea level.");
        addIfValid("Chicago", "United States", "America/Chicago",
            "The first skyscraper was built in Chicago in 1885.");
        addIfValid("New York", "United States", "America/New_York",
            "The subway never closes.");
        addIfValid("Lima", "Peru", "America/Lima",
            "Lima is the second-largest desert city in the world.");
        addIfValid("Santiago", "Chile", "America/Santiago",
            "Chile stretches over 2,600 miles north to south.");
        addIfValid("Sao Paulo", "Brazil", "America/Sao_Paulo",
            "Sao Paulo is the largest city in the Americas.");
        addIfValid("Reykjavik", "Iceland", "Atlantic/Reykjavik",
            "Almost all of Iceland's heat comes from geothermal energy.");
        addIfValid("London", "United Kingdom", "Europe/London",
            "The Prime Meridian runs through Greenwich.");
        addIfValid("Lagos", "Nigeria", "Africa/Lagos",
            "Lagos is one of the fastest-growing cities on Earth.");
        addIfValid("Paris", "France", "Europe/Paris",
            "Paris has more than 470 parks and gardens.");
        addIfValid("Cairo", "Egypt", "Africa/Cairo",
            "Cairo's name means 'the victorious'.");
        addIfValid("Nairobi", "Kenya", "Africa/Nairobi",
            "Nairobi has a national park inside the city limits.");
        addIfValid("Dubai", "United Arab Emirates", "Asia/Dubai",
            "The Burj Khalifa is over half a mile tall.");
        addIfValid("Karachi", "Pakistan", "Asia/Karachi",
            "Karachi is Pakistan's largest city.");
        addIfValid("Kolkata", "India", "Asia/Kolkata",
            "India uses a single time zone offset by 30 minutes.");
        addIfValid("Kathmandu", "Nepal", "Asia/Kathmandu",
            "Nepal's time zone is offset by 45 minutes.");
        addIfValid("Bangkok", "Thailand", "Asia/Bangkok",
            "Bangkok's full ceremonial name is the longest in the world.");
        addIfValid("Shanghai", "China", "Asia/Shanghai",
            "All of China uses one time zone.");
        addIfValid("Tokyo", "Japan", "Asia/Tokyo",
            "Tokyo is the most populous metropolitan area on Earth.");
        addIfValid("Sydney", "Australia", "Australia/Sydney",
            "Sydney Harbour Bridge is nicknamed 'The Coathanger'.");
        addIfValid("Auckland", "New Zealand", "Pacific/Auckland",
            "Auckland is built on roughly 50 dormant volcanoes.");
        addIfValid("Adak", "United States", "America/Adak",
            "Adak is the westernmost city in the United States.");
        addIfValid("Praia", "Cape Verde", "Atlantic/Cape_Verde",
            "Cape Verde is a group of ten volcanic islands.");
        addIfValid("Fernando de Noronha", "Brazil", "America/Noronha",
            "The island chain is a UNESCO World Heritage site.");
        addIfValid("Dhaka", "Bangladesh", "Asia/Dhaka",
            "Dhaka is one of the most densely populated cities on Earth.");
        addIfValid("Honiara", "Solomon Islands", "Pacific/Guadalcanal",
            "Honiara sits on the island of Guadalcanal.");
        addIfValid("Apia", "Samoa", "Pacific/Apia",
            "Samoa skipped December 30, 2011 to switch time zones.");
    }
}
