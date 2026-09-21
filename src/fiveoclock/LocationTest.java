package fiveoclock;

import java.time.ZoneId;
import student.TestCase;

/**
 * 
 * Tests the Location class
 * 
 * @author vivaandutt
 * @version Sep 20, 2026
 */

public class LocationTest extends TestCase {
    private Location lima;

    // Creates Location to use for testing the Location class
    public void setUp() throws Exception {
        super.setUp();
        lima = new Location("Lima", "Peru", ZoneId.of("America/Lima"),
            "Lima is a desert city.", "images/lima.png");
    }


    /**
     * Tests if the Constructor sets up all five values
     */
    public void testConstructorStoresValues() {
        assertEquals("Lima", lima.getCity());
        assertEquals("Peru", lima.getCountry());
        assertEquals(ZoneId.of("America/Lima"), lima.getZoneId());
        assertEquals("Lima is a desert city.", lima.getFunFact());
        assertEquals("images/lima.png", lima.getImagePath());
    }


    /**
     * Tests if Constructor trims off extra spaces
     */
    public void testConstructorTrimsInput() {
        Location padded = new Location(" Lima ", " Peru ", ZoneId.of(
            "America/Lima"), " fact ", " path ");
        assertEquals("Lima", padded.getCity());
        assertEquals("Peru", padded.getCountry());
        assertEquals("fact", padded.getFunFact());
        assertEquals("path", padded.getImagePath());
    }


    /**
     * Tests whether a blank city input is rejected
     */
    public void testConstructorRejectsBlankCity() {
        try {
            new Location(" ", "Peru", ZoneId.of("America/Lima"));
            fail("Expected an IllegalArgumentException for a blank city.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * Tests whether a null city is rejected
     */
    public void testConstructorRejectsNullCity() {
        try {
            new Location(null, "Peru", ZoneId.of("America/Lima"));
            fail("Expected an IllegalArgumentException for a null city.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * Tests whether a blank country is rejected
     */
    public void testConstructorRejectsBlankCountry() {
        try {
            new Location("Lima", " ", ZoneId.of("America/Lima"));
            fail("Expected an IllegalArgumentException for an empty country.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * Tests whether null zones are rejected
     */
    public void testConstructorRejectsNullZone() {
        try {
            new Location("Lima", "Peru", null);
            fail("Expected an IllegalArgumentException for a null zone.");
        }
        catch (IllegalArgumentException e) {
            assertNotNull(e.getMessage());
        }
    }


    /**
     * Tests whether optional values become empty strings instead of null
     */
    public void testOptionalValuesDefaultToEmpty() {
        Location bare = new Location("Lima", "Peru", ZoneId.of("America/Lima"),
            null, null);
        assertEquals("", bare.getFunFact());
        assertEquals("", bare.getImagePath());
    }


    /**
     * Tests whether optional fields are empty strings for the shorter
     * constructor
     */
    public void testShortConstructor() {
        Location bare = new Location("Lima", "Peru", ZoneId.of("America/Lima"));
        assertEquals("Lima", bare.getCity());
        assertEquals("", bare.getFunFact());
        assertEquals("", bare.getImagePath());
    }


    /**
     * Tests whether the toString shows the city, country, and zone
     */
    public void testToString() {
        assertEquals("Lima, Peru (America/Lima)", lima.toString());
    }


    /**
     * Tests whether two Locations with the same city, country, and zone are
     * equal
     */
    public void testEqualsAndHashCode() {
        Location copy = new Location("Lima", "Peru", ZoneId.of("America/Lima"),
            "different fact", "different.png");
        Location other = new Location("Tokyo", "Japan", ZoneId.of(
            "Asia/Tokyo"));
        
        assertTrue(lima.equals(lima));
        assertTrue(lima.equals(copy));
        assertEquals(lima.hashCode(), copy.hashCode());
        assertFalse(lima.equals(other));
        assertFalse(lima.equals(null));
        assertFalse(lima.equals("Lima"));
    }
}
