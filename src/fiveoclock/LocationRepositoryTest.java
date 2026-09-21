package fiveoclock;

import java.time.ZoneId;
import java.util.List;
import student.TestCase;

/**
 * Tests the LocationRepositoryTest
 * 
 * @author vivaandutt
 * @version Sep 20, 2026
 */
public class LocationRepositoryTest
    extends TestCase
{
    private LocationRepository repository;

    /**
     * Creates the repository to be tested with
     */
    public void setUp()
    {
        repository = new LocationRepository();
    }


    /**
     * Tests whether the constructor loads the prepared locations
     */
    public void testConstructorLoadsLocations()
    {
        assertTrue(repository.getAllLocations().size() > 0);
        assertTrue(repository.getZoneCount() > 0);
    }


    /**
     * Tests whether the locations in the repository are loaded
     */
    public void testLoadedLocationsAreValid()
    {
        for (Location location : repository.getAllLocations())
        {
            assertNotNull(location.getCity());
            assertNotNull(location.getCountry());
            assertNotNull(location.getZoneId());
            assertFalse(location.getCity().isEmpty());
        }
    }


    /**
     * Tests the addLocation() method
     */
    public void testAddLocation()
    {
        ZoneId zone = ZoneId.of("America/Chicago");
        int before = repository.getLocationsForZone(zone).size();

        repository
            .addLocation(new Location("Milwaukee", "United States", zone));

        List<Location> after = repository.getLocationsForZone(zone);
        assertEquals(before + 1, after.size());
        assertTrue(
            after.contains(new Location("Milwaukee", "United States", zone)));
    }


    /**
     * Tests whether a zone can contain more than one city
     */
    public void testAddLocationGroupsByZone()
    {
        ZoneId zone = ZoneId.of("Etc/GMT+3");
        repository.addLocation(new Location("Alpha", "Testland", zone));
        repository.addLocation(new Location("Beta", "Testland", zone));

        assertEquals(2, repository.getLocationsForZone(zone).size());
    }


    /**
     * Adding null throws and leaves the repository unchanged
     */
    public void testAddLocationRejectsNull()
    {
        int before = repository.getAllLocations().size();
        try
        {
            repository.addLocation(null);
            fail("Expected an IllegalArgumentException for a null location.");
        }
        catch (IllegalArgumentException e)
        {
            assertNotNull(e.getMessage());
        }
        assertEquals(before, repository.getAllLocations().size());
    }


    /**
     * Tests whether getAllLocations() returns a copy
     */
    public void testGetAllLocationsReturnsCopy()
    {
        List<Location> all = repository.getAllLocations();
        int before = all.size();
        all.clear();

        assertEquals(before, repository.getAllLocations().size());
    }


    /**
     * Tests whether getLocationsForZone()
     */
    public void testGetLocationsForZone()
    {
        List<Location> peru =
            repository.getLocationsForZone(ZoneId.of("America/Lima"));

        assertEquals(1, peru.size());
        assertEquals("Lima", peru.get(0).getCity());
    }


    /**
     * Tests whether an unused zone returns an empty list
     */
    public void testGetLocationsForUnusedZone()
    {
        List<Location> none =
            repository.getLocationsForZone(ZoneId.of("Antarctica/Troll"));
        assertNotNull(none);
        assertTrue(none.isEmpty());
    }


    /**
     * Tests whether the list returned for a zone is a copy
     */
    public void testGetLocationsForZoneReturnsCopy()
    {
        ZoneId zone = ZoneId.of("America/Lima");
        repository.getLocationsForZone(zone).clear();

        assertEquals(1, repository.getLocationsForZone(zone).size());
    }
}
