package fiveoclock;

import java.time.ZoneId;
import java.util.Objects;

/**
 * Holds the information about a single city, including its country and
 * time zone. Instances are immutable.
 *
 * @author Vivaan Dutt
 * @version 2026.09.16
 */
public class Location {
    private final String city;
    private final String country;
    private final ZoneId zoneId;
    private final String funFact;
    private final String imagePath;

    /**
     * Creates a new Location.
     *
     * @param city      the city name; must not be null or blank
     * @param country   the country name; must not be null or blank
     * @param zoneId    the city's time zone; must not be null
     * @param funFact   an optional fun fact; null is stored as ""
     * @param imagePath an optional image path; null is stored as ""
     * @throws IllegalArgumentException if city or country is blank, or if
     *                                  zoneId is null
     */
    public Location(String city, String country, ZoneId zoneId,
        String funFact, String imagePath) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City must not be blank.");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country must not be blank.");
        }
        if (zoneId == null) {
            throw new IllegalArgumentException("Zone ID must not be null.");
        }
        this.city = city.trim();
        this.country = country.trim();
        this.zoneId = zoneId;
        this.funFact = (funFact == null) ? "" : funFact.trim();
        this.imagePath = (imagePath == null) ? "" : imagePath.trim();
    }

    /**
     * Convenience constructor for locations with no fun fact or image.
     *
     * @param city    the city name
     * @param country the country name
     * @param zoneId  the city's time zone
     */
    public Location(String city, String country, ZoneId zoneId) {
        this(city, country, zoneId, "", "");
    }

    /** @return the city name */
    public String getCity() {
        return city;
    }

    /** @return the country name */
    public String getCountry() {
        return country;
    }

    /** @return the location's time zone */
    public ZoneId getZoneId() {
        return zoneId;
    }

    /** @return the fun fact, or "" if none was provided */
    public String getFunFact() {
        return funFact;
    }

    /** @return the image path, or "" if none was provided */
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return city + ", " + country + " (" + zoneId.getId() + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Location that = (Location)other;
        return city.equals(that.city)
            && country.equals(that.country)
            && zoneId.equals(that.zoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, zoneId);
    }
}
