package fiveoclock;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import student.TestCase;


<<<<<<< HEAD

public class TimeFinderServiceTest 
    extends TestCase
=======
// -------------------------------------------------------------------------
/**
 *  Tests the time finder service
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 * 
 *  @author connorbo
 *  @version Sep 21, 2026
 */
class TimeFinderServiceTest extends TestCase
>>>>>>> bcd811d18d434a41b114bc65b5311fa927233a08
{

    // ~ Fields ................................................................
    private static final Instant LIMA_AT_FIVE =
        Instant.parse("2026-09-16T22:30:00Z");

    private LocationRepository repository;
    private TimeFinderService service;
    private Location lima;

    // ~ Constructors ..........................................................
    public void setUp()
    {
        repository = new LocationRepository();
        service = new TimeFinderService(repository, new Random(42));
        lima = new Location("Lima", "Peru", ZoneId.of("America/Lima"));
    }
<<<<<<< HEAD


    // ~Public Methods ........................................................
    public void testConstructor()
    {
        assertNotNull(new TimeFinderService(repository));
    }


    public void testConstructorRejectsN()
    {
        try
        {
=======
    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * tests the constructor
     */
    public void testConstructor() {
        assertNotNull(new TimeFinderService(repository));
    }
    
    // ----------------------------------------------------------
    /**
     * tests reject of null
     */
    public void testConstructorRejectsN() {
        try {
>>>>>>> bcd811d18d434a41b114bc65b5311fa927233a08
            new TimeFinderService(null);
            fail("Expected an IllegalArgumentException for a null repo.");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }
<<<<<<< HEAD


    public void testFindLocationsAtFive()
    {
=======
    
    // ----------------------------------------------------------
    /**
     * tests locations at 5
     */
    public void testFindLocationsAtFive() {
>>>>>>> bcd811d18d434a41b114bc65b5311fa927233a08
        List<Location> matches = service.findLocationsAtFive(LIMA_AT_FIVE);

        assertTrue(matches.contains(lima));

        for (Location location : matches)
        {
            assertEquals(
                17,
                service.getLocalTime(location, LIMA_AT_FIVE).getHour());
        }

    }
<<<<<<< HEAD


=======
    // ----------------------------------------------------------
    /**
     * tests boundaries
     */
>>>>>>> bcd811d18d434a41b114bc65b5311fa927233a08
    public void testFindLocationsAtFiveBoundaries()
    {
        Instant justIn = Instant.parse("2026-09-16T22:00:00Z");
        Instant lastMinute = Instant.parse("2026-09-16T22:59:59Z");
        Instant justBefore = Instant.parse("2026-09-16T21:59:59Z");
        Instant justAfter = Instant.parse("2026-09-16T23:00:00Z");

        assertTrue(service.findLocationsAtFive(justIn).contains(lima));
        assertTrue(service.findLocationsAtFive(lastMinute).contains(lima));
        assertFalse(service.findLocationsAtFive(justBefore).contains(lima));
        assertFalse(service.findLocationsAtFive(justAfter).contains(lima));

    }
<<<<<<< HEAD


=======
    
    // ----------------------------------------------------------
    /**
     * tests the catching of null
     */
>>>>>>> bcd811d18d434a41b114bc65b5311fa927233a08
    public void testFailRejectsNull()
    {
        try
        {
            service.findLocationsAtFive(null);
            fail("Expected an illegallArgumentException for a null moment.");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
    }
<<<<<<< HEAD


=======
    
    // ----------------------------------------------------------
    /**
     * test the randomization of the choosing process
     */
>>>>>>> bcd811d18d434a41b114bc65b5311fa927233a08
    public void testChooseRandomLocation()
    {
        Location tokyo =
            new Location("Tokyo", "Japan", ZoneId.of("Asia/Tokyo"));
        Location paris =
            new Location("Paris", "France", ZoneId.of("Europe/Paris"));
        List<Location> options = Arrays.asList(lima, tokyo, paris);

        Optional<Location> chosen = service.chooseRandomLocation(options);

        assertTrue(chosen.isPresent());
        assertTrue(options.contains(chosen.get()));

    }

}
